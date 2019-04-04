package com.project.naveen.muhurtham;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Confirmation extends AppCompatActivity {
    String Name, DOB, PlaceOfBirth, Business,TimeOfBirth,Gender;
    String FName,MName, FBusiness,MBusiness, BroNo,SisNo,OwnHouse;
    String MobNo1,MobNo2, Email,Addr;
    String AstroName, Star, Section, Ancestry, Blemish;

    TextView tvName, tvDOB, tvPlaceOfBirth, tvBusiness,tvTimeOfBirth,tvGender,
    tvFName,tvMName, tvFBusiness,tvMBusiness, tvBroNo,tvSisNo,tvOwnHouse,
    tvMobNo1,tvMobNo2, tvEmail,tvAddr,
    tvAstroName, tvStar, tvSection, tvAncestry, tvBlemish;

    LinearLayout LLPD,LLFD,LLCD,LLHD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

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

        AstroName = Extras.getString("AstroName");
        Star = Extras.getString("Star");
        Section = Extras.getString("Section");
        Ancestry = Extras.getString("Ancestry");
        Blemish = Extras.getString("Blemish");

        //Toast.makeText(getApplicationContext(),AstroName+ Star+ Section+ Ancestry+ Blemish+MobNo1+MobNo2+ Email+Addr+FName+MName+ FBusiness+MBusiness+ BroNo+SisNo+OwnHouse+Name+ DOB+ PlaceOfBirth+ Business+TimeOfBirth+Gender, Toast.LENGTH_SHORT).show();

        tvName = findViewById(R.id.tvName);
        tvDOB = findViewById(R.id.tvDOB);
        tvPlaceOfBirth = findViewById(R.id.tvPlaceOfBirth);
        tvTimeOfBirth = findViewById(R.id.tvTimeOfBirth);
        tvGender = findViewById(R.id.tvGender);
        tvBusiness = findViewById(R.id.tvBusiness);

        tvFName = findViewById(R.id.tvFName);
        tvMName = findViewById(R.id.tvMName);
        tvFBusiness = findViewById(R.id.tvFBusiness);
        tvMBusiness = findViewById(R.id.tvMBusiness);
        tvBroNo = findViewById(R.id.tvBroNo);
        tvSisNo = findViewById(R.id.tvSisNo);
        tvOwnHouse = findViewById(R.id.tvOwnHouse);

        tvMobNo1 = findViewById(R.id.tvMobNo1);
        tvMobNo2 = findViewById(R.id.tvMobNo2);
        tvEmail = findViewById(R.id.tvEmail);
        tvAddr = findViewById(R.id.tvAddr);

        tvAstroName = findViewById(R.id.tvAstroName);
        tvStar = findViewById(R.id.tvStar);
        tvSection = findViewById(R.id.tvSection);
        tvAncestry = findViewById(R.id.tvAncestry);
        tvBlemish = findViewById(R.id.tvBlemish);

        tvName.setText(Name);
        tvDOB.setText(DOB);
        tvPlaceOfBirth.setText(PlaceOfBirth);
        tvTimeOfBirth.setText(TimeOfBirth);
        tvGender.setText(Gender);
        tvBusiness .setText(Business);

        tvFName .setText(FName);
        tvMName.setText(MName);
        tvFBusiness.setText(FBusiness);
        tvMBusiness.setText(MBusiness);
        tvBroNo.setText(BroNo);
        tvSisNo.setText(SisNo);
        tvOwnHouse.setText(OwnHouse);

        tvMobNo1.setText(MobNo1);
        tvMobNo2.setText(MobNo2);
        tvEmail .setText(Email);
        tvAddr .setText(Addr);

        tvAstroName .setText(AstroName);
        tvStar .setText(Star);
        tvSection .setText(Section);
        tvAncestry .setText(Ancestry);
        tvBlemish .setText(Blemish);

        LLPD = findViewById(R.id.LLPD);
        LLFD = findViewById(R.id.LLFD);
        LLCD = findViewById(R.id.LLCD);
        LLHD = findViewById(R.id.LLHD);
    }

/*
    public void onNextLLPD(View view) {
        LLPD.setVisibility(View.GONE);
        LLFD.setVisibility(View.VISIBLE);
    }

    public void onNextLLFD(View view) {
        LLFD.setVisibility(View.GONE);
        LLCD.setVisibility(View.VISIBLE);
    }

    public void onNextLLCD(View view) {
        LLCD.setVisibility(View.GONE);
        LLHD.setVisibility(View.VISIBLE);
    }
*/


    public void onConfirm(View view) {
        Intent intent = new Intent(Confirmation.this,PdfCreationActivity.class);

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
}
