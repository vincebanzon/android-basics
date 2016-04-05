package com.veide.vince.elementz_03;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // TODO : function find not working

    public Button savebtn;
    public EditText nametxt, emailtxt, phonetxt;
    public TextView birthdaylbl;
    public ImageButton imageButton;
    public ListView contactList;

    private String name, email, phone, picture;
    private int year_x, month_x, day_x;
    private static final int DIALOG_ID = 0;     // used in calendar
    private static byte[] image;

    private MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new MyDBHandler(this, null, null, 1);
        initialize();
        populateListView();
    }

    private void initialize(){
        // initialize elements
        nametxt = (EditText) findViewById(R.id.textName);
        emailtxt = (EditText) findViewById(R.id.textEmail);
        phonetxt = (EditText) findViewById(R.id.textPhone);
        birthdaylbl = (TextView) findViewById(R.id.lblBirthday);
        savebtn = (Button) findViewById(R.id.btnSave);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        contactList = (ListView) findViewById(R.id.listContact);

        // initialize date
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
    }

    public void assignValues(){
        name = nametxt.getText().toString().trim();
        email = emailtxt.getText().toString().trim();
        phone = phonetxt.getText().toString().trim();
    }

    public void clearValues(){

        // TODO : assign cursor to nametxt

        nametxt.setText("");
        emailtxt.setText("");
        phonetxt.setText("");
        birthdaylbl.setText("Birthday");
    }

    public void setValues(String name, String email, String phone,
                          int birthMonth, int birthDay, int birthYear){
        nametxt.setText(name);
        emailtxt.setText(email);
        phonetxt.setText(phone);
        birthdaylbl.setText(birthMonth+"/"+birthDay+"/"+birthYear);
        imageButton.setImageBitmap(null);
    }

    public void assignNameValue(){
        name = nametxt.getText().toString().trim();
    }

    public void save(View view){
        assignValues();
        Contact contact = new Contact(name, email, phone, day_x, month_x, year_x, image);
        dbHandler.addContact(contact);
        populateListView();
        clearValues();
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
    }

    public void find(View view){
        assignNameValue();
        Contact found = dbHandler.findContact(name);
        setValues(found.getName(), found.getEmail(), found.getPhone(),
                found.getBirthMonth(), found.getBirthDay(), found.getBirthYear());
        imageButton.setImageBitmap(getImage(found.getImage()));
    }

    public void birthday(View view){
        showDialog(DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == DIALOG_ID){
            return new DatePickerDialog(this, datePickerListener, year_x, month_x, day_x);
        }
        else{
            return null;
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;

            // update birthday textview
            birthdaylbl.setText(month_x+"/"+day_x+"/"+year_x);
        }
    };

    public void camera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imageButton.setImageBitmap(bitmap);

        // set image for saving on database
        image = getByte(bitmap);
    }

    // converter
    public static byte[] getByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // converter
    public static Bitmap getImage(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    // populate contacts to listView
    public void populateListView(){
        Cursor cursor = dbHandler.getAllRows();
        String[] fromFieldNames = new String[] {"_id",
                MyDBHandler.COLUMN_NAME, MyDBHandler.COLUMN_EMAIL,
                MyDBHandler.COLUMN_PHONE, MyDBHandler.COLUMN_BIRTHDAY,
                MyDBHandler.COLUMN_BIRTHMONTH, MyDBHandler.COLUMN_BIRTHYEAR,
                MyDBHandler.COLUMN_IMAGE};
        int[] toViewIDs = new int[] {R.id.textViewContactNumber, R.id.textViewName};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.contact_layout, cursor, fromFieldNames, toViewIDs, 0);
        contactList.setAdapter(myCursorAdapter);
    }
}
