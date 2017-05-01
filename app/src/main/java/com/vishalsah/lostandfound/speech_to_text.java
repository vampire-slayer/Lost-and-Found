package com.vishalsah.lostandfound;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Locale;

import static com.vishalsah.lostandfound.R.id.textView;

public class speech_to_text extends AppCompatActivity {
    private TextView txtSpeechInput;
    private TextView tapToSpeak;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

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
    private TextView textView;
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

                    if (f.name.equalsIgnoreCase(search)) {
                        array.add(f.name);
                        farray.add(f);
                        getkey.add(postSnapshot.getKey());
                    }
                }
                tapToSpeak = (TextView) findViewById(R.id.tapToSpeak);
                progressDialog.dismiss();
                Toast.makeText(speech_to_text.this, "Results fetched", Toast.LENGTH_SHORT).show();
                Object[] objs = array.toArray();
                String[] arrays = Arrays.copyOf(objs, objs.length, String[].class);
                listView = (ListView) findViewById(R.id.listView);
                txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
                txtSpeechInput.setVisibility(View.INVISIBLE);
                tapToSpeak.setVisibility(View.INVISIBLE);
                btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
                btnSpeak.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);

                adapter = new ArrayAdapter<String>(speech_to_text.this, android.R.layout.simple_list_item_1, arrays);
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
                Toast.makeText(speech_to_text.this, "Some error occured...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
        }
        user = firebaseAuth.getCurrentUser();

        progressDialog = new ProgressDialog(this);
        listView = (ListView) findViewById(R.id.listView);
        listView.setVisibility(View.INVISIBLE);
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
        }
    }
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                    search = txtSpeechInput.getText().toString();
                    Toast.makeText(getApplicationContext(), "You entered \'" + search + "\'", Toast.LENGTH_SHORT).show();
                    progressDialog.setMessage("Fetching results for \'" + search + "\', please wait.");
                    progressDialog.show();
                    searchbyname();
                }
                break;
            }
        }
    }
}
