package com.veide.vince.elementz_03;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vince on 3/24/16.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private static final String TABLE_CONTACT = "contacts";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";

    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version){
        super(context, "contactDB.db", factory, 1);

    }

    public void addContact(Contact contact){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_EMAIL, contact.getEmail());
        values.put(COLUMN_PHONE, contact.getPhone());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CONTACT, null, values);
        db.close();
    }

    public Contact findContact(String name){

        String query = "SELECT * FROM " + TABLE_CONTACT + " WHERE " + COLUMN_NAME + " = " + "\"" + name + "\"";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Contact contact = new Contact();

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            contact.setName(cursor.getString(1));
            contact.setEmail(cursor.getString(2));
            contact.setPhone(cursor.getString(3));
            cursor.close();
        } else{
            contact = null;
        }

        db.close();
        return contact;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE CONTACTS ( " +
                "ID INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PHONE + " TEXT )";
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST CONTACTS");
        onCreate(db);
    }
}
