package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileSetup extends AppCompatActivity {

    private Button continueBtn;
    private EditText nameView, surnameView, iDNumberView, dateOfBirthView, addressLine1View, addressLine2View, cityView, postalCodeView,homePhoneView, cellPhoneView, emailAddView;
    private FirebaseDatabase db;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup);

        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        nameView= (EditText) findViewById(R.id.first_name_view);
        surnameView=(EditText) findViewById(R.id.surname_view);
        iDNumberView=(EditText) findViewById(R.id.id_number_view);
        dateOfBirthView=(EditText)findViewById(R.id.dob_view);
        addressLine1View=(EditText)findViewById(R.id.address_line_view);
        addressLine2View=(EditText)findViewById(R.id.address_line_view2);
        cityView=(EditText)findViewById(R.id.address_city_view);
        postalCodeView=(EditText)findViewById(R.id.address_postal_code_view);
        homePhoneView=(EditText)findViewById(R.id.home_phone_view);
        cellPhoneView=(EditText)findViewById(R.id.cell_phone_view);
        emailAddView=(EditText)findViewById(R.id.email_view);



        continueBtn = (Button) findViewById(R.id.submit_btn);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User userDetails= new User(nameView.getText().toString(), surnameView.getText().toString(), iDNumberView.getText().toString(),dateOfBirthView.getText().toString(), addressLine1View.getText().toString(), addressLine2View.getText().toString(), cityView.getText().toString(),postalCodeView.getText().toString(), homePhoneView.getText().toString(), cellPhoneView.getText().toString(), emailAddView.getText().toString());
                Map<String ,User> map= new HashMap();
                String user= mAuth.getUid();
                map.put(user, userDetails);

                DatabaseReference dbRef= db.getReference("Users");
                dbRef.setValue(map);

                startActivity(new Intent(ProfileSetup.this, CarePacks.class) );

            }
        });

    }

}

