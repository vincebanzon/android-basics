package com.veide.vince.elementz_03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button savebtn;
    EditText nametxt, emailtxt, phonetxt;

    String name, email, phone, picture;
    Date birthday;
    MyDBHandler dbHandler;


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
        savebtn = (Button) findViewById(R.id.btnSave);

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
    }

    public void setValues(String name, String email, String phone){
        nametxt.setText(name);
        emailtxt.setText(email);
        phonetxt.setText(phone);
    }

    public void assignNameValue(){
        name = nametxt.getText().toString().trim();
    }

    public void save(View view){
        assignValues();
        Contact contact = new Contact(name, email, phone);
        dbHandler.addContact(contact);
        clearValues();
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
    }

    public void find(View view){
        assignNameValue();
        Contact found = dbHandler.findContact(name);
        setValues(found.getName(),found.getEmail(), found.getPhone());
        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
    }
}
