package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Referencing to view widgets
    EditText et_customer_name, et_customer_age;
    SwitchCompat sw_active_customer;
    Button btn_refresh, btn_add, btn_clear_records;
    ListView lv_all_customers;
    ArrayAdapter customerModelArrayAdapter;
    DatabaseHelper databaseHelper;
    List<?> customerModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // connecting
        et_customer_name = findViewById(R.id.et_customer_name);
        et_customer_age = findViewById(R.id.et_customer_age);
        sw_active_customer = findViewById(R.id.sw_active_customer);
        btn_refresh = findViewById(R.id.btn_refresh);
        btn_add = findViewById(R.id.btn_add);
        btn_clear_records = findViewById(R.id.btn_clear_records);
        lv_all_customers = findViewById(R.id.lv_all_customers);

//        fetching db records
        fetchAllRecord();

        // adding event listener to the buttons
        // btn show all
        btn_refresh.setOnClickListener(v -> {
            Boolean isRecord = fetchAllRecord();
            if(!isRecord) {
                Toast.makeText(MainActivity.this, "No Record has been found!", Toast.LENGTH_SHORT).show();
            }
        });
        // btn add
        btn_add.setOnClickListener(v -> {
            try {
                if(et_customer_name.getText().toString().isEmpty()) {
                    throw new Exception();
                }
//                Creating customer model
                CustomerModel customerModel = new CustomerModel(
                        -1,
                        et_customer_name.getText().toString(),
                        Integer.parseInt(et_customer_age.getText().toString()),
                        sw_active_customer.isChecked()
                );

                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                boolean success =  databaseHelper.addCustomer(customerModel);
                if(success) {
                    Toast.makeText(MainActivity.this, "Customer Successfully Added", Toast.LENGTH_SHORT).show();
                    clearInputs();
                    fetchAllRecord();

                }
                else {
                    Toast.makeText(MainActivity.this, "There was an error on inserting the customer.", Toast.LENGTH_SHORT).show();
                }

            }
            catch(Exception e) {
                Toast.makeText(MainActivity.this, "Error in creating on customer!", Toast.LENGTH_SHORT).show();
            }

        });

    btn_clear_records.setOnClickListener(v -> {
        if(fetchAllRecord()) {
            DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            databaseHelper.onUpgrade(db, 1, 2);
            // clearing list
            lv_all_customers.setAdapter(null);
            Toast.makeText(MainActivity.this, "Database cleared!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "There is no record!", Toast.LENGTH_SHORT).show();
        }
    });

    } // onCreate()
//  fetching all customers record
    public Boolean fetchAllRecord() {
        //        fetching db records
        databaseHelper = new DatabaseHelper(MainActivity.this);

        customerModelList = databaseHelper.getAllCustomers();
        if(customerModelList.isEmpty())
        {
            return false;
        }
        else {
            customerModelArrayAdapter = new ArrayAdapter<>(MainActivity.this,
                    android.R.layout.simple_list_item_1, customerModelList );
            lv_all_customers.setAdapter(customerModelArrayAdapter);
        } // else part
        return true;
    } // fetchAllRecord

//    clearing inputs and pointing to first input field after adding new customer
    public void clearInputs() {
        et_customer_name.setText("");
        et_customer_age.setText("");
        et_customer_name.setFocusableInTouchMode(true);
        et_customer_name.requestFocus();
        sw_active_customer.setChecked(false);
        return;
    }

}