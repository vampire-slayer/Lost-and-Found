package com.vishalsah.lostandfound;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import java.util.List;

public class searchactivity extends AppCompatActivity {

    private FirebaseUser user;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private boolean check;
    private ArrayList<Found> farray;
    private ArrayList<String> getkey;
    private CheckBox checkBox;
    private Button b1, b2, b3, b4, b5, b6, button20;
    private String search;
    private EditText editText3;
    private TextView textView3;
    private DatabaseReference databaseReference, xx, yy;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private void dothis(int position) {

        Bundle b = new Bundle();
        ArrayList<String> ss = new ArrayList<String>();
        ss.add(farray.get(position).name);
        ss.add(farray.get(position).category);
        ss.add(farray.get(position).brand);
        ss.add(farray.get(position).dateFound);
        ss.add(farray.get(position).Location);
        ss.add(farray.get(position).description);
        ss.add(farray.get(position).user);
        int x = farray.get(position).spamcount;
        if (farray.get(position).found == true)
            ss.add("true");
        else
            ss.add("false");
        ss.add(String.valueOf(x));
        ss.add(getkey.get(position));
        b.putStringArrayList("get", ss);
        Intent intent = new Intent("com.vishalsah.lostandfound.viewreport" );
        intent.putExtras(b);
        startActivity(intent);
    }
    private void searchbyname() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Found f = new Found();
                ArrayList<String> array = new ArrayList<String>();
                farray = new ArrayList<Found>();
                getkey = new ArrayList<String>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    f = postSnapshot.getValue(Found.class);
                    DatabaseReference ref = postSnapshot.getRef();

                    if (f.name.equalsIgnoreCase(search) && check == f.found) {
                        array.add(f.name);
                        farray.add(f);
                        getkey.add(postSnapshot.getKey());
                    }
                }
                progressDialog.dismiss();
                Toast.makeText(searchactivity.this, "Results fetched", Toast.LENGTH_SHORT).show();
                Object[] objs = array.toArray();
                String[] arrays = Arrays.copyOf(objs, objs.length, String[].class);
                listView = (ListView) findViewById(R.id.listView);
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.INVISIBLE);
                b3.setVisibility(View.INVISIBLE);
                b4.setVisibility(View.INVISIBLE);
                b5.setVisibility(View.INVISIBLE);
                b6.setVisibility(View.INVISIBLE);
                button20.setVisibility(View.INVISIBLE);
                textView3.setVisibility(View.INVISIBLE);
                editText3.setVisibility(View.INVISIBLE);
                checkBox.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);

                adapter = new ArrayAdapter<String>(searchactivity.this, android.R.layout.simple_list_item_1, arrays);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dothis(position);

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(searchactivity.this, "Some error occured...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void searchbycategory() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Found f = new Found();
                farray = new ArrayList<Found>();
                getkey = new ArrayList<String>();
                ArrayList<String> array = new ArrayList<String>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    f = postSnapshot.getValue(Found.class);

                    if (f.category.equalsIgnoreCase(search) && check == f.found) {
                        array.add(f.name);
                        farray.add(f);
                        getkey.add(postSnapshot.getKey());
                    }
                }
                progressDialog.dismiss();
                Toast.makeText(searchactivity.this, "Results fetched", Toast.LENGTH_SHORT).show();
                Object[] objs = array.toArray();
                String[] arrays = Arrays.copyOf(objs, objs.length, String[].class);
                listView = (ListView) findViewById(R.id.listView);
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.INVISIBLE);
                b3.setVisibility(View.INVISIBLE);
                b4.setVisibility(View.INVISIBLE);
                b5.setVisibility(View.INVISIBLE);
                checkBox.setVisibility(View.INVISIBLE);
                b6.setVisibility(View.INVISIBLE);
                button20.setVisibility(View.INVISIBLE);
                textView3.setVisibility(View.INVISIBLE);
                editText3.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
                adapter = new ArrayAdapter<String>(searchactivity.this, android.R.layout.simple_list_item_1, arrays);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dothis(position);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(searchactivity.this, "Some error occured...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void searchbybrand() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Found f = new Found();
                ArrayList<String> array = new ArrayList<String>();
                farray = new ArrayList<Found>();
                getkey = new ArrayList<String>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    f = postSnapshot.getValue(Found.class);

                    if (f.brand.equalsIgnoreCase(search) && check == f.found) {
                        array.add(f.name);
                        farray.add(f);
                        getkey.add(postSnapshot.getKey());
                    }
                }
                progressDialog.dismiss();
                Toast.makeText(searchactivity.this, "Results fetched", Toast.LENGTH_SHORT).show();
                Object[] objs = array.toArray();
                String[] arrays = Arrays.copyOf(objs, objs.length, String[].class);
                listView = (ListView) findViewById(R.id.listView);
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.INVISIBLE);
                b3.setVisibility(View.INVISIBLE);
                b4.setVisibility(View.INVISIBLE);
                checkBox.setVisibility(View.INVISIBLE);
                b5.setVisibility(View.INVISIBLE);
                b6.setVisibility(View.INVISIBLE);
                button20.setVisibility(View.INVISIBLE);
                textView3.setVisibility(View.INVISIBLE);
                editText3.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
                adapter = new ArrayAdapter<String>(searchactivity.this, android.R.layout.simple_list_item_1, arrays);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dothis(position);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(searchactivity.this, "Some error occured...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void searchbydate() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Found f = new Found();
                ArrayList<String> array = new ArrayList<String>();
                farray = new ArrayList<Found>();
                getkey = new ArrayList<String>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    f = postSnapshot.getValue(Found.class);

                    if (f.dateFound.equalsIgnoreCase(search) && check == f.found) {
                        array.add(f.name);
                        farray.add(f);
                        getkey.add(postSnapshot.getKey());
                    }
                }
                progressDialog.dismiss();
                Toast.makeText(searchactivity.this, "Results fetched", Toast.LENGTH_SHORT).show();
                Object[] objs = array.toArray();
                String[] arrays = Arrays.copyOf(objs, objs.length, String[].class);
                listView = (ListView) findViewById(R.id.listView);
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.INVISIBLE);
                b3.setVisibility(View.INVISIBLE);
                b4.setVisibility(View.INVISIBLE);
                checkBox.setVisibility(View.INVISIBLE);
                b5.setVisibility(View.INVISIBLE);
                b6.setVisibility(View.INVISIBLE);
                button20.setVisibility(View.INVISIBLE);
                textView3.setVisibility(View.INVISIBLE);
                editText3.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
                adapter = new ArrayAdapter<String>(searchactivity.this, android.R.layout.simple_list_item_1, arrays);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dothis(position);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(searchactivity.this, "Some error occured...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void searchbylocation() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Found f = new Found();
                ArrayList<String> array = new ArrayList<String>();
                farray = new ArrayList<Found>();
                getkey = new ArrayList<String>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    f = postSnapshot.getValue(Found.class);

                    if (f.Location.equalsIgnoreCase(search) && check == f.found) {
                        array.add(f.name);
                        farray.add(f);
                        getkey.add(postSnapshot.getKey());

                    }
                }
                progressDialog.dismiss();
                Toast.makeText(searchactivity.this, "Results fetched", Toast.LENGTH_SHORT).show();
                Object[] objs = array.toArray();
                String[] arrays = Arrays.copyOf(objs, objs.length, String[].class);
                listView = (ListView) findViewById(R.id.listView);
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.INVISIBLE);
                b3.setVisibility(View.INVISIBLE);
                b4.setVisibility(View.INVISIBLE);
                checkBox.setVisibility(View.INVISIBLE);
                b5.setVisibility(View.INVISIBLE);
                b6.setVisibility(View.INVISIBLE);
                button20.setVisibility(View.INVISIBLE);
                textView3.setVisibility(View.INVISIBLE);
                editText3.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
                adapter = new ArrayAdapter<String>(searchactivity.this, android.R.layout.simple_list_item_1, arrays);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dothis(position);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(searchactivity.this, "Some error occured...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void viewall() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Found f = new Found();
                ArrayList<String> array = new ArrayList<String>();
                farray = new ArrayList<Found>();
                getkey = new ArrayList<String>();
                String s;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    f = postSnapshot.getValue(Found.class);
                    if (check == f.found) {
                        array.add(f.name);
                        farray.add(f);
                        s = postSnapshot.getKey().toString();
                      //  Toast.makeText(searchactivity.this, s, Toast.LENGTH_SHORT).show();
                        getkey.add(s);
                    }
                }
                progressDialog.dismiss();
                Toast.makeText(searchactivity.this, "Results fetched", Toast.LENGTH_SHORT).show();
                Object[] objs = array.toArray();
                String[] arrays = Arrays.copyOf(objs, objs.length, String[].class);
                listView = (ListView) findViewById(R.id.listView);
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.INVISIBLE);
                b3.setVisibility(View.INVISIBLE);
                b4.setVisibility(View.INVISIBLE);
                checkBox.setVisibility(View.INVISIBLE);
                b5.setVisibility(View.INVISIBLE);
                b6.setVisibility(View.INVISIBLE);
                button20.setVisibility(View.INVISIBLE);
                textView3.setVisibility(View.INVISIBLE);
                editText3.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
                adapter = new ArrayAdapter<String>(searchactivity.this, android.R.layout.simple_list_item_1, arrays);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dothis(position);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(searchactivity.this, "Some error occured...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void viewmine() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Found f = new Found();
                ArrayList<String> array = new ArrayList<String>();
                farray = new ArrayList<Found>();
                getkey = new ArrayList<String>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    f = postSnapshot.getValue(Found.class);

                    if (check == f.found && user.getEmail().equals(f.user)) {
                        array.add(f.name);
                        farray.add(f);
                        getkey.add(postSnapshot.getKey());
                    }
                }
                progressDialog.dismiss();
                Toast.makeText(searchactivity.this, "Results fetched", Toast.LENGTH_SHORT).show();
                Object[] objs = array.toArray();
                String[] arrays = Arrays.copyOf(objs, objs.length, String[].class);
                listView = (ListView) findViewById(R.id.listView);
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.INVISIBLE);
                b3.setVisibility(View.INVISIBLE);
                b4.setVisibility(View.INVISIBLE);
                checkBox.setVisibility(View.INVISIBLE);
                b5.setVisibility(View.INVISIBLE);
                b6.setVisibility(View.INVISIBLE);
                button20.setVisibility(View.INVISIBLE);
                textView3.setVisibility(View.INVISIBLE);
                editText3.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
                adapter = new ArrayAdapter<String>(searchactivity.this, android.R.layout.simple_list_item_1, arrays);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dothis(position);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(searchactivity.this, "Some error occured...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchactivity);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();

        }
        user = firebaseAuth.getCurrentUser();
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        progressDialog = new ProgressDialog(this);
        b1 = (Button) findViewById(R.id.button3);
        b2 = (Button) findViewById(R.id.button10);
        b3 = (Button) findViewById(R.id.button11);
        b4 = (Button) findViewById(R.id.button12);
        b5 = (Button) findViewById(R.id.button13);
        b6 = (Button) findViewById(R.id.button14);
        button20 = (Button) findViewById(R.id.button20);
        textView3 = (TextView) findViewById(R.id.textView3);
        listView = (ListView) findViewById(R.id.listView);
        listView.setVisibility(View.INVISIBLE);
        editText3 = (EditText) findViewById(R.id.editText3);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                search = editText3.getText().toString();
                if (checkBox.isChecked())
                    check = true;
                else check = false;
                if (TextUtils.isEmpty(search)) {
                    Toast.makeText(searchactivity.this, "You cannot leave this field blank!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    progressDialog.setMessage("Fetching results... Please wait");
                    progressDialog.show();
                    searchbyname();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                search = editText3.getText().toString();
                if (checkBox.isChecked())
                    check = true;
                else check = false;
                if (TextUtils.isEmpty(search)) {
                    Toast.makeText(searchactivity.this, "You cannot leave this field blank!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    progressDialog.setMessage("Fetching results... Please wait");
                    progressDialog.show();
                    searchbycategory();
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                search = editText3.getText().toString();
                if (checkBox.isChecked())
                    check = true;
                else check = false;
                if (TextUtils.isEmpty(search)) {
                    Toast.makeText(searchactivity.this, "You cannot leave this field blank!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    progressDialog.setMessage("Fetching results... Please wait");
                    progressDialog.show();
                    searchbybrand();}
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                search = editText3.getText().toString();
                if (checkBox.isChecked())
                    check = true;
                else check = false;
                if (TextUtils.isEmpty(search)) {
                    Toast.makeText(searchactivity.this, "You cannot leave this field blank!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    progressDialog.setMessage("Fetching results... Please wait");
                    progressDialog.show();
                    searchbydate();}
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                search = editText3.getText().toString();
                if (checkBox.isChecked())
                    check = true;
                else check = false;
                if (TextUtils.isEmpty(search)) {
                    Toast.makeText(searchactivity.this, "You cannot leave this field blank!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    progressDialog.setMessage("Fetching results... Please wait");
                    progressDialog.show();
                    searchbylocation();}
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (checkBox.isChecked())
                    check = true;
                else check = false; progressDialog.setMessage("Fetching results... Please wait");
                progressDialog.show();viewall();
            }
        });
        button20.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (checkBox.isChecked())
                    check = true;
                else check = false; progressDialog.setMessage("Fetching results... Please wait");
                progressDialog.show();viewmine();
            }
        });
    }
}
