package com.kalex.generadordeordendeservicio.ui.home;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import com.kalex.generadordeordendeservicio.R;

import java.io.File;

public class InicioFragment extends Fragment {

        private static final int REQUEST_IMAGE_CAPTURE = 1;
        private static final int REQUEST_CAMERA_PERMISSION = 2;
        private ImageView currentImageView;

        // Variables para las vistas
        private TextView txtFolio, txtMarca, txtModelo, textColor, txtKilometraje, txtPlacas,
                txtNumerodeserie, DateIngreso, DateSalida, txtNombre, txtTelefono, txtEmail,
                texCantidad, txtDescripciontrabajo, txtCosto, txtObservaciones;

        private SeekBar seekBar;
        private TextView textViewSeekBarValue;

        // Variables para los CheckBoxes
        private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7,
                checkBox8, checkBox9, checkBox10, checkBox11, checkBox12, checkBox13, checkBox14, checkBox15, checkBox16;

        // Variables para los ImageButtons
        private ImageButton botonLadoderecho, BotonLadoizquierdo, botonFrente, botonDetras;

        // Variables para los ImageViews
        private ImageView imgLadoderecho, imgLadoizquierdo, imgFrente, imgDetras;

        // Variables para el Button
        private Button botoOrdendeservicio;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_inicio, container, false);

                // Inicializar las vistas usando findViewById
                txtFolio = rootView.findViewById(R.id.txtFolio);
                txtMarca = rootView.findViewById(R.id.txtMarca);
                txtModelo = rootView.findViewById(R.id.txtModelo);
                textColor = rootView.findViewById(R.id.textColor);
                txtKilometraje = rootView.findViewById(R.id.txtKilometraje);
                txtPlacas = rootView.findViewById(R.id.txtPlacas);
                txtNumerodeserie = rootView.findViewById(R.id.txtNumerodeserie);
                DateIngreso = rootView.findViewById(R.id.DateIngreso);
                DateSalida = rootView.findViewById(R.id.DateSalida);
                txtNombre = rootView.findViewById(R.id.txtNombre);
                txtTelefono = rootView.findViewById(R.id.txtTelefono);
                txtEmail = rootView.findViewById(R.id.txtEmail);
                texCantidad = rootView.findViewById(R.id.texCantidad);
                txtDescripciontrabajo = rootView.findViewById(R.id.txtDescripciontrabajo);
                txtCosto = rootView.findViewById(R.id.txtCosto);
                txtObservaciones = rootView.findViewById(R.id.txtObservaciones);

                // Inicializar los CheckBoxes
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

                // Inicializar los ImageButtons
                botonLadoderecho = rootView.findViewById(R.id.botonLadoderecho);
                BotonLadoizquierdo = rootView.findViewById(R.id.BotonLadoizquierdo);
                botonFrente = rootView.findViewById(R.id.botonFrente);
                botonDetras = rootView.findViewById(R.id.botonDetras);

                // Inicializar los ImageViews
                imgLadoderecho = rootView.findViewById(R.id.imgLadoderecho);
                imgLadoizquierdo = rootView.findViewById(R.id.imgLadoizquierdo);
                imgFrente = rootView.findViewById(R.id.imgFrente);
                imgDetras = rootView.findViewById(R.id.imgDetras);

                // Inicializar el Button
                botoOrdendeservicio = rootView.findViewById(R.id.botoOrdendeservicio);
                botoOrdendeservicio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        generatePdf();
                    }
                });

                // Inicializar el SeekBar y TextView
                seekBar = rootView.findViewById(R.id.seekBar);
                textViewSeekBarValue = rootView.findViewById(R.id.textViewSeekBarValue);

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        String progressText = progress + "%";
                        textViewSeekBarValue.setText(progressText);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // No se necesita implementación aquí
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // No se necesita implementación aquí
                    }
                });

                setupCaptureButton(botonLadoderecho, imgLadoderecho);
                setupCaptureButton(BotonLadoizquierdo, imgLadoizquierdo);
                setupCaptureButton(botonFrente, imgFrente);
                setupCaptureButton(botonDetras, imgDetras);

            return rootView;
        }

    private void generatePdf() {
        String folderName = "OrdenDeServicio";
        File externalFilesDir = requireContext().getExternalFilesDir(null);
        File pdfFolder = new File(externalFilesDir, folderName);

        if (!pdfFolder.exists()) {
            if (!pdfFolder.mkdirs()) {
                Log.e("PDFGeneration", "No se pudo crear el directorio");
                return;
            }
        }

        if (!pdfFolder.canWrite()) {
            Log.e("PDFGeneration", "No se puede escribir en el directorio");
            return;
        }

        String fileName = txtFolio.getText().toString() + ".pdf";

        // Crear la ruta completa del archivo PDF dentro del directorio
        String pdfPath = new File(pdfFolder, fileName).getAbsolutePath();

        try {
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Agregar los datos de los EditText al PDF
            document.add(new Paragraph("Folio: " + txtFolio.getText().toString()));
            // Agregar más campos de EditText aquí...

            document.close();

            // Mostrar un mensaje de éxito o hacer alguna otra acción
            Toast.makeText(getActivity(), "PDF generado correctamente", Toast.LENGTH_SHORT).show();

            // Abrir el archivo PDF utilizando una aplicación de visualización de PDF
            openPdf(pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error al generar el PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void openPdf(String pdfPath) {
        File pdfFile = new File(pdfPath);
        if (pdfFile.exists()) {
            Uri pdfUri = FileProvider.getUriForFile(requireContext(), "com.example.appname.fileprovider", pdfFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(pdfUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "El archivo PDF no existe", Toast.LENGTH_SHORT).show();
        }
    }




    private void setupCaptureButton(ImageButton button, ImageView imageView) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentImageView = imageView;
                    dispatchTakePictureIntent();
                }
            });
        }

        private void requestCameraPermission() {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                dispatchTakePictureIntent();
            }
        }

        private void dispatchTakePictureIntent() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if (requestCode == REQUEST_CAMERA_PERMISSION) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    // Handle permission denied case
                }
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                currentImageView.setImageBitmap(imageBitmap);
            }
        }
    }