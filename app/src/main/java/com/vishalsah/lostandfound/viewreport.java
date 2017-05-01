package com.vishalsah.lostandfound;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class viewreport extends AppCompatActivity {
    private TextView textView6, textView8, textView18, textView20, textView22, textView24, textView19, textView21, textView26;
    private Button b1, button19, button22;
    private FirebaseAuth firebaseAuth;
    private boolean status;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private  ArrayList<Found> farray;
    private String xx, xx1, xx2, xx3, xx4, xx5, xx6;
    private Found ff;
    private String save;
    private void remmove() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Found f = new Found();
                ArrayList<String> array = new ArrayList<String>();
                progressDialog.setMessage("Removing... Please wait");
                progressDialog.show();
                farray = new ArrayList<Found>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    f = postSnapshot.getValue(Found.class);
                    if (f.name.equals(ff.name) && f.found == ff.found && f.dateFound.equals(ff.dateFound) && f.brand.equals(ff.brand) && f.category.equals(ff.category)
                            && f.Location.equals(ff.Location)) {
                        postSnapshot.getRef().setValue(null);
                        progressDialog.dismiss();
                        Toast.makeText(viewreport.this, "Removed successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(viewreport.this, "Some error occured...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void update() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Found f = new Found();
                ArrayList<String> array = new ArrayList<String>();
                farray = new ArrayList<Found>();
                progressDialog.setMessage("Updating... Please wait");
                progressDialog.show();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    f = postSnapshot.getValue(Found.class);

                    if (f.name.equals(ff.name) && f.found == ff.found && f.dateFound.equals(ff.dateFound) && f.brand.equals(ff.brand) && f.category.equals(ff.category)
                            && f.Location.equals(ff.Location)) {
                        postSnapshot.getRef().setValue(ff);
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(viewreport.this, "Some error occured...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewreport);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();

        }
        user = firebaseAuth.getCurrentUser();
        final Bundle b = getIntent().getExtras();
        Intent x = getIntent();
        textView6 = (TextView) findViewById(R.id.textView6);
        textView8 = (TextView) findViewById(R.id.textView8);
        textView18 = (TextView) findViewById(R.id.textView18);
        textView20 = (TextView) findViewById(R.id.textView20);
        textView22 = (TextView) findViewById(R.id.textView22);
        textView24 = (TextView) findViewById(R.id.textView24);
        textView19 = (TextView) findViewById(R.id.textView19);
        textView21 = (TextView) findViewById(R.id.textView21);
        textView26 = (TextView) findViewById(R.id.textView26);
        progressDialog = new ProgressDialog(this);
        ArrayList<String> ss = b.getStringArrayList("get");
        textView6.setText(ss.get(0));
        textView8.setText(ss.get(1));
        textView18.setText(ss.get(2));
        textView20.setText(ss.get(3));
        textView22.setText(ss.get(4));
        textView24.setText(ss.get(5));

        b1 = (Button) findViewById(R.id.button15);
        button19 = (Button) findViewById(R.id.button19);
        button22 = (Button) findViewById(R.id.button22);

        status = true;
        xx = ss.get(6).trim();
        save = ss.get(9);
        ff = new Found();
        ff.name = ss.get(0);
        ff.category = ss.get(1);
        ff.brand = ss.get(2);
        ff.dateFound = ss.get(3);
        ff.Location = ss.get(4);
        ff.description = ss.get(5);
        ff.user = xx;
        String chch = ss.get(7);
        if (chch.equalsIgnoreCase("true")) {
            ff.found = true;
        } else {
            ff.found = false;
            textView19.setText("Date Lost :");
            textView21.setText("Location Lost :");
        }
        ff.spamcount = Integer.parseInt(ss.get(8));
        if (ff.spamcount > 0)
            textView26.setText("");
        else
            textView26.setText("");
        if (user.getEmail().equals(xx)) {
            b1.setText("Delete report");
            b1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    remmove();
                }
            });
        } else {
            b1.setText("Mail to "+xx);
            b1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri data = Uri.parse("mailto:"+ xx + "?subject=LostAndFound&body=Hello");
                    intent.setData(data);
                    startActivity(intent);
                }
            });
        }
        button19.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                    ff.spamcount++;
                    Toast.makeText(viewreport.this, "Reported to be spam successfully, admin will verify.", Toast.LENGTH_SHORT).show();
                 //   update();

            }
        });
        button22.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               Bundle b = new Bundle();
                b.putString("put", save);
                Intent intent = new Intent("com.vishalsah.lostandfound.showimage");
                intent.putExtras(b);
                startActivity(intent);

            }
        });

    }
}
