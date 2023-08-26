package com.kalex.generadordeordendeservicio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TallerMecanico.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "Usuarios";
    private static final String COLUMN_ID = "id_usuario";
    private static final String COLUMN_FULL_NAME = "nombreCompleto";
    private static final String COLUMN_PHONE = "telefono";
    private static final String COLUMN_USERNAME = "usuario";
    private static final String COLUMN_PASSWORD = "contraseña";
    private static final String COLUMN_USER_LEVEL = "nivelUsuario";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FULL_NAME + " TEXT, " +
                    COLUMN_PHONE + " TEXT, " +
                    COLUMN_USERNAME + " TEXT NOT NULL, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL, " +
                    COLUMN_USER_LEVEL + " INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        insertInitialUser(db, "ADMINISTRADOR", "0000000000","admin", "12345",1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private void insertInitialUser(SQLiteDatabase db, String nombreCompleto, String telefono, String usuario, String contraseña, int nivelUsuario) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULL_NAME, nombreCompleto);
        values.put(COLUMN_PHONE, telefono);
        values.put(COLUMN_USERNAME, usuario);
        values.put(COLUMN_PASSWORD, contraseña);
        values.put(COLUMN_USER_LEVEL, nivelUsuario);
        db.insert(TABLE_USERS, null, values);
    }

    public long insertUser(String nombreCompleto, String telefono, String usuario, String contraseña, int nivelUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FULL_NAME, nombreCompleto);
        values.put(COLUMN_PHONE, telefono);
        values.put(COLUMN_USERNAME, usuario);
        values.put(COLUMN_PASSWORD, contraseña);
        values.put(COLUMN_USER_LEVEL, nivelUsuario);

        // Insertar el nuevo usuario en la tabla
        return db.insert(TABLE_USERS, null, values);
    }

    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ID};
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, projection, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, null, null, null, null, null, null);

        if (cursor != null) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nombreIndex = cursor.getColumnIndex(COLUMN_FULL_NAME);
            int telefonoIndex = cursor.getColumnIndex(COLUMN_PHONE);
            int usuarioIndex = cursor.getColumnIndex(COLUMN_USERNAME);
            int contraseñaIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
            int nivelUsuarioIndex = cursor.getColumnIndex(COLUMN_USER_LEVEL);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idIndex);
                String nombreCompleto = cursor.getString(nombreIndex);
                String telefono = cursor.getString(telefonoIndex);
                String usuario = cursor.getString(usuarioIndex);
                String contraseña = cursor.getString(contraseñaIndex);
                int nivelUsuario = cursor.getInt(nivelUsuarioIndex);

                Usuario usuarioObj = new Usuario(id, nombreCompleto, telefono, usuario, contraseña, nivelUsuario);
                usuarios.add(usuarioObj);
            }
            cursor.close();
        }

        return usuarios;
    }

    public void eliminarUsuario(int idUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define la cláusula WHERE para eliminar el usuario por su ID
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(idUsuario)};

        // Elimina el usuario de la base de datos
        db.delete(TABLE_USERS, selection, selectionArgs);

        db.close();
    }

    // Agrega este método a tu clase DatabaseHelper para obtener el nivel de usuario
    public int obtenerNivelUsuario(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_USER_LEVEL};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_USERS, projection, selection, selectionArgs, null, null, null);
        int nivelUsuario = -1; // Valor predeterminado en caso de que no se encuentre el usuario

        if (cursor != null && cursor.moveToFirst()) {
            nivelUsuario = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_LEVEL));
            cursor.close();
        }

        // Agregar líneas de Log.d para imprimir información en el registro
        Log.d("DatabaseHelper", " Usuario: " + username + ", Nivel: " + nivelUsuario);

        return nivelUsuario;
    }



}
