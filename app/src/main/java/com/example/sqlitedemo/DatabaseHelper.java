package com.example.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

//    CONSTANT VARIABLES FOR CUSTOMER TABLE
    public static final String CUSTOMER_TABLE_NAME = "customer";
    public static final String CUSTOMER_COLUMN_ID = "id";
    public static final String CUSTOMER_COLUMN_NAME = "name";
    public static final String CUSTOMER_COLUMN_AGE = "age";
    public static final String CUSTOMER_COLUMN_IS_ACTIVE = "isActive";


    public DatabaseHelper(@Nullable Context context) {
        super(context,  "customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TABLE CREATION STATEMENT
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + CUSTOMER_TABLE_NAME + "(" +
                CUSTOMER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CUSTOMER_COLUMN_NAME + " TEXT," +
                CUSTOMER_COLUMN_AGE + " INTEGER," +
                CUSTOMER_COLUMN_IS_ACTIVE + " BOOL" +
                ")";
        db.execSQL(createTableStatement); // executing the query
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableStatement = "DROP TABLE IF EXISTS " + CUSTOMER_TABLE_NAME;
        db.execSQL(dropTableStatement);
        onCreate(db); // reDefining table structure
    }

//    Method for inserting one customer
    public boolean addCustomer(CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CUSTOMER_COLUMN_NAME, customerModel.getName());
        cv.put(CUSTOMER_COLUMN_AGE, customerModel.getAge());
        cv.put(CUSTOMER_COLUMN_IS_ACTIVE, customerModel.isActive());
        long row = db.insert(CUSTOMER_TABLE_NAME, null, cv);

        db.close();
        if (row < 1) {
            return false;
        }

        return true;
    }
//    Method to getting all customers
    public List<CustomerModel> getAllCustomers() {

        List<CustomerModel> customerList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String getAllCustomersQuery = "SELECT * FROM " + CUSTOMER_TABLE_NAME;
        Cursor cursor = db.rawQuery(getAllCustomersQuery, null);

        while(cursor.moveToNext()) {
            Integer customerId = cursor.getInt(0);
            String customerName = cursor.getString(1);
            Integer customerAge = cursor.getInt(2);
            Boolean customerActive = cursor.getInt(3) == 1 ? true : false;
//             adding data to customer object
            CustomerModel customerModel = new CustomerModel(
                    customerId,
                    customerName,
                    customerAge,
                    customerActive
            );
//               Now adding customer model object to the list
            customerList.add(customerModel);
        }
//        Closing the databases
        cursor.close();
        db.close();
        return customerList;
    }

}
