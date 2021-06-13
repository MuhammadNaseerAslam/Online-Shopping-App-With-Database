package com.example.bitf17a039_a1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderDetail  extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11;
    Button btnCancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);
        t1 = (TextView) findViewById(R.id.tvFName);
        t2 = (TextView) findViewById(R.id.tvLName);
        t3 = (TextView) findViewById(R.id.tvEmail);
        t4 = (TextView) findViewById(R.id.tvContact);
        t5 = (TextView) findViewById(R.id.tvCompany);
        t6 = (TextView) findViewById(R.id.tvZipCode);
        t7 = (TextView) findViewById(R.id.tvState);
        t8 = (TextView) findViewById(R.id.tvCity);
        t9=(TextView) findViewById(R.id.tvBoxes);
        t10=(TextView) findViewById(R.id.tvTime);
        t11 = (TextView) findViewById(R.id.tvFName);
        btnCancel=(Button)findViewById(R.id.btn_cancel);
        //Recieved data from LastpageActivity ListView
        Intent i = getIntent();
        String OrderId=i.getStringExtra("ID");
        String Fname = i.getStringExtra("FirstName");
        String Lname = i.getStringExtra("LastName");
        String Email = i.getStringExtra("MailId");
        String Contact = i.getStringExtra("ContactNo");
        String Company = i.getStringExtra("Company");
        String ZipCode = i.getStringExtra("Zip Code");
        String State = i.getStringExtra("State");
        String City = i.getStringExtra("City");
        String Boxes = i.getStringExtra("Boxes");
        String Time=i.getStringExtra("Time");


        //Show on 5th Page Activity

        t1.setText("Order Id: "+OrderId);
        t2.setText("First Name:  " + Fname);
        t3.setText("Last Name:  "+Lname);
        t4.setText("Email:  " + Email);
        t5.setText("Contact : " + Contact);
       t6.setText("Company:  " + Company);
       t7.setText("Address:  " + City + " (" + ZipCode + ")," + State);
       t8.setText("No of Boxes: " + Boxes);
       t9.setText("Time:  "+Time);



        btnCancel.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {

                finish();
            }
        });
    }

}
