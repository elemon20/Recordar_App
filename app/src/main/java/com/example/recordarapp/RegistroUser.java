package com.example.recordarapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

public class RegistroUser extends AppCompatActivity {

    private EditText tilRut, username1, password, repassword, respuesta;
    private Button signup;
    private DBHelper DB;
    private Boolean insert = null;
    private Boolean checkRut = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        referencias();
        eventos();
    }

    //method region

    private void newRegisterDB(){
        String rut = tilRut.getText().toString();
        String user = username1.getText().toString();
        String pass = password.getText().toString();
        String repass = repassword.getText().toString();
        String resp = respuesta.getText().toString();

        if(rut.equals("")||user.equals("")||pass.equals("")||repass.equals("")||resp.equals(""))
            Toast.makeText(RegistroUser.this, "Por favor ingrese todos los campos", Toast.LENGTH_SHORT).show();
        else{
            if(pass.equals(repass)){
                //Boolean checkRut = DB.checkusername(rut);
                checkusername(rut);
                if(checkRut==false){
                    //Boolean insert = DB.insertData(user,pass);
                    insertData();
                    if(insert==true){
                        Toast.makeText(RegistroUser.this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), PerfilUser.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(RegistroUser.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegistroUser.this, "¡El usuario ya existe! Por favor, registrese", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(RegistroUser.this, "Contraseña no coinciden", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void insertData(){
        DBHelper DB = new DBHelper(this, "Login", null, 1);
        try (SQLiteDatabase MyBD = DB.getWritableDatabase()){
            if(MyBD != null){
                //ingreso de datos de forma paramétrica
                String[] param = new String[4];
                param[0] = tilRut.getText().toString();
                param[1] = username1.getText().toString();
                param[2] = password.getText().toString();
                param[3] = respuesta.getText().toString();

                MyBD.execSQL("insert into users (rut, username, password, respuesta) " +
                        "values(?,?,?,?)", param);
                insert = true;


            }

        }catch (Exception ex){
            Log.e("TAG_", ex.toString() + " en insert de DB");
        }
    }

    private Boolean checkusername(String idRut){

        DBHelper DB = new DBHelper(this, "Login", null, 1);

        try(SQLiteDatabase MyBD = DB.getWritableDatabase()){
            if(MyBD != null){
                String strRut = idRut;
                Cursor c = MyBD.rawQuery("Select rut from users where rut == ?", new String[]
                        {idRut});

                if(c.moveToFirst()){
                    Log.d("TAG_", "Registros obtenidos :" + c.getCount());
                    do{
                        checkRut = true;

                    }while(c.moveToNext());
                }else

                    checkRut = false;
                return false;
            }
        }catch (Exception ex){
            Log.e("TAG_", ex.toString());
        }
        return null;
    }


    //end region

    //Reference region
    private void referencias() {
        tilRut = findViewById(R.id.tilRut);
        username1 = findViewById(R.id.username1);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signup = findViewById(R.id.btnsignup);
        respuesta = findViewById(R.id.tilRespuesta);




    }
    //end region

    //event region
    private void eventos() {
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newRegisterDB();
                Intent intent = new Intent(getApplicationContext(), LoginUser.class);
                startActivity(intent);
            }

        });
    }

    //end region
}