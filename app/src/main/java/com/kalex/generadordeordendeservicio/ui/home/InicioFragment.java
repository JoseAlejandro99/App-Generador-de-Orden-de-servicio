package com.kalex.generadordeordendeservicio.ui.home;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.kalex.generadordeordendeservicio.R;
import java.io.File;
import java.io.IOException;

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
    private ImageView imgLadoderecho, imgLadoizquierdo, imgFrente, imgDetras;
    private Button botonOrdendeservicio;

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






        botonOrdendeservicio = rootView.findViewById(R.id.botonOrdendeservicio);

        botonOrdendeservicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateAndSavePDF();
            }
        });

        return rootView;
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
            document.add(new Paragraph("Ingreso: "+ txtNombre.getText().toString()));
            document.add(new Paragraph("Ingreso: "+ txtTelefono.getText().toString()));
            document.add(new Paragraph("Ingreso: "+ txtEmail.getText().toString()));

            document.add(new Paragraph("DESCRIPCION DEL TRABAJO"));

            document.add(new Paragraph("OBSERVACIONES"));
            document.add(new Paragraph(txtObservaciones.getText().toString()));

            document.add(new Paragraph("INVENTARIO"));

            document.add(new Paragraph("DAÑOS PREEXISTENTES EN EL VEHICULO"));
            document.add(new Paragraph("Lado derecho"));
            document.add(new Paragraph("Lado izquierdo"));
            document.add(new Paragraph("Frente"));
            document.add(new Paragraph("Detras"));

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
