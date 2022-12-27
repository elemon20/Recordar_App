package com.example.recordarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.CollationElementIterator;

public class PerfilUser extends AppCompatActivity {

    private Button cambioclave, btnCrearEvento, btnEliminarEvento;

    String strRut = "";
    String strUsuario = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        referencias();
        eventos();
        Intent intent = getIntent();
        strRut = intent.getStringExtra("rut");
        strUsuario = intent.getStringExtra("usuario");


    }

    private void cambiarPass(){

        Intent intent = new Intent(getApplicationContext(), Cambiapass.class);
        intent.putExtra("rut", strRut);
        intent.putExtra("usuario", strUsuario);
        startActivity(intent);
    }

    private void confirmarEliminacion(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("¿Estás seguro de que quieres eliminar tus datos?");
        alert.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Eliminación de datos", "Eliminando datos...");
                eliminarDatos();
                Intent intent = new Intent(getApplicationContext(), LoginUser.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }


    private void eliminarDatos(){
        DBHelper DB = new DBHelper(this, "Login", null, 1);
        try (SQLiteDatabase MyBD = DB.getWritableDatabase()) {
            if (MyBD != null) {
                String sql1 = "DELETE FROM users WHERE rut = ?";
                String sql2 = "DELETE FROM events WHERE user_rut = ?";

                MyBD.execSQL(sql1, new String[]{strRut});
                MyBD.execSQL(sql2, new String[]{strRut});

            }
        }catch (Exception ex){
            Log.e("TAG_", ex.toString() + " en insert de DB");
        }
    }



    private void referencias() {
        
        cambioclave = findViewById(R.id.btncambiarcontraseña);
        btnCrearEvento = findViewById(R.id.btnCrearEvento);
        btnEliminarEvento = findViewById(R.id.btnEliminarEvento);


    }

    private void eventos() {
        cambioclave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarPass();
            }
        });

        btnCrearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CrearEvento.class);
                intent.putExtra("rut", strRut);
                intent.putExtra("usuario", strUsuario);
                startActivity(intent);
            }
        });

        btnEliminarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarEliminacion();

            }
        });

    }




}