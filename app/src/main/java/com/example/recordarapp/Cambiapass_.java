package com.example.recordarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.ContentValues;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class Cambiapass_ extends AppCompatActivity {

    private TextView rut;
    private EditText pass, re_pass;
    private Button form_btn;

    String strRut = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiapass);


        referencias();
        eventos();



    }

    private void obtieneDatos(String idRut){
        DBHelper DB = new DBHelper(this, "Login", null, 1);

        try(SQLiteDatabase MyBD = DB.getWritableDatabase()){
            if(MyBD != null){
                String rutId = idRut;
                Cursor c = MyBD.rawQuery("Select rut from users where rut == ?", new String[]
                        {rutId});

                if(c.moveToFirst()){
                    Log.d("TAG_", "Registros obtenidos :" + c.getCount());
                    do{
                        strRut = c.getString(0);

                    }while(c.moveToNext());
                }else
                    Toast.makeText(this, "No hay registros que mostrar", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            Log.e("TAG_", ex.toString());
        }

    }

    private Boolean validaPass(String rut, String password){
        DBHelper DB = new DBHelper(this, "Login", null, 1);
        try (SQLiteDatabase MyBD = DB.getWritableDatabase()){
            ContentValues contentValues= new ContentValues();
            contentValues.put("password", password);
            long result = MyBD.update("users",  contentValues, "rut=?",new String[]{rut});
            if(result==-1)
                return false;
            else
                return true;
        }catch (Exception ex){
            Log.e("TAG_", ex.toString());
        }

        return null;
    }

    private void validaCambioPass(){
        String strRut = rut.getText().toString();
        String password = pass.getText().toString();
        String repass = re_pass.getText().toString();
        if (password.equals(repass)) {
            Boolean cambiar_claveapp = validaPass(strRut, password);

            if (cambiar_claveapp == true) {

                Intent intent1 = new Intent(getApplicationContext(), LoginUser.class);
                startActivity(intent1);
                Toast.makeText(Cambiapass_.this, "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(Cambiapass_.this, "Contraseña no pudo ser cambiada ", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(Cambiapass_.this, "La contraseña no es igual ", Toast.LENGTH_SHORT).show();
        }
    }



    //Reference region
    private void referencias() {

        rut = findViewById(R.id.tvusername);
        pass = findViewById(R.id.etnucontraseña);
        re_pass = findViewById(R.id.etrepitcontraseña);
        form_btn = findViewById(R.id.btncamclave);
        //db = new DBHelper(this);

        Intent intent = getIntent();
        rut.setText(intent.getStringExtra("rut"));
    }
    //end region


    //event region
    private void eventos(){
        form_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validaCambioPass();
            }
        });
    }


    //end region

}