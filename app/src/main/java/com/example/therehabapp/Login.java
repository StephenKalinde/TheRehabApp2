package com.example.therehabapp;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class Login extends AppCompatActivity {

    private Button loginBtn;
    private EditText emailAddView;
    private EditText passwordView;
    private FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";
    private ProgressDialog progressDialogBox;
    private String uid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        mAuth = FirebaseAuth.getInstance();

        loginBtn =(Button) findViewById(R.id.login_btn);
        emailAddView=(EditText) findViewById(R.id.emailAdd);
        passwordView=(EditText) findViewById(R.id.login_password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!emailAddView.getText().equals(null) && !passwordView.getText().equals(null))
                {

                    progressDialogBox = new ProgressDialog(Login.this, R.style.MyDialogTheme);
                    progressDialogBox.setTitle("Login");
                    progressDialogBox.setMessage("Logging in ...");
                    progressDialogBox.setCancelable(false);
                    progressDialogBox.show();

                    SignInUser(emailAddView.getText().toString(), passwordView.getText().toString());

                }

            }
        });

    }

    private void SignInUser(String emailAdd, String password)
    {
        mAuth.signInWithEmailAndPassword(emailAdd, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Log.d(TAG, "signInWithEmail:  success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            uid = user.getUid();
                            initFCM();
                            progressDialogBox.cancel();
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG);
                            startActivity(new Intent(Login.this, Home.class));
                        }
                        else{
                            Log.w(TAG, "signInWithEmail: failure",task.getException());
                            progressDialogBox.cancel();
                            Toast.makeText(Login.this, "Authentication failed. Try again", Toast.LENGTH_LONG).show();
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
