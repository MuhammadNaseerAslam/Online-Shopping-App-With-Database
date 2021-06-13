package com.example.bitf17a039_a1;

public class Order
{

    public PersonalData pd;
    public CompanyDetail cd;
    Order(PersonalData p)
    {
        this.pd=p;
    }
    Order()
    {

    }
    Order(PersonalData per,CompanyDetail comp)
    {
       this.pd=per;
        this.cd=comp;
    }
    /*
    public  int Id;
    public String Fname;
    public Order Lname;
    public Order Email;
    public Order Contact;
    public String Company;
    public String ZipCode;
    public String State;
    public String City;
    public String Boxes;
    public String DateAndTime;

     */


}

