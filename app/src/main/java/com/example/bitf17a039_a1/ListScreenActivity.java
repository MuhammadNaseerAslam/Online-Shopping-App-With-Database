package com.example.bitf17a039_a1;


        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
        import android.view.ActionMode;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AbsListView;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ListView;
        import java.util.ArrayList;
        import android.widget.SearchView;
        import android.widget.Toast;

        import com.google.android.material.floatingactionbutton.FloatingActionButton;
        import com.google.android.material.snackbar.Snackbar;



public class ListScreenActivity extends AppCompatActivity implements
        ListView.OnItemClickListener,
        ListView.OnItemLongClickListener, SearchView.OnQueryTextListener  {
    SearchView sv;
    public SQLiteDatabase dbOrder;
    public ListView lvOrder;
    public ArrayList<Order> customer;
    public ListAdapter laCustomer;
    public ArrayList<Order> selectedCustomer;
    ListScreenActivity ref = this;
    String strArray[] = {};
    String WholeRecord="";
    int k;
    Button Load,Update;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_five);
        Load = (Button) findViewById(R.id.btn_Load);
       // Update = (Button) findViewById(R.id.btn_Update);
        //Recieved data from ThirdpageActivity
        Intent i = getIntent();
        final String custFirstName=i.getStringExtra("FirstName");
        final String custLastName=i.getStringExtra("LastName");
        final String custEmail=i.getStringExtra("MailId");
        final String custContact=i.getStringExtra("ContactNo");
        final String custCompany=i.getStringExtra("Company");
        final String custZipCode=i.getStringExtra("Zip Code");
        final String custState=i.getStringExtra("State");
        final String custCity=i.getStringExtra("City");
        final String custBoxes=i.getStringExtra("Boxes");
       final String custTime=i.getStringExtra("Time");
        final String custUpdate=i.getStringExtra("Update");
        final String Index = i.getStringExtra("Index");

        lvOrder = (ListView) findViewById(R.id.lvCustomer);
        customer = new ArrayList<Order>();
        selectedCustomer = new ArrayList<Order>();
        laCustomer = new com.example.bitf17a039_a1.ListAdapter(this, customer, selectedCustomer);
        


        dbOrder = openOrCreateDatabase("customer_db", MODE_PRIVATE, null);
        dbOrder.execSQL("CREATE TABLE IF NOT EXISTS customer" +
                "(custID INTEGER PRIMARY KEY AUTOINCREMENT,custFName TEXT, custLName TEXT, custEmail TEXT, custContact TEXT,custCompany TEXT,custZipCode TEXT,custState TEXT,custCity TEXT,custBoxes TEXT, custDateTime TEXT);");

        //Loading Item in List view
        RefreshListView();


        sv = (SearchView) findViewById(R.id.search);
        sv.setOnQueryTextListener(this);


        lvOrder.setAdapter(laCustomer);
        lvOrder.setOnItemClickListener(this);
        lvOrder.setOnItemLongClickListener(this);

        Load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order o = new Order();
                o.pd = new PersonalData();
                o.cd = new CompanyDetail();
                if(custUpdate==null)
                {
                    Cursor c = dbOrder.rawQuery("SELECT MAX(custID) from customer", null);
                    c.moveToFirst();
                    int id = c.getInt(0) + 1;
                    o.pd.ID = id;
                    o.pd.Fname = custFirstName;
                    o.pd.Lname = custLastName;
                    o.pd.Email = custContact;
                    o.pd.Contact = custContact;
                    o.cd.Company = custCompany;
                    o.cd.ZipCode = custZipCode;
                    o.cd.State = custState;
                    o.cd.City = custCity;
                    o.cd.Boxes = custBoxes;
                    o.cd.DateAndTime = custTime;
                    customer.add(o);
                    laCustomer.notifyDataSetChanged();
                    dbOrder.execSQL("INSERT INTO customer VALUES(NULL,'" + o.pd.Fname + "', '" + o.pd.Lname + "', '" + o.pd.Email + "','" + o.pd.Contact + "','" + o.cd.Company + "','" + o.cd.ZipCode + "','" + o.cd.State + "','" + o.cd.City + "','" + o.cd.Boxes + "','" + o.cd.DateAndTime + "');");
                    Toast.makeText(ref, "Inserted", Toast.LENGTH_LONG).show();
                }
                else if(custUpdate!=null) {
                    o.pd.ID = Integer.parseInt(custUpdate);
                    o.pd.Fname = custFirstName;
                    o.pd.Lname = custLastName;
                    o.pd.Email = custContact;
                    o.pd.Contact = custContact;
                    o.cd.Company = custCompany;
                    o.cd.ZipCode = custZipCode;
                    o.cd.State = custState;
                    o.cd.City = custCity;
                    o.cd.Boxes = custBoxes;
                    o.cd.DateAndTime = custTime;
                    dbOrder.execSQL("UPDATE customer SET custFName= '" + o.pd.Fname + "', custLName= '" + o.pd.Lname + "', custEmail = '" + o.pd.Email + "',custContact = '" + o.pd.Contact + "',custCompany= '" + o.cd.Company + "',custZipCode= '" + o.cd.ZipCode + "',custState= '" + o.cd.State + "',custCity = '" + o.cd.City + "',custBoxes = '" + o.cd.Boxes + "',custDateTime = '" + o.cd.DateAndTime + "' where custID= " + Integer.parseInt(custUpdate) + "");
                    laCustomer.notifyDataSetChanged();
                    Toast.makeText(ref, "Updated", Toast.LENGTH_SHORT).show();
                }


            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "New Order", Snackbar.LENGTH_LONG)
                       // .setAction("Action", null).show();
                Intent i = new Intent(ListScreenActivity.this, PageTwoActivity.class);
                 startActivity(i);
            }
        });





    }

  public void RefreshListView()
  {
      Cursor c = dbOrder.rawQuery("SELECT custID,custFName,custLName,custEmail,custContact,custCompany,custZipCode,custState,custCity,custBoxes,custDateTime from customer", null);
      c.moveToFirst();

      for (int k = 0; k < c.getCount(); k++) {
          Order o = new Order();
          o.pd=new PersonalData();
          o.cd=new CompanyDetail();
          o.pd.ID=c.getInt(0);
          o.pd.Fname = c.getString(1);
          o.pd.Lname = c.getString(2);
          o.pd.Email =c.getString(3);
          o.pd.Contact = c.getString(4);
          o.cd.Company=c.getString(5);
          o.cd.ZipCode=c.getString(6);
          o.cd.State=c.getString(7);
          o.cd.City=c.getString(8);
          o.cd.Boxes=c.getString(9);
          o.cd.DateAndTime=c.getString(10);
          customer.add(o);
          c.moveToNext();
      }
  }


    @Override
    public boolean onQueryTextSubmit(String s) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        laCustomer.getFilter().filter(s);
        return false;
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent i = new Intent(ListScreenActivity.this, OrderDetail.class);
        Order o=customer.get(position);

        i.putExtra("ID",Integer.toString(o.pd.ID));
        i.putExtra("FirstName",o.pd.Fname);
        i.putExtra("LastName",o.pd.Lname);
        i.putExtra("MailId",o.pd.Email);
        i.putExtra("ContactNo",o.pd.Contact);
        i.putExtra("Company",o.cd.Company);
        i.putExtra("Zip Code",o.cd.ZipCode);
        i.putExtra("State",o.cd.State);
        i.putExtra("City", o.cd.City);
        i.putExtra("Boxes",o.cd.Boxes);
        i.putExtra("Time",o.cd.DateAndTime);
        startActivity(i);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
    {

        lvOrder.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        lvOrder.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener()
        {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu)
            {
                mode.getMenuInflater().inflate(R.menu.main_menu, menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (checked) {

                    selectedCustomer.add(customer.get(position));
                } else {
                    selectedCustomer.remove(customer.get(position));
                }

                laCustomer.notifyDataSetChanged();

                mode.setTitle(lvOrder.getCheckedItemCount() + " Selected");
            }

            @Override
            public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.delete) {


                    AlertDialog.Builder simple = new AlertDialog.Builder(ref);
                    simple.setTitle("Are you sure to delete Order ??");
                    simple.setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(), "So you did not wanna to delete Order", Toast.LENGTH_SHORT).show();

                        }
                    });
                    simple.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {

                            Order o = selectedCustomer.get(position);
                            dbOrder.execSQL("DELETE FROM customer WHERE  custID= "+o.pd.ID+"" );
                            customer.remove(position);
                            laCustomer.notifyDataSetChanged();
                            Toast.makeText(ref, "Deleted", Toast.LENGTH_LONG).show();
                            mode.finish();

                        }
                    });
                    simple.setCancelable(false);
                    simple.create();
                    simple.show();


                    return true;
                } else if (item.getItemId() == R.id.edit) {
                    int count = selectedCustomer.size();
                    if(count>1)
                    {
                        Toast.makeText(getBaseContext(), "Sorry to say you can't edit multiple Items at a time", Toast.LENGTH_LONG).show();
                    }
                   else if(count==1) {
                        Intent i = new Intent(ref, PageTwoActivity.class);

                        Order o = selectedCustomer.get(position);
                        i.putExtra("Position",Integer.toString(o.pd.ID));
                        i.putExtra("FirstName",o.pd.Fname);
                        i.putExtra("LastName",o.pd.Lname);
                        i.putExtra("MailId",o.pd.Email);
                        i.putExtra("ContactNo",o.pd.Contact);
                        i.putExtra("Company",o.cd.Company);
                        i.putExtra("Zip Code",o.cd.ZipCode);
                        i.putExtra("State",o.cd.State);
                        i.putExtra("City", o.cd.City);
                        i.putExtra("Boxes",o.cd.Boxes);
                        i.putExtra("Time",o.cd.DateAndTime);
                        i.putExtra("Index",position);
                        startActivity(i);

                    }

                    return true;
                }

                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                int count = selectedCustomer.size();
                for (int i = 0; i < count; i++) {
                    selectedCustomer.remove(0);
                }

                laCustomer.notifyDataSetChanged();
            }
        });

        return true;
    }


}

