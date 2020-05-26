package com.example.therehabapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class TermsAndConditions extends AppCompatActivity {

    private Button confirmBtn;
    private FirebaseAuth mAuth;

    private static final String TAG="SignUpActivity";
    private ProgressDialog progressDialogBox;
    private String uid;

    private String emailAddress;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_and_conditions);

        mAuth = FirebaseAuth.getInstance();

        emailAddress = getIntent().getStringExtra("emailAddress");
        password = getIntent().getStringExtra("password");

        confirmBtn = (Button) findViewById(R.id.agree_btn);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialogBox = new ProgressDialog(TermsAndConditions.this, R.style.MyDialogTheme);
                progressDialogBox.setTitle("Sign Up");
                progressDialogBox.setMessage("Signing Up...");
                progressDialogBox.setCancelable(false);
                progressDialogBox.show();

                CreateAccount(emailAddress,password);

            }
        });
    }

    /** create user accouunt **/
    private void CreateAccount(String emailAdd, String password)
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
                            Toast.makeText(TermsAndConditions.this, "Sign Up Successful!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(TermsAndConditions.this, QuestionaireOptions.class ));
                            finishAffinity();
                        }

                        else {
                            Log.w(TAG,"createUserWithEmail: failure");
                            progressDialogBox.cancel();
                            //Toast.makeText(TermsAndConditions.this, "Sign Up failed. Try Again.",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(TermsAndConditions.this,SignUp.class);
                            intent.putExtra("Error","error");
                            startActivity(intent);
                            finishAffinity();
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
