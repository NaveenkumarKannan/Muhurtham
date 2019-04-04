package com.project.naveen.muhurtham;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Family extends AppCompatActivity {
    EditText etFName,etMName, etFBusiness,etMBusiness, etBroNo,etSisNo;
    RadioGroup rgOwnHouse;

    String Name, DOB, PlaceOfBirth, Business,TimeOfBirth,Gender;
    String FName,MName, FBusiness,MBusiness, BroNo,SisNo,OwnHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        etFName = findViewById(R.id.etFname);
        etMName = findViewById(R.id.etMname);
        etFBusiness = findViewById(R.id.etFBusiness);
        etMBusiness = findViewById(R.id.etMBusiness);
        etBroNo = findViewById(R.id.etBroNo);
        etSisNo = findViewById(R.id.etSisNo);
        rgOwnHouse = (RadioGroup) findViewById(R.id.rgOwnHouse);
        rgOwnHouse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rbYes:
                        OwnHouse = "Yes";
                        break;
                    case R.id.rbNo:
                        OwnHouse = "No";
                        break;
                }
            }
        });

        Bundle Extras = getIntent().getExtras();
        Name = Extras.getString("Name");
        DOB = Extras.getString("DOB");
        PlaceOfBirth = Extras.getString("PlaceOfBirth");
        TimeOfBirth = Extras.getString("TimeOfBirth");
        Business = Extras.getString("Business");
        Gender = Extras.getString("Gender");

        //Toast.makeText(getApplicationContext(),"Intent"+Name+ DOB+ PlaceOfBirth+ Business+TimeOfBirth+Gender, Toast.LENGTH_LONG).show();

    }

    public void nextPage(View view) {
        FName = etFName.getText().toString();
        MName = etMName.getText().toString();
        FBusiness = etFBusiness.getText().toString();
        //MBusiness = etMBusiness.getText().toString();
        SisNo = etSisNo.getText().toString();
        BroNo = etBroNo.getText().toString();

        //Toast.makeText(getApplicationContext(),FName+MName+ FBusiness+MBusiness+ BroNo+SisNo+OwnHouse+Name+ DOB+ PlaceOfBirth+ Business+TimeOfBirth+Gender, Toast.LENGTH_LONG).show();

        if(FName.trim().length()>0 && MName.trim().length()>0&&FBusiness.trim().length()>0
                && SisNo.trim().length()>0 && BroNo.trim().length()>0 ){

            Intent intent = new Intent(Family.this,Contact.class);

            Bundle extras = new Bundle();
            extras.putString("Name",Name);
            extras.putString("DOB",DOB);
            extras.putString("PlaceOfBirth",PlaceOfBirth);
            extras.putString("TimeOfBirth",TimeOfBirth);
            extras.putString("Business",Business);
            extras.putString("Gender",Gender);

            extras.putString("FName",FName);
            extras.putString("MName",MName);
            extras.putString("FBusiness",FBusiness);
            extras.putString("MBusiness",MBusiness);
            extras.putString("BroNo",BroNo);
            extras.putString("SisNo",SisNo);
            extras.putString("OwnHouse",OwnHouse);
            intent.putExtras(extras);

            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(),"Enter all the details", Toast.LENGTH_LONG).show();
        }


    }
}
