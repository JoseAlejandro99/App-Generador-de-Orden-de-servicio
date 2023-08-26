package com.kalex.generadordeordendeservicio.ui.Usuario;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalex.generadordeordendeservicio.R;
import com.kalex.generadordeordendeservicio.SignUpActivity;
import com.kalex.generadordeordendeservicio.Usuario;
import com.kalex.generadordeordendeservicio.DatabaseHelper;
import com.kalex.generadordeordendeservicio.usuario_item;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AlertDialog;
import android.os.Handler;


public class UsuariosFragment extends Fragment {

    private RecyclerView recyclerView;
    private usuario_item usuarioAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_usuarios, container, false);

        ImageButton buttonAgregar = rootView.findViewById(R.id.ButtonAgregar);

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = rootView.findViewById(R.id.listaUsuario);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        databaseHelper = new DatabaseHelper(getActivity());
        usuarioAdapter = new usuario_item(new ArrayList<>()); // Inicializa el adaptador con una lista vacía
        recyclerView.setAdapter(usuarioAdapter);

        // Cambios en el método onDeleteUserClick en UsuariosFragment
        usuarioAdapter.setOnUserDeleteListener(new usuario_item.OnUserDeleteListener() {
            @Override
            public void onDeleteUserClick(int userId) {
                showDeleteConfirmationDialog(userId);
                // Usar un Handler para postergar la actualización de datos
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        updateUsuarioList();
                    }
                });
            }
        });



        updateUsuarioList();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUsuarioList();
    }

    private void eliminarUsuario(int idUsuario) {
        databaseHelper.eliminarUsuario(idUsuario); // Llama al método de eliminación de la base de datos
    }

    private void updateUsuarioList() {
        List<Usuario> usuarios = databaseHelper.getAllUsuarios();
        usuarioAdapter.setData(usuarios);
        usuarioAdapter.notifyDataSetChanged();
    }

    private void showDeleteConfirmationDialog(int userId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirmar Eliminación")
                .setMessage("¿Estás seguro de que quieres eliminar este usuario?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarUsuario(userId);
                        updateUsuarioList();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

}


