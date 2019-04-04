package com.project.naveen.muhurtham;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    EditText name, dob, placeofbirth, business,timeofbirth;
    RadioGroup rgGender;
    TextView tvDOB, tvTimeOfBirth;

    String Name, DOB, PlaceOfBirth, Business,TimeOfBirth,Gender;

    final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100,MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 101, CAMERA_PERMISSION_REQ = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("Error e","E error");
        Log.d("Error d","D error");
        Log.i("Error i","i error");
        Log.v("Error v","v error");
        Log.w("Error w","w error");

        name = findViewById(R.id.etName);
        //dob = findViewById(R.id.etDOB);
        placeofbirth = findViewById(R.id.etPlaceOfBirth);
        //timeofbirth = findViewById(R.id.etTimeOfBirth);
        business = findViewById(R.id.etBusiness);

        tvDOB = findViewById(R.id.tvDOB);
        tvTimeOfBirth = findViewById(R.id.tvTimeOfBirth);

        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day, month, year;
                Calendar calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,R.style.TimeDatePickerTheme,MainActivity.this,
                        year,month,day);
                datePickerDialog.show();
            }
        });
        tvTimeOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour, minute;
                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR);
                minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity.this,R.style.TimeDatePickerTheme,MainActivity.this,
                        hour,minute, false);
                timePickerDialog.show();
            }
        });

        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rbMale:
                        Gender = "Male";
                        break;
                    case R.id.rbFemale:
                        Gender = "Female";
                        break;
                }
            }
        });

        askPermission();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        DOB = String.valueOf(i2)+"-"+String.valueOf(i1+1)+"-"+String.valueOf(i);
        tvDOB.setText(DOB);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        if(i<=12){
            TimeOfBirth = String.valueOf(i)+":"+String.valueOf(i1)+" AM";
        }
        else {
            i = i-12;
            TimeOfBirth = String.valueOf(i)+":"+String.valueOf(i1)+" PM";
        }

        tvTimeOfBirth.setText(TimeOfBirth);
    }

    private void askPermission() {
        //WRITE_EXTERNAL_STORAGE Permission
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            if( Environment.getExternalStorageDirectory().canWrite()) {
                //   Toast.makeText(getApplicationContext(), "The path is writable", Toast.LENGTH_LONG).show();
            }
            else {
                //   Toast.makeText(getApplicationContext(), "The path is not writable and asking permission", Toast.LENGTH_LONG).show();

                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        //         Toast.makeText(getApplicationContext(), "Grant the permission otherwise the app doesn't work", Toast.LENGTH_LONG).show();
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    // Permission has already been granted
                }

            }
        }
        else {
            //  Toast.makeText(getApplicationContext(), "MEDIA_MOUNTED not equal", Toast.LENGTH_LONG).show();
        }

        //READ_EXTERNAL_STORAGE Permission
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            if( Environment.getExternalStorageDirectory().canRead()) {
                //   Toast.makeText(getApplicationContext(), "The path is writable", Toast.LENGTH_LONG).show();
            }
            else {
                //   Toast.makeText(getApplicationContext(), "The path is not writable and asking permission", Toast.LENGTH_LONG).show();

                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        //         Toast.makeText(getApplicationContext(), "Grant the permission otherwise the app doesn't work", Toast.LENGTH_LONG).show();
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    // Permission has already been granted
                }

            }
        }
        else {
            //  Toast.makeText(getApplicationContext(), "MEDIA_MOUNTED not equal", Toast.LENGTH_LONG).show();
        }

        //Camera Permission
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //         Toast.makeText(getApplicationContext(), "Grant the permission otherwise the app doesn't work", Toast.LENGTH_LONG).show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_REQ);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case CAMERA_PERMISSION_REQ: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public void nextPage(View view) {
        Name = name.getText().toString();
        //DOB = dob.getText().toString();
        DOB = tvDOB.getText().toString();
        PlaceOfBirth = placeofbirth.getText().toString();
        Business = business.getText().toString();
        //TimeOfBirth = timeofbirth.getText().toString();
        TimeOfBirth = tvTimeOfBirth.getText().toString();

        //Toast.makeText(getApplicationContext(),Name+ DOB+ PlaceOfBirth+ Business+TimeOfBirth+Gender, Toast.LENGTH_LONG).show();


        if(Name.trim().length()>0 && DOB.trim().length()>0&&PlaceOfBirth.trim().length()>0
                && Business.trim().length()>0 && TimeOfBirth.trim().length()>0 ){

            //Intent intent = new Intent(MainActivity.this,Family.class);
            Intent intent = new Intent(MainActivity.this,PdfCreationActivity.class);
            Bundle extras = new Bundle();
            extras.putString("Name",Name);
            extras.putString("DOB",DOB);
            extras.putString("PlaceOfBirth",PlaceOfBirth);
            extras.putString("TimeOfBirth",TimeOfBirth);
            extras.putString("Business",Business);
            extras.putString("Gender",Gender);
            intent.putExtras(extras);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(),"Enter all the details", Toast.LENGTH_LONG).show();
        }
    }

}
