package com.kalex.generadordeordendeservicio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseSQLite extends SQLiteOpenHelper {

    public static final String databaseName = "TallerMecanico.db";

    public DatabaseSQLite(@Nullable Context context) {
        super(context, "TallerMecanico.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table Usuario(id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, nombreCompleto TEXT, telefono INTEGER, usuario TEXT, contraseña TEXT, nivelUsuario INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("Drop Table if exists Usuario");
    }

    public Boolean insertData(String nombreCompleto, Integer telefono, String usuario, String contraseña, Integer nivelUsuario) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombreCompleto", nombreCompleto);
        contentValues.put("telefono", telefono);
        contentValues.put("usuario", usuario);
        contentValues.put("contraseña", contraseña);
        contentValues.put("nivelUsuario", nivelUsuario);
        long result = -1;
        try {
            result = MyDatabase.insert("Usuario", null, contentValues);
        } catch (SQLException e) {
            // Manejo de errores, si es necesario.
        }

        return result != -1; // Return true if successful, false otherwise.
    }
    public Boolean checkUsuario(String usuario) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM Usuario WHERE usuario=?", new String[]{usuario});

        boolean usuarioExists = cursor.getCount() > 0;
        cursor.close();
        return usuarioExists;
    }

    public Boolean checkUsuarioContraseña(String usuario, String contraseña) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM Usuario WHERE usuario=? AND contraseña=?", new String[]{usuario, contraseña});

        boolean credentialsMatch = cursor.getCount() > 0;
        cursor.close();
        return credentialsMatch;
    }
}
