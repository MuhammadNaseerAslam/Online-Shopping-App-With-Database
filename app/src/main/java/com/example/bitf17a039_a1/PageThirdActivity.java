package com.example.bitf17a039_a1;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
        import java.io.FileOutputStream;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PageThirdActivity extends  AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btnSubmit,btnPreviousthird,btnNextthird,btnSendMessage,btnSendEmail;
    EditText edtCompanyName,edtZipCode,edtState,edtCity;
    Spinner spBoxes;
    String data;
   TextView tv;
    PageThirdActivity ref;
   private int pos=-1;
    private String records="record";

    //DataBase related






    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_four);
        edtCompanyName=(EditText)findViewById(R.id.edtCompany);
        edtZipCode=(EditText)findViewById(R.id.edtZip);
        edtState= (EditText)findViewById(R.id.edtState);
        edtCity=(EditText)findViewById(R.id.edtCity);
        tv= (TextView)findViewById(R.id.tvResult);
        spBoxes=(Spinner) findViewById(R.id.spinnerBoxes);
        //btnSubmit=(Button)findViewById(R.id.btn_submit);
        btnPreviousthird=(Button)findViewById(R.id.btn_previous_third);
        btnNextthird=(Button)findViewById(R.id.btn_next4);
        btnSendMessage = (Button) findViewById(R.id.btnSendMessage);
        btnSendEmail = (Button) findViewById(R.id.btnSendEmail);

        //Creating Database





        //Recieving data

       Intent i=getIntent();
        final String Fname=i.getStringExtra("FirstName");
        final String Lname=i.getStringExtra("LastName");
        final String Email=i.getStringExtra("MailId");
        final String Contact=i.getStringExtra("ContactNo");
        final String Company=i.getStringExtra("Company");
        final String Zipcode=i.getStringExtra("Zip Code");
        final String State=i.getStringExtra("State");
        final String City=i.getStringExtra("City");
        final String Boxes=i.getStringExtra("Boxes");
        final String Time=i.getStringExtra("Time");
        final String Position=i.getStringExtra("Position");
        final String Index=i.getStringExtra("Index");
        //final int pos=Integer.parseInt(Position);
       // if(Position!=null) {
            edtCompanyName.setText(Company);
            edtZipCode.setText(Zipcode);
            edtState.setText(State);
            edtCity.setText(City);

        //}

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spineritem,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBoxes.setAdapter(adapter);
        spBoxes.setOnItemSelectedListener(this);


        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendMessage();

            }
        });
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendEmail();

            }
        });
        btnNextthird.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                String CompanyName=edtCompanyName.getText().toString();
                String ZipCode=edtZipCode.getText().toString();
                String State=edtState.getText().toString();
                String City=edtCity.getText().toString();
                String PossibleBoxes=spBoxes.getSelectedItem().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                String currentDateandTime = sdf.format(new Date());

                if(CompanyName.equals("")||ZipCode.equals("")||State.equals("")||City.equals("")||PossibleBoxes.equals(""))
                {
                    Toast.makeText(PageThirdActivity.this,"Please Fill all the fields",Toast.LENGTH_LONG).show();
                }
                else
                {

                    Intent i = new Intent(PageThirdActivity.this, ListScreenActivity.class);

                    i.putExtra("FirstName",Fname);
                    i.putExtra("LastName",Lname);
                    i.putExtra("MailId",Email);
                    i.putExtra("ContactNo",Contact);
                    i.putExtra("Company",CompanyName);
                    i.putExtra("Zip Code",ZipCode);
                    i.putExtra("State",State);
                    i.putExtra("City",City);
                    i.putExtra("Boxes",PossibleBoxes);
                    i.putExtra("Time",currentDateandTime);
                    i.putExtra("Update",Position);
                    i.putExtra("Index",Index);
                    startActivity(i);




                }
            }
        });
        btnPreviousthird.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v) {
                edtCompanyName.getText().clear();
                edtZipCode.getText().clear();
                edtState.getText().clear();
                edtCity.getText().clear();
                finish();
            }
        });



    }
    private void sendMessage()
    {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("smsto:"));
        i.setType("vnd.android-dir/mms-sms");
        i.putExtra("address", "0306240371");
        i.putExtra("sms_body"  , "Dear you ordered from our Online Shopping Mall Thank You!");

        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if(PageThirdActivity.this.checkSelfPermission(Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED) {
                    startActivity(i);
                    finish();
                }
                else
                {

                }
            }else
            {
                startActivity(i);
                finish();
            }

        }
        catch (Exception ex) {
        }
    }

    private void sendEmail()
    {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setData(Uri.parse("mailto:"));
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL, "naseeraslam456@gmail.com");
        i.putExtra(Intent.EXTRA_CC, "bitf17a039@pucite.edu.pk");
        i.putExtra(Intent.EXTRA_SUBJECT, "Online Shopping");
        i.putExtra(Intent.EXTRA_TEXT, "Thank for Online Shopping from our Mall");

        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
            finish();
        }
        catch (Exception ex) {
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }


}

