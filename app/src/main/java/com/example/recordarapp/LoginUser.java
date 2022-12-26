package com.example.recordarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;


public class LoginUser extends AppCompatActivity {


    private TextInputLayout tiluser, tilpass, tilRutLogin;
    private Button btnlogin,signin,btnRecu;
    private DBHelper DB;
    private int indiceActual;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        referencias();
        eventos();
        obtenerUltimoUser();
        guardaruserActual();;



    }

    private void obtenerUltimoUser(){
        SharedPreferences sp = getSharedPreferences("datos", Context.MODE_PRIVATE);
        indiceActual = sp.getInt("indice",0);
    }
    private void guardaruserActual(){
        SharedPreferences sp = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorSP = sp.edit();
        editorSP.putInt("indice", indiceActual);
        editorSP.putString("usuario", tiluser.getEditText().getText().toString());
        editorSP.commit();


    }

    @Override
    protected void onDestroy() {
        guardaruserActual();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        guardaruserActual();
        super.onPause();
    }

    private void iniciarSesion(){
        String user, pass, rut;

        rut = tilRutLogin.getEditText().getText().toString();
        user = tiluser.getEditText().getText().toString();
        pass = tilpass.getEditText().getText().toString();

        if(rut.equals("")||user.equals("")||pass.equals(""))
            Toast.makeText(LoginUser.this, "Por favor ingrese todos los campos", Toast.LENGTH_SHORT).show();
        else{
            Boolean checkuserpass = checkusernamepassword(rut, user, pass);
            if(checkuserpass==true){
                Toast.makeText(LoginUser.this, "Iniciaste sesión con éxito", Toast.LENGTH_SHORT).show();
                Intent intent  = new Intent(getApplicationContext(), PerfilUser.class);
                intent.putExtra("rut", rut);
                intent.putExtra("usuario", user);
                startActivity(intent);
            }else{
                Toast.makeText(LoginUser.this, "Datos no válidas", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Boolean checkusernamepassword(String rut, String username, String password){
        DBHelper DB = new DBHelper(this, "Login", null, 1);
        try (SQLiteDatabase MyBD = DB.getWritableDatabase()){
            if(MyBD != null){
                Cursor c = MyBD.rawQuery("Select * from users where rut = ? and username = ? and password = ?", new String[]
                        {rut,username,password});

                if(c.getCount()>0)
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
        tilRutLogin =  findViewById(R.id.tilRutLogin);
        tiluser =  findViewById(R.id.tilUser3);
        tilpass =  findViewById(R.id.tilPass);
        btnlogin =  findViewById(R.id.btnsignin1);
        signin =  findViewById(R.id.btnsignin);
        btnRecu = findViewById(R.id.btnRecu);



    }

    private void eventos(){

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistroUser.class);
                startActivity(intent);
            }
        });


        btnRecu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Recuperacion.class);
                startActivity(intent);
            }
        });

    }







}