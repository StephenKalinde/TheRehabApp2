package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {


     private Button signUpBtn;
     private Button loginBtn;
     private EditText name, surname, cellNumber, emailAdd, password, confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_view);

        signUpBtn= (Button) findViewById(R.id.signUpConfirm);
        loginBtn= (Button) findViewById(R.id.login_btn);

        name= (EditText) findViewById(R.id.name);
        surname= (EditText) findViewById(R.id.surname);
        cellNumber= (EditText) findViewById(R.id.cell_number);
        emailAdd= (EditText) findViewById(R.id.emailAdd);
        password= (EditText) findViewById(R.id.initPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SignUp.this, TermsAndConditions.class ));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (SignUp.this, Login.class));
            }
        });

    }

}
