package com.kalex.generadordeordendeservicio;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText login_user;
    private EditText login_password;
    private Button botonLogin;
    private DatabaseSQLite database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_user = findViewById(R.id.login_user);
        login_password = findViewById(R.id.login_password);
        botonLogin = findViewById(R.id.boton_login);
        database = new DatabaseSQLite(this);

        // Agregar un nuevo usuario a la base de datos
        Boolean result = database.insertData("Administrador", 1234567890, "admin", "12345", 1);
        if (result) {
            Toast.makeText(LoginActivity.this, "New user added successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "Failed to add new user", Toast.LENGTH_SHORT).show();
        }

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = login_user.getText().toString();
                String contraseña = login_password.getText().toString();

                if (usuario.isEmpty() || contraseña.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    boolean credentialsMatch = database.checkUsuarioContraseña(usuario, contraseña);

                    if (credentialsMatch) {
                        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, Activity_Principal.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}