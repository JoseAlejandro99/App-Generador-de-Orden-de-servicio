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
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.kalex.generadordeordendeservicio.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class InicioFragment extends Fragment {

    private EditText txtFolio, txtMarca, txtModelo, txtColor, txtKilometraje, txtPlacas, DateIngreso, DateSalida, txtNombre, txtTelefono, txtEmail, txtObservaciones, txtNumerodeserie, txtCantidad, txtDescripciontrabajo, txtCosto;

    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10, checkBox11, checkBox12, checkBox13, checkBox14, checkBox15, checkBox16;
    private SeekBar seekBar;
    private ImageButton botonLadoderecho, botonLadoizquierdo, botonFrente, botonDetras;
    private ImageView imgLadoderecho, imgLadoizquierdo, imgFrente, imgDetras;
    private ImageView imagenActual;
    private Button botonOrdendeservicio;

    private static final int CODIGO_SOLICITUD_PERMISO_CAMARA = 100;
    private static final int CODIGO_SOLICITUD_CAMARA = 1;
    private boolean todasLasImagenesTomadas = false;

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

        txtCantidad = rootView.findViewById(R.id.txtCantidad);
        txtDescripciontrabajo = rootView.findViewById(R.id.txtDescripciontrabajo);
        txtCosto = rootView.findViewById(R.id.txtCosto);

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

    private boolean validarCampos() {
        String[] campos = {
                txtFolio.getText().toString(),
                txtMarca.getText().toString(),
                txtModelo.getText().toString(),
                txtColor.getText().toString(),
                txtKilometraje.getText().toString(),
                txtPlacas.getText().toString(),
                txtNumerodeserie.getText().toString(),
                DateIngreso.getText().toString(),
                DateSalida.getText().toString(),
                txtNombre.getText().toString(),
                txtTelefono.getText().toString(),
                txtCantidad.getText().toString(),
                txtDescripciontrabajo.getText().toString(),
                txtCosto.getText().toString()
        };

        String[] nombresCampos = {
                "Folio",
                "Marca",
                "Modelo",
                "Color",
                "Kilometraje",
                "Placas",
                "Número de serie",
                "Fecha de Ingreso",
                "Fecha de Salida",
                "Nombre",
                "Teléfono",
                "Cantidad",
                "Descripción del Trabajo",
                "Costo"
        };

        StringBuilder camposFaltantes = new StringBuilder();

        boolean camposValidos = true;

        for (int i = 0; i < campos.length; i++) {
            if (campos[i].isEmpty()) {
                camposFaltantes.append("- ").append(nombresCampos[i]).append("\n");
                camposValidos = false;
            }
        }

        if (!camposValidos) {
            String mensaje = "Por favor, rellene los siguientes campos:\n" + camposFaltantes.toString();
            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show();
        }

        return camposValidos;
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
            // Verificar si todas las imágenes han sido tomadas
            if (imgLadoderecho.getDrawable() != null &&
                    imgLadoizquierdo.getDrawable() != null &&
                    imgFrente.getDrawable() != null &&
                    imgDetras.getDrawable() != null) {
                todasLasImagenesTomadas = true;
            }
        }
    }

    private void agregarImagenACelda(Table table, ImageView imageView, float widthPercent, float heightPercent) {
        Drawable drawable = imageView.getDrawable();
        Bitmap imagenBitmap = ((BitmapDrawable) drawable).getBitmap();

        // Redimensionar la imagen
        int newWidth = (int) (imagenBitmap.getWidth() * widthPercent);
        int newHeight = (int) (imagenBitmap.getHeight() * heightPercent);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imagenBitmap, newWidth, newHeight, true);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        com.itextpdf.layout.element.Image imagenPdf = new com.itextpdf.layout.element.Image(ImageDataFactory.create(byteArray));

        // Crea una celda en la tabla y agrega la imagen a la celda
        Cell cell = new Cell();
        cell.add(imagenPdf);

        // Agrega la celda a la tabla
        table.addCell(cell);
    }


    private void generateAndSavePDF() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            if (todasLasImagenesTomadas && validarCampos()) {
                createPDF();
            } else if (!todasLasImagenesTomadas) {
                Toast.makeText(requireContext(), "Por favor, tome las fotos de los 4 ángulos", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private Paragraph createCenteredParagraph(String text) {
        Paragraph paragraph = new Paragraph(text);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        return paragraph;
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


            //Contenido del PDF

            // Crear una tabla con 3 filas y 3 columnas
            Table table1 = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1}));
            table1.setBorder(Border.NO_BORDER);

            // Agregar celdas a la tabla
            table1.addCell("");
            table1.addCell(createCenteredParagraph("Orden de servicio")).setHorizontalAlignment(HorizontalAlignment.CENTER);
            table1.addCell(createCenteredParagraph("Folio: " + txtFolio.getText().toString())).setHorizontalAlignment(HorizontalAlignment.CENTER);

            table1.addCell("");
            table1.addCell(createCenteredParagraph("Taller Mecanico Mcqueen")).setHorizontalAlignment(HorizontalAlignment.CENTER);
            table1.addCell("");

            table1.setWidth(UnitValue.createPercentValue(100));

            // Agregar la tabla al documento
            document.add(table1);

            //----------------------------------------------------

            Table table2 = new Table(UnitValue.createPercentArray(new float[]{1, 1}));

            // Agregar celdas a la tabla
            table2.addCell(createCenteredParagraph("DATOS DEL VEHICULO")).setHorizontalAlignment(HorizontalAlignment.CENTER);
            table2.addCell(createCenteredParagraph("DATOS DEL CLIENTE")).setHorizontalAlignment(HorizontalAlignment.CENTER);
            table2.addCell("Marca: " + txtMarca.getText().toString());
            table2.addCell("Ingreso: " + DateIngreso.getText().toString());
            table2.addCell("Modelo: " + txtModelo.getText().toString() + "Color: " + " " + txtColor.getText().toString());
            table2.addCell("Salida: " + DateSalida.getText().toString());
            table2.addCell("Kilometraje: " + txtKilometraje.getText().toString() + " KM" + " " + "Placas: " + txtPlacas.getText().toString());
            table2.addCell("Nombre: " + txtNombre.getText().toString());
            table2.addCell("Numero de serie: " + txtNumerodeserie.getText().toString());
            table2.addCell("Telefono: " + txtTelefono.getText().toString());
            table2.addCell("");
            table2.addCell("Email: " + txtEmail.getText().toString());

            table2.setWidth(UnitValue.createPercentValue(100));

            // Agregar la tabla al documento
            document.add(table2);

            //---------------------------------------------------------------------------------------
            // Crear una tabla con 3 filas y 3 columnas
            Table table3 = new Table(4);

            // Agregar celdas a la tabla
            table3.addCell(createCenteredParagraph("CANTIDAD")).setHorizontalAlignment(HorizontalAlignment.CENTER);
            table3.addCell(createCenteredParagraph("DESCRIPCION DEL TRABAJO")).setHorizontalAlignment(HorizontalAlignment.CENTER);
            table3.addCell(createCenteredParagraph("COSTO")).setHorizontalAlignment(HorizontalAlignment.CENTER);
            table3.addCell(createCenteredParagraph("IMPORTE")).setHorizontalAlignment(HorizontalAlignment.CENTER);

            table3.addCell(txtCantidad.getText().toString());
            table3.addCell(txtDescripciontrabajo.getText().toString());
            table3.addCell("$" + txtCosto.getText().toString());

            String cantidad = txtCantidad.getText().toString();
            String costo = txtCosto.getText().toString();

            // Convertir las cadenas a valores numéricos (double)
            double Vcantidad = Double.parseDouble(cantidad);
            double Vcosto = Double.parseDouble(costo);

            // Calcular el resultado de la multiplicación
            double resultadoImporte = Vcantidad * Vcosto;

            table3.addCell("$" + (resultadoImporte));

            table3.addCell("");
            table3.addCell("");
            table3.addCell("TOTAL: ");
            table3.addCell("$" + (resultadoImporte));

            table3.setWidth(UnitValue.createPercentValue(100));

            // Agregar la tabla al documento
            document.add(table3);

            //---------------------------------------------------------------------------------------

            Table table4 = new Table(1);

            // Agregar celdas a la tabla

            table4.addCell(createCenteredParagraph("OBSERVACIONES")).setHorizontalAlignment(HorizontalAlignment.CENTER);
            table4.addCell(""+txtObservaciones.getText().toString());

            table4.setWidth(UnitValue.createPercentValue(100));

            // Agregar la tabla al documento
            document.add(table4);

            //---------------------------------------------------------------------------------------

            Table table5 = new Table(1);

            // Agregar celdas a la tabla

            table5.addCell(createCenteredParagraph("INVENTARIO")).setHorizontalAlignment(HorizontalAlignment.CENTER);

            table5.setWidth(UnitValue.createPercentValue(100));

            // Agregar la tabla al documento
            document.add(table5);

            //---------------------------------------------------------------------
            Table table6 = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 1}));
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

            for (String elemento : elementosSeleccionados) {
                table6.addCell(elemento);
            }


            table6.setWidth(UnitValue.createPercentValue(100));

            // Agregar la tabla al documento
            document.add(table6);
            //---------------------------------------------------------------------------------------

            Table table7 = new Table(1);

            // Agregar celdas a la tabla

            table7.addCell(createCenteredParagraph("El nivel de gasolina es: " + seekBar.getProgress() + "%")).setHorizontalAlignment(HorizontalAlignment.CENTER);

            table7.setWidth(UnitValue.createPercentValue(100));

            // Agregar la tabla al documento
            document.add(table7);
            //---------------------------------------------------------------------------------------

            Table table8 = new Table(1);

            // Agregar celdas a la tabla

            table8.addCell(createCenteredParagraph("DAÑOS PREEXISTENTES EN EL VEHICULO")).setHorizontalAlignment(HorizontalAlignment.CENTER);

            table8.setWidth(UnitValue.createPercentValue(100));

            // Agregar la tabla al documento
            document.add(table8);
            //---------------------------------------------------------------------------------------

            Table table9 = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 1}));

            // Agregar celdas a la tabla

            table9.addCell(createCenteredParagraph("Lado derecho")).setHorizontalAlignment(HorizontalAlignment.CENTER);
            table9.addCell(createCenteredParagraph("Lado izquierdo")).setHorizontalAlignment(HorizontalAlignment.CENTER);
            table9.addCell(createCenteredParagraph("Frente")).setHorizontalAlignment(HorizontalAlignment.CENTER);
            table9.addCell(createCenteredParagraph("Detras")).setHorizontalAlignment(HorizontalAlignment.CENTER);

            agregarImagenACelda(table9, imgLadoderecho, 0.6f, 0.6f);
            agregarImagenACelda(table9, imgLadoizquierdo, 0.6f, 0.6f);
            agregarImagenACelda(table9, imgFrente, 0.6f, 0.6f);
            agregarImagenACelda(table9, imgDetras, 0.6f, 0.6f);

            table9.setWidth(UnitValue.createPercentValue(100));

            // Agregar la tabla al documento
            document.add(table9);
            //---------------------------------------------------------------------------------------

            Table table10 = new Table(2);

            // Agregar celdas a la tabla

            table10.addCell(createCenteredParagraph("\n____________________________\n" + "Dueño del taller mecanico")).setHorizontalAlignment(HorizontalAlignment.CENTER);
            table10.addCell(createCenteredParagraph("\n____________________________\n" + txtNombre.getText().toString())).setHorizontalAlignment(HorizontalAlignment.CENTER);

            table10.setWidth(UnitValue.createPercentValue(100));

            // Agregar la tabla al documento
            document.add(table10);

            //---------------------------------------------------------------------------------------

            Table table11 = new Table(1);

            // Agregar celdas a la tabla

            table11.addCell(createCenteredParagraph("Ampliación Ejido San Fco. S/N, Monte Hermón, 41304 Tlapa, Gro.")).setHorizontalAlignment(HorizontalAlignment.CENTER);

            table11.setWidth(UnitValue.createPercentValue(100));

            // Agregar la tabla al documento
            document.add(table11);

            //----------------------------------------------------

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
