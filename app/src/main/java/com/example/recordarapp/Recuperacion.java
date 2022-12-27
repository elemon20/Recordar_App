package com.example.recordarapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Recuperacion extends AppCompatActivity {

    private EditText etRut, etResp;
    private Button reset_btn;

    String strRut = "";
    String strUsuario = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion);

        referencias();
        eventos();

        Intent intent = getIntent();
        strRut = intent.getStringExtra("rut");
        strUsuario = intent.getStringExtra("usuario");

    }

    private void actualizaPass(){
        String rut = etRut.getText().toString();
        String resp = etResp.getText().toString();
        Boolean checkRut = checkRut(rut,resp);
        if (checkRut == true) {
            Toast.makeText(Recuperacion.this, "Usuario correcto", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Cambiapass.class);
            intent.putExtra("rut", rut);
            intent.putExtra("usuario", strUsuario);
            try {
                startActivity(intent);
            }catch (Exception ex){
                Log.e("TAG_", ex.toString());
            }

        } else {

            Toast.makeText(Recuperacion.this, "Usuario no existe", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean checkRut(String idRut, String resp){

        DBHelper DB = new DBHelper(this, "Login", null, 1);

        try(SQLiteDatabase MyBD = DB.getWritableDatabase()){
            if(MyBD != null){
                String strRut = idRut;
                String strResp = resp;
                Cursor c = MyBD.rawQuery("Select rut from users where rut == ? and respuesta == ?",
                        new String[] {idRut, resp});

                if (c.getCount() > 0)
                    return true;
                else
                    return false;
            }
        }catch (Exception ex){
            Log.e("TAG_", ex.toString());
        }
        return null;
    }

    private void referencias() {
        reset_btn = findViewById(R.id.btnrecu);
        etResp = findViewById(R.id.etResp);
        etRut = findViewById(R.id.etRut);

    }

    private void eventos() {
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizaPass();
            }
        });

    }
}