package com.kalex.generadordeordendeservicio;

public class Usuario {
    private int id;
    private String nombreCompleto;
    private int telefono;
    private String usuario;
    private String contraseña;
    private int nivelUsuario;

    public Usuario(int id, String nombreCompleto, int telefono, String usuario, String contraseña, int nivelUsuario) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.nivelUsuario = nivelUsuario;
    }

    public int getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public int getNivelUsuario() {
        return nivelUsuario;
    }
}
