package com.example.jorgerama.projetoandroidjorge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import Java.ConnectionServer;
import Java.HttpHelper;

public class RegisterActivity extends AppCompatActivity {


    private EditText editName;
    private EditText editMail;
    private EditText editPassword;
    private View mProgressView;
    private View mRegisterFormView;
    private UserRegisterTask mAuthTask = null;

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
        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor = null;

                Usuario new_user = new Usuario(editName.getText().toString(),editMail.getText().toString(),editPassword.getText().toString());


                showProgress(true);
                mAuthTask = new UserRegisterTask(editName.getText().toString(),editMail.getText().toString(),editPassword.getText().toString());
                mAuthTask.execute((Void) null);


                /*try {
                    insert(new_user);

                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    RegisterActivity.this.startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }*/


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



    public class UserRegisterTask extends AsyncTask<Void, Void, String> {

        private final String mEmail;
        private final String mPassword;
        private final String mName;

        UserRegisterTask(String name,String email, String password) {
            mName = name;
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return null;
            }

            /*for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }*/



            Document mainDoc;
            //String toastStr;
            // fixed ip localhost type "ifconfig"
            //String fixedIP = "192.168.0.9";
            //String port = "8080";




            String urlStr = "http://" + ConnectionServer.address + ":" +ConnectionServer.port + "/project/services/Hello/create_user?email=" + mEmail  + "&username=" + mName + "&password=" + mPassword;

            String outStr;

            outStr = "empty";
            try {
                mainDoc = HttpHelper.getXMLFromWeb(urlStr);
                NodeList nodeList = mainDoc
                        .getElementsByTagName("ns:return");
                outStr = // myText.getText() +
                        nodeList.item(0).getChildNodes().item(0).getNodeValue();


                Log.v("MeuApp","testando");
                Log.v("MeuApp", outStr);

                return outStr;

            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                return e.getMessage();
            } catch (SAXException e) {
                e.printStackTrace();
                return e.getMessage();
            }

            //http://localhost:8080/project/services/Hello/create_user?email=JorgeRamaKrsna@gmail.com&username=JorgeRama&password=1234
            /*SQLiteDatabase sqlLite = new DBHelper(contexto).getReadableDatabase();

            String where = "email = ? AND password = ?";

            String[] colunas = new String[] { "name" , "email" , "password"};

            String argumentos[] = new String[] { mEmail,mPassword };

            cursor = sqlLite.query("users", colunas, where, argumentos, null, null, null);

            if ( cursor != null && cursor.moveToFirst()) {
                usuario = new Usuario(cursor.getString(cursor.getColumnIndex("name")),cursor.getString(cursor.getColumnIndex("email")),cursor.getString(cursor.getColumnIndex("password")));
                return usuario.getName();
            }
            else return null;*/
        }

        @Override
        protected void onPostExecute(final String success) {
            mAuthTask = null;
            showProgress(false);

            if (success != null) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class );
                // EditText emailText = (EditText) findViewById(R.id.email);
                //String email = emailText.getText().toString();
                intent.putExtra("EXTRA_NAME" , success);
                startActivity(intent);
                //finish();
            } else {
              //  mPasswordView.setError(getString(R.string.error_incorrect_password));
               // mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }



    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}
