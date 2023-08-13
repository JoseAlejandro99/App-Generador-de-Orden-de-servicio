package com.kalex.generadordeordendeservicio.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.kalex.generadordeordendeservicio.R;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class InicioFragment extends Fragment {

    private EditText txtFolio;
    private EditText txtMarca;
    private EditText txtModelo;
    private EditText txtColor;
    private EditText txtKilometraje;
    private EditText txtPlacas;
    private EditText DateIngreso;
    private EditText DateSalida;
    private EditText txtNombre;
    private EditText txtTelefono;
    private EditText txtEmail;
    private  EditText txtObservaciones;
    private EditText txtNumerodeserie;
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10, checkBox11, checkBox12, checkBox13, checkBox14, checkBox15, checkBox16;
    private SeekBar seekBar;
    private ImageButton botonLadoderecho, botonLadoizquierdo, botonFrente, botonDetras;
    private ImageView imgLadoderecho, imgLadoizquierdo, imgFrente, imgDetras;
    private ImageView imagenActual;
    private Button botonOrdendeservicio;

    private static final int CODIGO_SOLICITUD_PERMISO_CAMARA = 100;
    private static final int CODIGO_SOLICITUD_CAMARA = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inicio, container, false);

        txtFolio = rootView.findViewById(R.id.txtFolio);
        txtMarca = rootView.findViewById(R.id.txtMarca);
        txtModelo = rootView.findViewById(R.id.txtModelo);
        txtColor = rootView.findViewById(R.id.txtColor);
        txtKilometraje = rootView.findViewById(R.id.txtKilometraje);
        txtPlacas = rootView.findViewById(R.id.txtPlacas);
        txtNumerodeserie = rootView.findViewById(R.id.txtNumerodeserie);

        DateIngreso = rootView.findViewById(R.id.DateIngreso);
        DateSalida = rootView.findViewById(R.id.DateSalida);
        txtNombre = rootView.findViewById(R.id.txtNombre);
        txtTelefono = rootView.findViewById(R.id.txtTelefono);
        txtEmail = rootView.findViewById(R.id.txtEmail);

        txtObservaciones = rootView.findViewById(R.id.txtObservaciones);

        checkBox1 = rootView.findViewById(R.id.checkBox1);
        checkBox2 = rootView.findViewById(R.id.checkBox2);
        checkBox3 = rootView.findViewById(R.id.checkBox3);
        checkBox4 = rootView.findViewById(R.id.checkBox4);
        checkBox5 = rootView.findViewById(R.id.checkBox5);
        checkBox6 = rootView.findViewById(R.id.checkBox6);
        checkBox7 = rootView.findViewById(R.id.checkBox7);
        checkBox8 = rootView.findViewById(R.id.checkBox8);
        checkBox9 = rootView.findViewById(R.id.checkBox9);
        checkBox10 = rootView.findViewById(R.id.checkBox10);
        checkBox11 = rootView.findViewById(R.id.checkBox11);
        checkBox12 = rootView.findViewById(R.id.checkBox12);
        checkBox13 = rootView.findViewById(R.id.checkBox13);
        checkBox14 = rootView.findViewById(R.id.checkBox14);
        checkBox15 = rootView.findViewById(R.id.checkBox15);
        checkBox16 = rootView.findViewById(R.id.checkBox16);

        seekBar = rootView.findViewById(R.id.seekBar);

        botonLadoderecho = rootView.findViewById(R.id.botonLadoderecho);
        botonLadoizquierdo = rootView.findViewById(R.id.botonLadoizquierdo);
        botonFrente = rootView.findViewById(R.id.botonFrente);
        botonDetras = rootView.findViewById(R.id.botonDetras);

        imgLadoderecho = rootView.findViewById(R.id.imgLadoderecho);
        imgLadoizquierdo = rootView.findViewById(R.id.imgLadoizquierdo);
        imgFrente = rootView.findViewById(R.id.imgFrente);
        imgDetras = rootView.findViewById(R.id.imgDetras);

        botonOrdendeservicio = rootView.findViewById(R.id.botonOrdendeservicio);

        SeekBar seekBar = rootView.findViewById(R.id.seekBar);
        TextView textViewSeekBarValue = rootView.findViewById(R.id.textViewSeekBarValue);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSeekBarValue.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No es necesario realizar ninguna acción aquí
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // No es necesario realizar ninguna acción aquí
            }
        });


        botonLadoderecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenActual = imgLadoderecho;
                verificarYTomarFoto();
            }
        });

        botonLadoizquierdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenActual = imgLadoizquierdo;
                verificarYTomarFoto();
            }
        });

        botonFrente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenActual = imgFrente;
                verificarYTomarFoto();
            }
        });

        botonDetras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenActual = imgDetras;
                verificarYTomarFoto();
            }
        });

        botonOrdendeservicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateAndSavePDF();
            }
        });

        return rootView;
    }

    private void verificarYTomarFoto() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CODIGO_SOLICITUD_PERMISO_CAMARA);
        } else {
            tomarFoto();
        }
    }

    private void tomarFoto() {
        Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentCamara.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivityForResult(intentCamara, CODIGO_SOLICITUD_CAMARA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODIGO_SOLICITUD_PERMISO_CAMARA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tomarFoto();
            } else {
                Toast.makeText(requireContext(), "Se requieren permisos de cámara para tomar fotos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_SOLICITUD_CAMARA && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imagenBitmap = (Bitmap) extras.get("data");
            imagenActual.setImageBitmap(imagenBitmap);
        }
    }

    private void agregarImagenAlPDF(Document document, ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap imagenBitmap = ((BitmapDrawable) drawable).getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imagenBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        com.itextpdf.layout.element.Image imagenPdf = new com.itextpdf.layout.element.Image(ImageDataFactory.create(byteArray));
        document.add(imagenPdf);
    }



    private void generateAndSavePDF() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            createPDF();
        }
    }

    private void createPDF() {
        try {
            String folderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/OrdenDeServicio";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String fileName = txtFolio.getText().toString() + ".pdf";
            String filePath = folderPath + "/" + fileName;

            PdfWriter pdfWriter = new PdfWriter(filePath);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            // Contenido de pdf
            document.add(new Paragraph("Orden de Servicio"));
            document.add(new Paragraph("Taller Mecanico"));
            document.add(new Paragraph("Mcqueen"));

            document.add(new Paragraph("Folio: " + txtFolio.getText().toString()));

            document.add(new Paragraph("DATOS DEL VEHICULO"));
            document.add(new Paragraph("Marca: "+ txtMarca.getText().toString()));
            document.add(new Paragraph("Modelo: "+ txtModelo.getText().toString()));
            document.add(new Paragraph("Color: "+ txtColor.getText().toString()));
            document.add(new Paragraph("Kilometraje: "+ txtKilometraje.getText().toString()+" KM"));
            document.add(new Paragraph("Placas: "+ txtPlacas.getText().toString()));
            document.add(new Paragraph("Numero de serie: "+ txtNumerodeserie.getText().toString()));

            document.add(new Paragraph("DATOS DEL CLIENTE"));
            document.add(new Paragraph("Ingreso: "+ DateIngreso.getText().toString()));
            document.add(new Paragraph("Salida: "+ DateSalida.getText().toString()));
            document.add(new Paragraph("Nombre: "+ txtNombre.getText().toString()));
            document.add(new Paragraph("Telefono: "+ txtTelefono.getText().toString()));
            document.add(new Paragraph("Email: "+ txtEmail.getText().toString()));

            document.add(new Paragraph("DESCRIPCION DEL TRABAJO"));
            

            document.add(new Paragraph("OBSERVACIONES"));
            document.add(new Paragraph(txtObservaciones.getText().toString()));

            document.add(new Paragraph("INVENTARIO"));
            // Agregar los elementos seleccionados a la lista
            List<String> elementosSeleccionados = new ArrayList<>();
            if (checkBox1.isChecked()) {
                elementosSeleccionados.add(checkBox1.getText().toString());
            }
            if (checkBox2.isChecked()) {
                elementosSeleccionados.add(checkBox2.getText().toString());
            }
            if (checkBox3.isChecked()) {
                elementosSeleccionados.add(checkBox3.getText().toString());
            }
            if (checkBox4.isChecked()) {
                elementosSeleccionados.add(checkBox4.getText().toString());
            }
            if (checkBox5.isChecked()) {
                elementosSeleccionados.add(checkBox5.getText().toString());
            }
            if (checkBox6.isChecked()) {
                elementosSeleccionados.add(checkBox6.getText().toString());
            }
            if (checkBox7.isChecked()) {
                elementosSeleccionados.add(checkBox7.getText().toString());
            }
            if (checkBox8.isChecked()) {
                elementosSeleccionados.add(checkBox8.getText().toString());
            }
            if (checkBox9.isChecked()) {
                elementosSeleccionados.add(checkBox9.getText().toString());
            }
            if (checkBox10.isChecked()) {
                elementosSeleccionados.add(checkBox10.getText().toString());
            }
            if (checkBox11.isChecked()) {
                elementosSeleccionados.add(checkBox11.getText().toString());
            }
            if (checkBox12.isChecked()) {
                elementosSeleccionados.add(checkBox12.getText().toString());
            }
            if (checkBox13.isChecked()) {
                elementosSeleccionados.add(checkBox13.getText().toString());
            }
            if (checkBox14.isChecked()) {
                elementosSeleccionados.add(checkBox14.getText().toString());
            }
            if (checkBox15.isChecked()) {
                elementosSeleccionados.add(checkBox15.getText().toString());
            }
            if (checkBox16.isChecked()) {
                elementosSeleccionados.add(checkBox16.getText().toString());
            }

            // Agregar los elementos seleccionados al documento
            for (String elemento : elementosSeleccionados) {
                document.add(new Paragraph(elemento));
            }
            document.add(new Paragraph("El nivel de gasolina es: "+ seekBar.getProgress() + "%"));

            document.add(new Paragraph("DAÑOS PREEXISTENTES EN EL VEHICULO"));
            // Agregar imágenes al PDF
            document.add(new Paragraph("Lado derecho"));
            agregarImagenAlPDF(document, imgLadoderecho);
            document.add(new Paragraph("Lado izquierdo"));
            agregarImagenAlPDF(document, imgLadoizquierdo);
            document.add(new Paragraph("Frente"));
            agregarImagenAlPDF(document, imgFrente);
            document.add(new Paragraph("Detras"));
            agregarImagenAlPDF(document, imgDetras);

            document.add(new Paragraph("___________________________"));
            document.add(new Paragraph("Dueño del taller mecanico"));

            document.add(new Paragraph("___________________________"));
            document.add(new Paragraph(txtNombre.getText().toString()));

            document.add(new Paragraph("Ampliación Ejido San Fco. S/N, Monte Hermón, 41304 Tlapa, Gro."));

            document.close();
            openPDF(filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openPDF(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            Uri pdfUri = FileProvider.getUriForFile(requireContext(), "com.kalex.generadordeordendeservicio.fileprovider", file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(pdfUri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Importante para otorgar permisos

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(requireContext(), "No se encontró una aplicación para abrir archivos PDF", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
