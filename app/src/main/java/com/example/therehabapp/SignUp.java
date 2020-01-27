package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {


     private Button signUpBtn;
     private Button loginBtn;
     private EditText nameView, surnameView, cellNumberView, emailAddView, passwordView, confirmPasswordView;
     private FirebaseAuth mAuth;
     private static final String TAG="SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_view);

        mAuth= FirebaseAuth.getInstance();

        signUpBtn= (Button) findViewById(R.id.signUpConfirm);
        loginBtn= (Button) findViewById(R.id.login_btn);

        nameView= (EditText) findViewById(R.id.name);
        surnameView= (EditText) findViewById(R.id.surname);
        cellNumberView= (EditText) findViewById(R.id.cell_number);
        emailAddView= (EditText) findViewById(R.id.emailAdd);
        passwordView= (EditText) findViewById(R.id.initPassword);
        confirmPasswordView = (EditText) findViewById(R.id.confirmPassword);

        final String name = nameView.getText().toString();
        final String surname = surnameView.getText().toString();
        final String cellNumber = cellNumberView.getText().toString();
        final String emailAdd= emailAddView.getText().toString();
        final String password= passwordView.getText().toString();
        final String confirmPassword= confirmPasswordView.getText().toString();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateAccount(name,surname,cellNumber,emailAdd, password,confirmPassword);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (SignUp.this, Login.class));
            }
        });


    }

    /**@Override
    public void onStart()
    {
        super.onStart();
        //check if user os signed (non-null) and updater UI accordingly.
        FirebaseUser currentUser =mAuth.getCurrentUser();
        updateUI(currentUser);
    }**/

    private void CreateAccount(String name, String surname , String cellNumber, String emailAdd, String password, String confirmPassword)
    {
        if(password==confirmPassword)
        {
            mAuth.createUserWithEmailAndPassword(emailAdd, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){
                            if (task.isSuccessful())
                            {
                                Log.d(TAG, "createUserWithEmail: successful");
                                FirebaseUser user =mAuth.getCurrentUser();
                                Toast.makeText(SignUp.this, "Sign Up Successful!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUp.this, TermsAndConditions.class ));
                            }

                            else {
                                Log.w(TAG,"createUserWithEmail: failure");
                                Toast.makeText(SignUp.this, "Sign Up failed. Try Again.",Toast.LENGTH_LONG).show();
                            }
                        }

                    });
        }
    }

}
