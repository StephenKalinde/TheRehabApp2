package com.example.therehabapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileSetup extends AppCompatActivity {

    private Button continueBtn;
    private EditText nameView, surnameView, iDNumberView, dateOfBirthView, addressLine1View, addressLine2View, cityView, postalCodeView,homePhoneView, cellPhoneView, emailAddView, physicianNameView, physicainNumberView, substancesView;
    private CheckBox hospitalisedNo, hospitalizedYes, addictionsNo, addictionsYes, smokingNo, smokingYes, criminalRecordNo, criminalRecordYes;
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
        physicianNameView=(EditText)findViewById(R.id.physician_view);
        physicainNumberView=(EditText)findViewById(R.id.physician_view);
        substancesView=(EditText)findViewById(R.id.substance_view);

        hospitalisedNo=(CheckBox)findViewById(R.id.check_box_no) ;
        hospitalizedYes=(CheckBox)findViewById(R.id.check_box_yes);
        addictionsNo=(CheckBox)findViewById(R.id.check_box_no2);
        addictionsYes=(CheckBox)findViewById(R.id.check_box_yes2);
        smokingNo= (CheckBox)findViewById(R.id.check_box_no3);
        smokingYes= (CheckBox)findViewById(R.id.check_box_yes3);
        criminalRecordNo= (CheckBox)findViewById(R.id.check_box_no4);
        criminalRecordYes=(CheckBox)findViewById(R.id.check_box_yes4);

        continueBtn = (Button) findViewById(R.id.submit_btn);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((hospitalisedNo.isChecked()||hospitalizedYes.isChecked()) && (addictionsNo.isChecked()||addictionsYes.isChecked()) && (smokingNo.isChecked()||smokingYes.isChecked()) && (criminalRecordNo.isChecked()||criminalRecordYes.isChecked()))
                {

                    ProgressDialog progressDialogBox = new ProgressDialog(ProfileSetup.this, R.style.MyDialogTheme);
                    progressDialogBox.setTitle("Saving...");
                    progressDialogBox.setCancelable(false);
                    progressDialogBox.show();

                    SaveToDb();

                    startActivity(new Intent(ProfileSetup.this, CarePacks.class) );

                }
                else{

                    Toast.makeText(ProfileSetup.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void SaveToDb()
    {
        User userDetails= new User(nameView.getText().toString(), surnameView.getText().toString(), iDNumberView.getText().toString(),dateOfBirthView.getText().toString(), addressLine1View.getText().toString(), addressLine2View.getText().toString(), cityView.getText().toString(),postalCodeView.getText().toString(), homePhoneView.getText().toString(), cellPhoneView.getText().toString(), emailAddView.getText().toString());
        Map<String ,User> map= new HashMap();
        String user= mAuth.getUid();
        map.put(user, userDetails);

        DatabaseReference dbRefUsers= db.getReference("Users");
        dbRefUsers.setValue(map);

        //saving profile questions
        boolean hospitalized=false , addictions=false , smoking=false ,criminalRecord=false ;
        if(hospitalizedYes.isChecked() == true)
        {
            hospitalized=true;
        }
        if(addictionsYes.isChecked() == true)
        {
            addictions=true;
        }
        if(smokingYes.isChecked() == true)
        {
            smoking=true;
        }
        if(criminalRecordYes.isChecked() == true)
        {
            criminalRecord=true;
        }

        ProfileQuestions profileQuestions = new ProfileQuestions(hospitalized,physicianNameView.getText().toString(),physicainNumberView.getText().toString(),addictions,substancesView.getText().toString(),smoking,criminalRecord);
        Map<String , ProfileQuestions> map2= new HashMap<>();
        map2.put(user,profileQuestions);

        DatabaseReference dbRefQuestions= db.getReference("UserAnswers");
        dbRefQuestions.setValue(map2);
    }

}

