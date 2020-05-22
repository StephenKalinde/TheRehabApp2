package com.example.therehabapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class SignUp extends AppCompatActivity {

     private Button signUpBtn;
     private Button loginBtn;
     public  EditText nameView, surnameView, cellNumberView, emailAddView, passwordView, confirmPasswordView;
     private FirebaseAuth mAuth;

     private static final String TAG="SignUpActivity";
     private ProgressDialog progressDialogBox;
     private String uid;

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

        nameView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        surnameView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        cellNumberView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        emailAddView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        passwordView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        confirmPasswordView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nameView.getText().toString().equals("") || surnameView.getText().toString().equals("") || cellNumberView.getText().toString().equals("") ||emailAddView.getText().toString().equals("") || passwordView.getText().toString().equals("") || confirmPasswordView.getText().toString().equals("") )
                {
                    Toast.makeText(SignUp.this, "Enter All Details", Toast.LENGTH_SHORT).show();
                }

                else{

                    if (passwordView.getText().toString().equals(confirmPasswordView.getText().toString())) {

                        progressDialogBox = new ProgressDialog(SignUp.this, R.style.MyDialogTheme);
                        progressDialogBox.setTitle("Sign Up");
                        progressDialogBox.setMessage("Signing Up...");
                        progressDialogBox.setCancelable(false);
                        progressDialogBox.show();

                        CreateAccount(nameView.getText().toString(), surnameView.getText().toString(), cellNumberView.getText().toString(), emailAddView.getText().toString(), passwordView.getText().toString(), confirmPasswordView.getText().toString());


                    }

                    else {

                        Toast.makeText(SignUp.this, "Passwords Do not match. Try Again!", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (SignUp.this, Login.class));
            }
        });

    }

    private void CreateAccount(String name, String surname , String cellNumber, String emailAdd, String password, String confirmPassword)
    {
            mAuth.createUserWithEmailAndPassword(emailAdd, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){
                            if (task.isSuccessful())
                            {
                                Log.d(TAG, "createUserWithEmail: successful");
                                FirebaseUser user =mAuth.getCurrentUser();
                                uid = user.getUid();
                                initFCM();
                                progressDialogBox.cancel();
                                Toast.makeText(SignUp.this, "Sign Up Successful!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUp.this, TermsAndConditions.class ));
                            }

                            else {
                                Log.w(TAG,"createUserWithEmail: failure");
                                progressDialogBox.cancel();
                                Toast.makeText(SignUp.this, "Sign Up failed. Try Again.",Toast.LENGTH_LONG).show();
                            }

                        }

                    });

    }

    private void initFCM()
    {

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("DebuggMe: ",token);
        sendRegistrationTokenToServer(token);
    }

    //save fcm token to server
    private void sendRegistrationTokenToServer(String token)
    {

        DatabaseReference tokenRef = FirebaseDatabase.getInstance().getReference("Tokens/"+uid);
        tokenRef.setValue(token).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {

                    Log.d("DebuggMe: ","Token saved successfully");

                }

                else{

                    Log.d("DebuggMe: "," Token failed to save");

                }

            }
        });

    }


}
