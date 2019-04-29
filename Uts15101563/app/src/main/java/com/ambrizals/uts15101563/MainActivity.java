package com.ambrizals.uts15101563;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    EditText et_loginUsername;
    EditText et_loginPassword;
    String usernameConfig, passwordConfig;


    String loginUsername, loginPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button)findViewById(R.id.btn_submitLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_loginUsername = (EditText)findViewById(R.id.et_LoginUsername);
                et_loginPassword = (EditText)findViewById(R.id.et_loginPassword);
                loginUsername = et_loginUsername.getText().toString();
                loginPassword = et_loginPassword.getText().toString();
                usernameConfig = getString(R.string.username);
                passwordConfig = getString(R.string.password);
                Intent welcomeIntent = new Intent(MainActivity.this, WelcomeActivity.class);

                if ((loginUsername.equals("")) || (loginPassword.equals(""))) {
                    Toast.makeText(MainActivity.this, "Terdapat kolom yang kosong", Toast.LENGTH_SHORT).show();
                } else {
                    if ((loginUsername.equals(usernameConfig)) && (loginPassword.equals(passwordConfig))) {
                        Toast.makeText(getBaseContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(welcomeIntent);
                    } else {
                        Toast.makeText(MainActivity.this, "Username atau Password Salah", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

}
