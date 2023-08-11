package com.kalex.generadordeordendeservicio.ui.notifications;

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

import java.util.List;

public class UsuariosFragment extends Fragment {

    private RecyclerView recyclerView;
    private usuario_item usuarioAdapter;

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
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        List<Usuario> usuarios = databaseHelper.getAllUsuarios();
        usuarioAdapter = new usuario_item(usuarios);
        recyclerView.setAdapter(usuarioAdapter);

        return rootView;
    }
}



