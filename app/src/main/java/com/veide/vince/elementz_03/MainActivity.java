package com.veide.vince.elementz_03;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public Button savebtn;
    public EditText nametxt, emailtxt, phonetxt;
    public TextView birthdaylbl;

    private String name, email, phone, picture;
    private int year_x, month_x, day_x;
    private static final int DIALOG_ID = 0;
    private Date birthday;
    private MyDBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new MyDBHandler(this, null, null, 1);
        initialize();

    }

    private void initialize(){
        // initialize elements
        nametxt = (EditText) findViewById(R.id.textName);
        emailtxt = (EditText) findViewById(R.id.textEmail);
        phonetxt = (EditText) findViewById(R.id.textPhone);
        birthdaylbl = (TextView) findViewById(R.id.lblBirthday);
        savebtn = (Button) findViewById(R.id.btnSave);

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
    }

    public void assignNameValue(){
        name = nametxt.getText().toString().trim();
    }

    public void save(View view){
        assignValues();
        Contact contact = new Contact(name, email, phone, day_x, month_x, year_x);
        dbHandler.addContact(contact);
        clearValues();
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
    }

    public void find(View view){
        assignNameValue();
        Contact found = dbHandler.findContact(name);
        setValues(found.getName(), found.getEmail(), found.getPhone(),
                found.getBirthMonth(), found.getBirthDay(), found.getBirthYear());
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
}
