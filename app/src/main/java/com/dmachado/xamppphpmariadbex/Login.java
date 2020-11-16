package com.dmachado.xamppphpmariadbex;

import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {

    private TextInputEditText txt_in_username, txt_in_pass;
    private TextView txt_criarConta;
    private Button bt_login;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_criarConta = findViewById(R.id.txt_criarnovaconta);

        txt_in_username = findViewById(R.id.txt_in_username);
        txt_in_pass = findViewById(R.id.txt_in_pass);

        bt_login = findViewById(R.id.bt_login);

        progressBar = findViewById(R.id.progress);

        txt_criarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp.class));
                finish();
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullname, username, pass, email;

                username = String.valueOf(txt_in_username.getText());
                pass = String.valueOf(txt_in_pass.getText());

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pass)) {

                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";

                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = pass;
                            PutData putData = new PutData("http://192.168.1.80/PhpMyqlTestDrive/Operac/New/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Login Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });

                }else {
                    Toast.makeText(getApplicationContext(), "Falta preencher campos", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}