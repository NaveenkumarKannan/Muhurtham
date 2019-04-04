package com.project.naveen.muhurtham;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Contact extends AppCompatActivity {
    EditText etMobNo1,etMobNo2, etEmail,etAddr;

    String Name, DOB, PlaceOfBirth, Business,TimeOfBirth,Gender;
    String FName,MName, FBusiness,MBusiness, BroNo,SisNo,OwnHouse;
    String MobNo1,MobNo2, Email,Addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        etMobNo1 = findViewById(R.id.etMobNo1);
        etMobNo2 = findViewById(R.id.etMobNo2);
        etEmail = findViewById(R.id.etEmail);
        etAddr = findViewById(R.id.etAddr);

        Bundle Extras = getIntent().getExtras();
        Name = Extras.getString("Name");
        DOB = Extras.getString("DOB");
        PlaceOfBirth = Extras.getString("PlaceOfBirth");
        TimeOfBirth = Extras.getString("TimeOfBirth");
        Business = Extras.getString("Business");
        Gender = Extras.getString("Gender");

        FName = Extras.getString("FName");
        MName = Extras.getString("MName");
        FBusiness = Extras.getString("FBusiness");
        MBusiness = Extras.getString("MBusiness");
        BroNo = Extras.getString("BroNo");
        SisNo = Extras.getString("SisNo");
        OwnHouse = Extras.getString("OwnHouse");

        //Toast.makeText(getApplicationContext(),FName+MName+ FBusiness+MBusiness+ BroNo+SisNo+OwnHouse+Name+ DOB+ PlaceOfBirth+ Business+TimeOfBirth+Gender, Toast.LENGTH_LONG).show();
    }

    public void nextPage(View view) {
        MobNo1 = etMobNo1.getText().toString();
        MobNo2 = etMobNo2.getText().toString();
        Email = etEmail.getText().toString();
        Addr = etAddr.getText().toString();

        //Toast.makeText(getApplicationContext(),MobNo1+MobNo2+ Email+Addr+FName+MName+ FBusiness+MBusiness+ BroNo+SisNo+OwnHouse+Name+ DOB+ PlaceOfBirth+ Business+TimeOfBirth+Gender, Toast.LENGTH_LONG).show();

        if(MobNo1.trim().length()>0 && MobNo2.trim().length()>0&&Email.trim().length()>0
                && Addr.trim().length()>0){
            Intent intent = new Intent(Contact.this,Horoscope.class);

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

            extras.putString("MobNo1",MobNo1);
            extras.putString("MobNo2",MobNo2);
            extras.putString("Email",Email);
            extras.putString("Addr",Addr);

            intent.putExtras(extras);

            startActivity(intent);

        }
        else {
            Toast.makeText(getApplicationContext(),"Enter all the details", Toast.LENGTH_LONG).show();
        }
    }
}
