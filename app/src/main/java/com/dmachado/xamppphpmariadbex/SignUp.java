package com.dmachado.xamppphpmariadbex;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    private TextInputEditText txt_in_fullname, txt_in_email, txt_in_username, txt_in_password;
    private Button bt_signUp;
    private TextView txt_jatemLogIn;
    private ProgressBar progressBar;

    private static final int CODE_GET_REQUEST = 15;
    private static final int CODE_POST_REQUEST = 16;

    private boolean isUpdating = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txt_in_fullname = findViewById(R.id.txt_in_fullname);
        txt_in_email = findViewById(R.id.txt_in_email);
        txt_in_username = findViewById(R.id.txt_in_username);
        txt_in_password = findViewById(R.id.txt_in_pass);

        txt_jatemLogIn = findViewById(R.id.txt_jatemconta);

        bt_signUp = findViewById(R.id.bt_signUp);

        progressBar = findViewById(R.id.progress);

        bt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullname, username, pass, email;

                fullname = String.valueOf(txt_in_fullname.getText());
                username = String.valueOf(txt_in_username.getText());
                email = String.valueOf(txt_in_email.getText());
                pass = String.valueOf(txt_in_password.getText());

                if (!TextUtils.isEmpty(fullname) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(email)) {

                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);

                   /* Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "email";
                            field[3] = "password";

                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = email;
                            data[3] = pass;
                            PutData putData = new PutData("http://192.168.1.80/PhpMyqlTestDrive/Operac/New/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });*/

                    HashMap<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("fullname", fullname);
                    params.put("email", email);
                    params.put("password", pass);

                    Log.e("vamosver", "US: " + username + " FN: " + fullname + " EM: "+email + " PS: "+pass);

                    //Calling the create hero API
                    PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_USER, params, CODE_POST_REQUEST);
                    request.execute();

                    Log.e("vamosss", "Chegou aqui");

                }else {
                    Toast.makeText(getApplicationContext(), "Falta preencher campos", Toast.LENGTH_LONG).show();
                }
            }
        });

        txt_jatemLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, MainActivity.class));
                finish();
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    //refreshing the herolist after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    //because we haven't created it yet
                    //refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST) {
                Log.e("vbamos", "entrou aqui");
                return requestHandler.sendPostRequest(url, params);
            }

            if (requestCode == CODE_GET_REQUEST) {
                return requestHandler.sendGetRequest(url);
            }

            return null;
        }
    }
}