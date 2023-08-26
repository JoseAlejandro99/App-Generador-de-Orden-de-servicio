package com.kalex.generadordeordendeservicio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class usuario_item extends RecyclerView.Adapter<usuario_item.UsuarioViewHolder> {

    private List<Usuario> usuarioList;
    private OnUserDeleteListener deleteListener;

    public usuario_item(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public void setData(List<Usuario> nuevosUsuarios) {
        usuarioList.clear();
        usuarioList.addAll(nuevosUsuarios);
        notifyDataSetChanged();
    }

    public interface OnUserDeleteListener {
        void onDeleteUserClick(int userId);
    }

    public void setOnUserDeleteListener(OnUserDeleteListener listener) {
        deleteListener = listener;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_usuario_item, parent, false);
        return new UsuarioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarioList.get(position);
        holder.textViewID.setText("Id: " + usuario.getId());
        holder.textViewNombre.setText("Nombre: " + usuario.getNombreCompleto());
        holder.textViewTelefono.setText("Telefono: " + usuario.getTelefono());
        holder.textViewUsuario.setText("Usuario: " + usuario.getUsuario());
        holder.textViewContraseña.setText("Contraseña: " + usuario.getContraseña());
        holder.textViewNivel.setText("Nivel de usuario: " + usuario.getNivelUsuario());

        holder.btnEliminar.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDeleteUserClick(usuario.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }

    static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView textViewID, textViewNombre, textViewTelefono, textViewUsuario, textViewContraseña, textViewNivel;
        ImageButton btnEliminar;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewID = itemView.findViewById(R.id.textViewID);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewTelefono = itemView.findViewById(R.id.textViewTelefono);
            textViewUsuario = itemView.findViewById(R.id.textViewUsuario);
            textViewContraseña = itemView.findViewById(R.id.textViewContraseña);
            textViewNivel = itemView.findViewById(R.id.textViewNivel);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

}

