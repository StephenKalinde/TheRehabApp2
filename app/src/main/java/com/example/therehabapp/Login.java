package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private Button loginBtn;
    private EditText email;
    private EditText password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        loginBtn =(Button) findViewById(R.id.login_btn);
        email=(EditText) findViewById(R.id.emailAdd);
        password=(EditText) findViewById(R.id.login_password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validation here 

                startActivity(new Intent(Login.this, Home.class ));
            }
        });

    }
}
