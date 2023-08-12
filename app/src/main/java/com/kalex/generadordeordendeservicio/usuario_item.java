package com.kalex.generadordeordendeservicio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class usuario_item extends RecyclerView.Adapter<usuario_item.UsuarioViewHolder> {

    private List<Usuario> usuarioList;

    public usuario_item(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public void setData(List<Usuario> nuevosUsuarios) {
        usuarioList.clear();
        usuarioList.addAll(nuevosUsuarios);
        notifyDataSetChanged();
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
        holder.textViewID.setText(String.valueOf(usuario.getId()));
        holder.textViewNombre.setText(usuario.getNombreCompleto());
        holder.textViewTelefono.setText(String.valueOf(usuario.getTelefono()));
        holder.textViewUsuario.setText(usuario.getUsuario());
        holder.textViewContraseña.setText(usuario.getContraseña());
        holder.textViewNivel.setText(String.valueOf(usuario.getNivelUsuario()));
    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }

    static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView textViewID, textViewNombre, textViewTelefono, textViewUsuario, textViewContraseña, textViewNivel;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewID = itemView.findViewById(R.id.textViewID);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewTelefono = itemView.findViewById(R.id.textViewTelefono);
            textViewUsuario = itemView.findViewById(R.id.textViewUsuario);
            textViewContraseña = itemView.findViewById(R.id.textViewContraseña);
            textViewNivel = itemView.findViewById(R.id.textViewNivel);
        }
    }
}

