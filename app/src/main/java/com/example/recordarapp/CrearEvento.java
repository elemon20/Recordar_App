package com.example.recordarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class CrearEvento extends AppCompatActivity {

    private EditText etNombreev,etFechaev,etObservacion,etLugarev;
    private Button btnGuardarev;
    private Spinner spnImportancia;
    private String[] importancia;

    String strRut = "";

    private ArrayAdapter<String> miAdaptador;
    private ArrayList<Evento> losEventos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);

        poblar();
        ref1();
        eventos();


        Intent intent = getIntent();
        strRut = intent.getStringExtra("rut");
    }

    private void poblar() {
        importancia = new String[4];
        importancia[0] = "Seleccione tipo";
        importancia[1] = "Alta";
        importancia[2] = "Media";
        importancia[3] = "Baja";


    }

    private void insertDataev(){

        String nomev = etNombreev.getText().toString();
        String fech = etFechaev.getText().toString();
        String obsev = etObservacion.getText().toString();
        String impor = spnImportancia.getSelectedItem().toString();
        String lugev = etLugarev.getText().toString();

        DBHelper DB = new DBHelper(this, "event", null, 1);
        try (SQLiteDatabase MyBD = DB.getWritableDatabase()){
            if(MyBD != null){

                if (nomev.equals("")||fech.equals("")||obsev.equals("")||impor.equals("")||lugev.equals("")) {
                    Toast.makeText(CrearEvento.this, "Por favor ingrese todos los campos", Toast.LENGTH_SHORT).show();
                }else
                {
                    String[] param = new String[6];
                    param[0] = etNombreev.getText().toString();
                    param[1] = etFechaev.getText().toString();
                    param[2] = spnImportancia.getSelectedItem().toString();
                    param[3] = etObservacion.getText().toString();
                    param[4] = etLugarev.getText().toString();
                    param[5] = strRut;

                    MyBD.execSQL("insert into events(nombre, fecha, importancia, observacion, lugar, user_rut) " +
                            "values(?,?,?,?,?,?)", param);


                }

            }
            else {



            }

        }catch (Exception ex){
            Log.e("TAG_", ex.toString() + " en insert de DB");
        }
    }

    private void grabarEvento(){
        Evento e = new Evento();
        e.setTitulo(etNombreev.getText().toString());
        e.setFecha(etFechaev.getText().toString());
        e.setImportancia(spnImportancia.getSelectedItem().toString());
        e.setObservacion(etObservacion.getText().toString());
        e.setLugar(etLugarev.getText().toString());

        losEventos.add(e);

        Toast.makeText(this, "Grabado exitosamente", Toast.LENGTH_SHORT).show();

    }

    private void ref1() {
        etNombreev = findViewById(R.id.etnombreev);
        etFechaev = findViewById(R.id.etfechaev);
        etObservacion= findViewById(R.id.etobservacion);
        etLugarev = findViewById(R.id.etlugarev);
        btnGuardarev = findViewById(R.id.btnguardarev);
        spnImportancia = findViewById(R.id.spnImportancia);

        miAdaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, importancia);
        spnImportancia.setAdapter(miAdaptador);
    }




    //event region
    private void eventos() {
        btnGuardarev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertDataev();
                grabarEvento();
                Intent intent = new Intent(getApplicationContext(), PerfilUser.class);
                startActivity(intent);
            }
        });
    }

    //end region
}