package com.vishalsah.lostandfound;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoundActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private static final String DBUrl = "https://lost-and-found-app.firebaseio.com/";
    private DatabaseReference databaseReference;
    private DatePicker datePicker;
    private FirebaseUser user;
    private TextView textView2;
    private EditText editText3, editText6, editText5, editText7;
    private Spinner spinner;
    private Button button3;
    private void dothat() {
        Found u1 = new Found();
        u1.name = editText3.getText().toString();
        if (TextUtils.isEmpty(u1.name)) {
            Toast.makeText(FoundActivity.this, "Item name field cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        u1.category = spinner.getSelectedItem().toString();
        u1.brand = editText5.getText().toString();
        u1.dateFound = getCurrentDate();
        u1.Location = editText6.getText().toString();
        u1.description = editText7.getText().toString();
        u1.found = true;
        u1.user = user.getEmail();
        u1.spamcount = 0;
        if (databaseReference != null) {
            // editText3.setText("fjhgjgkhj");
            //   DatabaseReference usersRef = databaseReference.child("found");
            DatabaseReference usersRef = databaseReference.push();
            if (usersRef != null) {
                DatabaseReference xx = usersRef;
                if (xx != null) {
                    usersRef.setValue(u1);
                }
            }
            Toast.makeText(FoundActivity.this, "Added successfully!", Toast.LENGTH_SHORT).show();
            Bundle b = new Bundle();
            b.putString("put", usersRef.getKey());
            Intent intent = new Intent("com.vishalsah.lostandfound.UploadImage");
            intent.putExtras(b);
            startActivity(intent);
            finish();

        } else Toast.makeText(FoundActivity.this, "Could not be added!", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
        }
        user = firebaseAuth.getCurrentUser();
        datePicker = (DatePicker) findViewById(R.id.datePicker2);
        textView2 = (TextView) findViewById(R.id.textView10);
        textView2.setText("");
        spinner = (Spinner) findViewById(R.id.spinner2);

        editText3 = (EditText) findViewById(R.id.editText7);
        editText5 = (EditText) findViewById(R.id.editText8);
        editText6 = (EditText) findViewById(R.id.editText13);
        editText7 = (EditText) findViewById(R.id.editText14);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        button3 = (Button) findViewById(R.id.button8);

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dothat();
            }
        });

    }
    public String getCurrentDate(){
        StringBuilder builder=new StringBuilder();
       // builder.append("Current Date: ");
        builder.append((datePicker.getDayOfMonth())+"/");//month is 0 based
        builder.append((datePicker.getMonth() + 1)+"/");
        builder.append(datePicker.getYear());
        return builder.toString();
    }
}
