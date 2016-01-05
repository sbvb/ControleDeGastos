package com.example.jorgerama.projetoandroidjorge;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {


    private EditText editName;
    private EditText editMail;
    private EditText editPassword;
    Context contexto;
    private AlertDialog alerta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        contexto = this.getApplicationContext();

        Button register_button = (Button) findViewById(R.id.buttonRegisterUser);
        editName = (EditText) findViewById(R.id.editName);
        editMail = (EditText) findViewById(R.id.editMail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if()

                Cursor cursor = null;

                Usuario new_user = new Usuario(editName.getText().toString(),editMail.getText().toString(),editPassword.getText().toString());


                try {
                    insert(new_user);

                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    RegisterActivity.this.startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

    }

    public Usuario select(String userMail) throws Exception {
        Usuario usuario = null;
        Cursor cursor = null;

        SQLiteDatabase sqlLite = new DBHelper(contexto).getReadableDatabase();

        String where = "email = ?";

        String[] colunas = new String[] { "name" , "email" , "password"};

        String argumentos[] = new String[] { userMail };

        cursor = sqlLite.query("users", colunas, where, argumentos, null, null, null);

        if ( cursor != null && cursor.moveToFirst()) {
            usuario = new Usuario(cursor.getString(cursor.getColumnIndex("name")),cursor.getString(cursor.getColumnIndex("email")),cursor.getString(cursor.getColumnIndex("password")));
        }

        if (cursor != null)
            cursor.close();

        return usuario;
    }

    public long insert(Usuario usuario) throws Exception {
        SQLiteDatabase sqlLite = new DBHelper(contexto).getWritableDatabase();

        ContentValues content = new ContentValues();

        content.put("name", usuario.getName());
        content.put("email", usuario.getEmail());
        content.put("password", usuario.getPassword());

        return sqlLite.insert("users", null, content);
    }

}
