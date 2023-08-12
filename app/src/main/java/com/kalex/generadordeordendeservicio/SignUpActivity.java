package com.kalex.generadordeordendeservicio;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText nombreCompletoEditText, telefonoEditText, usuarioEditText, contraseñaEditText, confirmarContraseñaEditText;
    private RadioGroup nivelUsuarioRadioGroup;
    private Button agregarUsuarioButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Inicialización de vistas
        nombreCompletoEditText = findViewById(R.id.TextnombreCompleto);
        telefonoEditText = findViewById(R.id.textTelefono);
        usuarioEditText = findViewById(R.id.TextUsuario);
        contraseñaEditText = findViewById(R.id.TextContraseña);
        confirmarContraseñaEditText = findViewById(R.id.TextConfirmarContraseña);
        nivelUsuarioRadioGroup = findViewById(R.id.nivelUsuario);
        agregarUsuarioButton = findViewById(R.id.AgregarUsuario);
        databaseHelper = new DatabaseHelper(this);

        // Configuración del botón Agregar Usuario
        agregarUsuarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarUsuario();
            }
        });
    }

    private void agregarUsuario() {
        // Obtener los valores de los campos de entrada
        String nombreCompleto = nombreCompletoEditText.getText().toString().trim();
        String telefonoStr = telefonoEditText.getText().toString().trim();
        long telefono = (telefonoStr.isEmpty()) ? 0 : Long.parseLong(telefonoStr);
        String usuario = usuarioEditText.getText().toString().trim();
        String contraseña = contraseñaEditText.getText().toString();
        String confirmarContraseña = confirmarContraseñaEditText.getText().toString();

        // Obtener el ID del RadioButton seleccionado en el RadioGroup
        int selectedId = nivelUsuarioRadioGroup.getCheckedRadioButtonId();
        int nivelUsuario = (selectedId == R.id.radioButton) ? 1 : 2; // Suponiendo '1' para Administrador y '2' para Empleado

        // Verificar si las contraseñas coinciden
        if (contraseña.equals(confirmarContraseña)) {
            if (!nombreCompleto.isEmpty() && !usuario.isEmpty() && !contraseña.isEmpty()) {
                // Insertar el usuario en la base de datos
                long insertedRow = databaseHelper.insertUser(nombreCompleto, telefono, usuario, contraseña, nivelUsuario);

                if (insertedRow != -1) {
                    Toast.makeText(this, "Usuario agregado correctamente", Toast.LENGTH_SHORT).show();
                    // Limpiar los campos de entrada
                    nombreCompletoEditText.getText().clear();
                    telefonoEditText.getText().clear();
                    usuarioEditText.getText().clear();
                    contraseñaEditText.getText().clear();
                    confirmarContraseñaEditText.getText().clear();
                    finish();
                } else {
                    Toast.makeText(this, "Error al agregar usuario", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos obligatorios", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
    }
}
