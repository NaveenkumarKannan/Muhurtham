package com.project.naveen.muhurtham;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSmartCopy;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

public class PdfCreationActivity extends AppCompatActivity {
    String Name, DOB, PlaceOfBirth, Business,TimeOfBirth,Gender;
    String FName,MName, FBusiness,MBusiness, BroNo,SisNo,OwnHouse;
    String MobNo1,MobNo2, Email,Addr;
    String AstroName, Star, Section, Ancestry, Blemish;

    final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100,MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 101, CAMERA_PERMISSION_REQ = 102;

    ImageView imageView, ivHoroscope;
    private int PICK_IMAGE= 100,CAPTURE_IMAGE = 101,PICK_HORO_IMAGE = 102,CAPTURE_HORO_IMAGE = 103, REQUEST_CODE_DOC = 104;

    Bitmap bitmapPhoto, bitmapHoroscope;

    BaseColor orange = WebColors.getRGBColor("#ff471a");
    BaseColor blue = WebColors.getRGBColor("#4747d1");

    String docFilePath,createFilePath;
    Button btnUpHorImg, btnUpHorPdf, btnGenPdf;
    String pdfFileName;

    ProgressDialog dialog;

    String mimeType = "application/pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_creation);

        //imageView = findViewById(R.id.imageView);
        ivHoroscope = findViewById(R.id.ivHoroscope);
        btnUpHorImg = findViewById(R.id.btnUpHorImg);
        btnUpHorPdf = findViewById(R.id.btnUpHorPdf);
        btnGenPdf = findViewById(R.id.btnGenPdf);

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

        pdfFileName = Name + DOB + ".pdf";
        createFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Muhurtham";
        //Toast.makeText(getApplicationContext(),AstroName+ Star+ Section+ Ancestry+ Blemish+MobNo1+MobNo2+ Email+Addr+FName+MName+ FBusiness+MBusiness+ BroNo+SisNo+OwnHouse+Name+ DOB+ PlaceOfBirth+ Business+TimeOfBirth+Gender, Toast.LENGTH_SHORT).show();

        askPermission();

        Log.e("Name","Msg: "+Name);
    }



    // CreatePdf Code link => https://trinitytuts.com/create-pdf-file-in-android/
    // YouTube o/p link => https://www.youtube.com/watch?v=BXXBMcVhF0w
    public void createPdf() {
        dialog = new ProgressDialog(this,R.style.TimeDatePickerTheme);
        dialog.setMessage("Creating PDF...");
        dialog.setCancelable(true);
        dialog.show();

        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {

                File dir = new File(createFilePath);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                //Log.e("PDFCreator", "PDF Path: " + createFilePath);
                Document document = new Document();
                try {
                    // SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                    //File file = new File(dir, "Muhurtham PDF" + sdf.format(Calendar.getInstance().getTime()) + ".pdf");
                    File file;
                    FileOutputStream fOut;
                    if(docFilePath == null){
                        file = new File(dir, pdfFileName);
                        fOut = new FileOutputStream(file);
                    }
                    else {
                        file = new File(dir, DOB + Name + ".pdf");
                        fOut = new FileOutputStream(file);
                    }


                    //simply create a pdf file
                    PdfWriter writer = PdfWriter.getInstance(document, fOut);

                    //open the document
                    //document.setMargins(10,5,5,5);
                    document.open();
/*
            //Set Page border
            Rectangle rect= new Rectangle(document.getPageSize());
            rect.setBorder(2);
            rect.setBorder(Rectangle.BOX);
            rect.setBorderWidth(10);
            rect.setBorderColor(BaseColor.BLACK);
            document.add(rect);
*/
                    PdfPTable table, pt;
                    PdfPCell cell;
                    Font font = FontFactory.getFont("Arial Unicode MS", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

                    pt = new PdfPTable(3);
                    float[] wid = new float[]{20, 30, 50};
                    pt.setWidths(wid);

                    // Inserting Image in PDF
                    cell = new PdfPCell();
                    cell.setBorder(PdfPCell.TOP|PdfPCell.BOTTOM|PdfPCell.LEFT);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    ByteArrayOutputStream DrawableStream = new ByteArrayOutputStream();
                    Bitmap DrawableBitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.ganesh);
                    DrawableBitmap.compress(Bitmap.CompressFormat.PNG, 100 , DrawableStream);
                    Image DrawableImg = Image.getInstance(DrawableStream.toByteArray());
                    DrawableImg.setAlignment(Image.ALIGN_LEFT);
                    pt.addCell(DrawableImg);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setColspan(2);
                    cell.setBorder(PdfPCell.TOP|PdfPCell.BOTTOM|PdfPCell.RIGHT);
                    cell.addElement(new Paragraph("Your Company தந்தை",font));
                    cell.addElement(new Paragraph("Astro-Name"));
                    cell.addElement(new Paragraph("Your Address Your City"));
                    cell.addElement(new Paragraph("Your Country"));
                    cell.addElement(new Paragraph("Ph:Phone&Mobile email:your mail-id"));
                    pt.addCell(cell);
                    //Toast.makeText(getApplicationContext(), "Bitmap is not null ", Toast.LENGTH_LONG).show();

                    //Passport Size Image
            /*
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.TOP|PdfPCell.BOTTOM|PdfPCell.LEFT);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            //cell.setFixedHeight(100f);
            ByteArrayOutputStream PassportStream = new ByteArrayOutputStream();
            bitmapPhoto.compress(Bitmap.CompressFormat.JPEG, 100 , PassportStream);
            Image PassportImg = Image.getInstance(PassportStream.toByteArray());
            PassportImg.scaleAbsolute(20,30);
            PassportImg.setAlignment(Image.ALIGN_LEFT);
            pt.addCell(PassportImg);
            */

                    table = new PdfPTable(2);
                    //table.setHorizontalAlignment(Element.ALIGN_LEFT);
                    //table.setSpacingAfter(5);
                    float[] fl = new float[]{20, 30};
                    table.setWidths(fl);

                    cell = new PdfPCell(new Phrase("Name தந்தை",font));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+Name));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("  "+Gender));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("DOB"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+DOB));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Place of Birth"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+PlaceOfBirth));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Time of Birth"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+TimeOfBirth));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Business"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+Business));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Father"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+FName));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Father's Business"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+FBusiness));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Mother"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+MName));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);
            /*
            cell = new PdfPCell(new Phrase("Business"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(": "+MBusiness));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);
            */

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("No. of Brothers"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+BroNo));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("No. of Sisters"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+SisNo));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Own House"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+OwnHouse));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    PdfPTable table1;
                    table1 = new PdfPTable(2);

                    float[] f2 = new float[]{20, 30};
                    table1.setWidths(f2);

                    cell = new PdfPCell(new Phrase("Reg No."));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(": 1004"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

            /*
            cell = new PdfPCell(new Phrase("Reg-Date"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(PdfPCell.NO_BORDER);
            table1.addCell(cell);

            cell = new PdfPCell(new Phrase(": 21-Jan-2016"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(PdfPCell.NO_BORDER);
            table1.addCell(cell);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

            cell = new PdfPCell(new Phrase("Report Dt"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(PdfPCell.NO_BORDER);
            table1.addCell(cell);

            cell = new PdfPCell(new Phrase(": "+sdf.format(Calendar.getInstance().getTime())));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(PdfPCell.NO_BORDER);
            table1.addCell(cell);

            cell = new PdfPCell(new Phrase("Ref No."));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(PdfPCell.NO_BORDER);
            table1.addCell(cell);

            cell = new PdfPCell(new Phrase(": 1004"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(PdfPCell.NO_BORDER);
            table1.addCell(cell);
            */

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase("Mobile No.1"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+MobNo1));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase("Mobile No.2"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+MobNo2));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase("Email ID"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+Email));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase("Address"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+Addr));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(""));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setColspan(2);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase("Astro Name"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+AstroName));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase("Star"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+Star));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase("Section"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+Section));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase("Ancestry"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+Ancestry));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase("Blemish"));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Phrase(": "+Blemish));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(PdfPCell.NO_BORDER);
                    table1.addCell(cell);

                    cell = new  PdfPCell();
                    cell.setBorder(PdfPCell.BOTTOM|PdfPCell.LEFT);
                    cell.setColspan(2);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.addElement(table);
                    pt.addCell(cell);

                    cell = new  PdfPCell();
                    cell.setBorder(PdfPCell.BOTTOM|PdfPCell.RIGHT);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.addElement(table1);
                    pt.addCell(cell);

                    if(docFilePath == null){
                        ByteArrayOutputStream upLoadImgStream = new ByteArrayOutputStream();
                        bitmapHoroscope.compress(Bitmap.CompressFormat.JPEG, 100 , upLoadImgStream);
                        Image upLoadImg = Image.getInstance(upLoadImgStream.toByteArray());
                        upLoadImg.setAlignment(Image.ALIGN_CENTER);
                        upLoadImg.scaleAbsolute(1,1);
                        cell = new PdfPCell(upLoadImg,true);
                        cell.setColspan(3);
                        cell.setBorder(PdfPCell.TOP|PdfPCell.BOTTOM|PdfPCell.LEFT|PdfPCell.RIGHT);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pt.addCell(cell);
                    }

                    document.add(pt);

                    //Toast.makeText(getApplicationContext(), "Created...", Toast.LENGTH_LONG).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "FileNotFoundException"+e, Toast.LENGTH_LONG).show();
                } catch (DocumentException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "DocumentException"+e, Toast.LENGTH_LONG).show();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "MalformedURLException"+e, Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "IOException"+e, Toast.LENGTH_LONG).show();
                } finally {
                    document.close();
                }
                if(docFilePath == null){
                    dialog.dismiss();

                    File file = new File(dir, Name + DOB + ".pdf");

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.project.naveen.muhurtham", file);
                        intent.setDataAndType(contentUri, mimeType);
                    } else {
                        intent.setDataAndType(Uri.fromFile(file), mimeType);
                    }
                    startActivity(intent);
                }
                else {
                    mergePdf();
                    dialog.dismiss();
                    File file = new File(dir, Name + DOB + ".pdf");

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.project.naveen.muhurtham", file);
                        intent.setDataAndType(contentUri, mimeType);
                    } else {
                        intent.setDataAndType(Uri.fromFile(file), mimeType);
                    }
                    startActivity(intent);
                }

            }
        });
        mThread.start();
    }

    public void openGalleryPhoto(View view) {
        //for try1
        //startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE);

        //for try 2 and 3  => Today's best choice
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);
    }

    public void openCameraPhoto(View view) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Muhurtham/images";
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        File imgae_file = new File(file,"temp_image.jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgae_file));
        startActivityForResult(intent,CAPTURE_IMAGE);
    }

    public void openGalleryHoroscope(View view) {
        btnGenPdf.setVisibility(View.VISIBLE);
        btnUpHorPdf.setVisibility(View.GONE);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_HORO_IMAGE);
    }

    public void openCameraHoroscope(View view) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Muhurtham/images";
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        File imgae_file = new File(file,"temp_image1.jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgae_file));
        startActivityForResult(intent,CAPTURE_HORO_IMAGE);
    }


    public void getDocument(View view){
        btnUpHorImg.setVisibility(View.GONE);

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //To view pdf only
        intent.setType("application/pdf");
        //To view both pdf and word document
        //intent.setType("application/msword,application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, REQUEST_CODE_DOC);
    }

    public void generatePdf(View view) {
        createPdf();
    }

    public void mergePdf() {

        File dir = new File(createFilePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //Log.e("PDFCreator", "PDF Path: " + createFilePath);
        Document document = new Document();
        try {
            File file;
            FileOutputStream fOut;
            if (docFilePath == null) {
                file = new File(dir, pdfFileName);
                fOut = new FileOutputStream(file);
            } else {
                file = new File(dir, DOB + Name + ".pdf");
                fOut = new FileOutputStream(file);
            }

            //Merge two pdf into one pdf
            PdfCopy copy = new PdfCopy(document, fOut);

            //open the document
            document.open();

            Map<String, PdfReader> filesToMerge;
            filesToMerge = new TreeMap<String, PdfReader>();
            filesToMerge.put("01 Hello World", new PdfReader(docFilePath));

            PdfCopy.PageStamp stamp;

            int n;
            int pageNo = 0;
            PdfImportedPage page;
            Chunk chunk;
            Map<Integer, String> toc = new TreeMap<Integer, String>();
            for (Map.Entry<String, PdfReader> entry : filesToMerge.entrySet()) {
                n = entry.getValue().getNumberOfPages();
                toc.put(pageNo + 1, entry.getKey());
                for (int i = 0; i < n; ) {
                    pageNo++;
                    page = copy.getImportedPage(entry.getValue(), ++i);
                    stamp = copy.createPageStamp(page);
                    chunk = new Chunk(String.format("Page %d", pageNo));
                    if (i == 1)
                        chunk.setLocalDestination("p" + pageNo);
                    ColumnText.showTextAligned(stamp.getUnderContent(),
                            Element.ALIGN_RIGHT, new Phrase(chunk),
                            559, 810, 0);
                    stamp.alterContents();
                    copy.addPage(page);
                }
            }
            document.close();
            for (PdfReader r : filesToMerge.values()) {
                r.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //press ctrl+o
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //imageView.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ganesh));
        if(resultCode == RESULT_OK && requestCode == CAPTURE_IMAGE){
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Muhurtham/images/temp_image.jpg";
            //imageView.setImageDrawable(Drawable.createFromPath(path));

            bitmapPhoto = decodeSampledBitmapFromPath(path, getPx(219), getPx(283));
            //bitmap = decodeSampledBitmapFromPath(path, 20, 20);

            //bitmap = BitmapFactory.decodeFile(path);
            imageView.setImageBitmap(bitmapPhoto);
        }
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            //found mistake=> if(requestCode == RESULT_OK && requestCode == PICK_IMAGE)
            Uri imageUri = data.getData();
            // try1=>fail - Because of mistake
            String[] projection={MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(imageUri,projection,null,null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            //bitmap = BitmapFactory.decodeFile(filePath);
            bitmapPhoto = decodeSampledBitmapFromPath(filePath, getPx(219), getPx(283));

            /*
            try {
                //try3=>fail - Because of mistake
                //InputStream inputStream = getContentResolver().openInputStream(imageUri);
                //bitmap = BitmapFactory.decodeStream(inputStream);

                //try2=>fail - Because of mistake
                //bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            */
            imageView.setImageBitmap(bitmapPhoto);
        }
        if(resultCode == RESULT_OK && requestCode == CAPTURE_HORO_IMAGE){
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Muhurtham/images/temp_image1.jpg";

            bitmapHoroscope = BitmapFactory.decodeFile(path);
            ivHoroscope.setImageBitmap(bitmapHoroscope);
        }
        if(resultCode == RESULT_OK && requestCode == PICK_HORO_IMAGE){
            //found mistake=> if(requestCode == RESULT_OK && requestCode == PICK_IMAGE)
            Uri imageUri = data.getData();
            /*//The below statements are useless for Nougat version
            // try1=>fail - Because of mistake
            String[] projection={MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(imageUri,projection,null,null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            Log.e("Path", ""+filePath);
            //file path can't be get in the Nougat version
            //bitmapHoroscope = BitmapFactory.decodeFile(filePath);
            */

            //try3=>fail - Because of mistake
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmapHoroscope = BitmapFactory.decodeStream(inputStream);
            ivHoroscope.setImageBitmap(bitmapHoroscope);
        }
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_DOC){
            Uri fileuri = data.getData();
            Log.e("fileuri","Msg: "+fileuri);
            docFilePath = getFileNameByUri(this, fileuri);
            Log.e("docFilePath","Msg: "+docFilePath);

            File file = new File(docFilePath);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.project.naveen.muhurtham", file);
                intent.setDataAndType(contentUri, mimeType);
            } else {
                intent.setDataAndType(Uri.fromFile(file), mimeType);
            }
            startActivity(intent);
        }

    }

    public static Bitmap decodeSampledBitmapFromPath(String path,
                                                     int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * Convert dp in pixels
     * @param dp
     * @return
     */
    public int getPx(int dp){
        float scale = getResources().getDisplayMetrics().density;
        int px = Math.round(dp * scale);
        return px;
    }

// get file path

    private String getFileNameByUri(Context context, Uri uri)
    {
        String filepath = "";//default fileName
        //Uri filePathUri = uri;
        File file;
        if (uri.getScheme().toString().compareTo("content") == 0)
        {
            Cursor cursor = context.getContentResolver().query(uri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.ORIENTATION }, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            cursor.moveToFirst();

            String mImagePath = cursor.getString(column_index);
            cursor.close();
            filepath = mImagePath;

        }
        else if (uri.getScheme().compareTo("file") == 0)
        {
            try
            {
                file = new File(new URI(uri.toString()));
                if (file.exists())
                    filepath = file.getAbsolutePath();

            }
            catch (URISyntaxException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            filepath = uri.getPath();
        }
        return filepath;
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
                if (ContextCompat.checkSelfPermission(PdfCreationActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(PdfCreationActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        //         Toast.makeText(getApplicationContext(), "Grant the permission otherwise the app doesn't work", Toast.LENGTH_LONG).show();
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(PdfCreationActivity.this,
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
                if (ContextCompat.checkSelfPermission(PdfCreationActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(PdfCreationActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        //         Toast.makeText(getApplicationContext(), "Grant the permission otherwise the app doesn't work", Toast.LENGTH_LONG).show();
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(PdfCreationActivity.this,
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
        if (ContextCompat.checkSelfPermission(PdfCreationActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(PdfCreationActivity.this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //         Toast.makeText(getApplicationContext(), "Grant the permission otherwise the app doesn't work", Toast.LENGTH_LONG).show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(PdfCreationActivity.this,
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

}
