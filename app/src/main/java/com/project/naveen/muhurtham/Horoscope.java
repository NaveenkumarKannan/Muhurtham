package com.project.naveen.muhurtham;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Horoscope extends AppCompatActivity {
    EditText etAstroName, etStar, etSection, etAncestry ;//etBlemish
    RadioGroup rgBlemish;

    String Name, DOB, PlaceOfBirth, Business,TimeOfBirth,Gender;
    String FName,MName, FBusiness,MBusiness, BroNo,SisNo,OwnHouse;
    String MobNo1,MobNo2, Email,Addr;
    String AstroName, Star, Section, Ancestry, Blemish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horoscope);

        etAstroName = findViewById(R.id.etAstroName);
        etStar = findViewById(R.id.etStar);
        etSection = findViewById(R.id.etSection);
        etAncestry = findViewById(R.id.etAncestry);
        //etBlemish = findViewById(R.id.etBlemish);

        rgBlemish = (RadioGroup) findViewById(R.id.rgBlemish);
        rgBlemish.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rbPure:
                        Blemish = "Sutha Jathagam";
                        break;
                    case R.id.rbRaagu:
                        Blemish = "Raagu Kethu";
                        break;
                    case R.id.rbSevvai:
                        Blemish = "Sevvai";
                        break;
                    case R.id.rbRaaguSevvai:
                        Blemish = "Raagu Kethu Sevvai";
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

        FName = Extras.getString("FName");
        MName = Extras.getString("MName");
        FBusiness = Extras.getString("FBusiness");
        MBusiness = Extras.getString("MBusiness");
        BroNo = Extras.getString("BroNo");
        SisNo = Extras.getString("SisNo");
        OwnHouse = Extras.getString("OwnHouse");

        MobNo1 = Extras.getString("MobNo1");
        MobNo2 = Extras.getString("MobNo2");
        Email = Extras.getString("Email");
        Addr = Extras.getString("Addr");

        //Toast.makeText(getApplicationContext(),MobNo1+MobNo2+ Email+Addr+FName+MName+ FBusiness+MBusiness+ BroNo+SisNo+OwnHouse+Name+ DOB+ PlaceOfBirth+ Business+TimeOfBirth+Gender, Toast.LENGTH_LONG).show();

    }

    public void onSubmit(View view) {
        AstroName = etAstroName.getText().toString();
        Star = etStar.getText().toString();
        Section = etSection.getText().toString();
        Ancestry = etAncestry.getText().toString();
        //Blemish = etBlemish.getText().toString();

        //Toast.makeText(getApplicationContext(), AstroName+ Star+ Section+ Ancestry+ Blemish+MobNo1+MobNo2+ Email+Addr+FName+MName+ FBusiness+MBusiness+ BroNo+SisNo+OwnHouse+Name+ DOB+ PlaceOfBirth+ Business+TimeOfBirth+Gender, Toast.LENGTH_LONG).show();


        if(AstroName.trim().length()>0 && Star.trim().length()>0&&Section.trim().length()>0
                && Ancestry.trim().length()>0 ){

            Intent intent = new Intent(Horoscope.this,Confirmation.class);

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

            extras.putString("AstroName",AstroName);
            extras.putString("Star",Star);
            extras.putString("Section",Section);
            extras.putString("Ancestry",Ancestry);
            extras.putString("Blemish",Blemish);

            intent.putExtras(extras);

            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(),"Enter all the details", Toast.LENGTH_LONG).show();
        }

    }
}
