package com.vishalsah.lostandfound;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class signupactivity extends AppCompatActivity {

    private EditText e1, e2, e3, e4, e5;
    private Button b1;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private User u1;
    private String password, email;
    private void registerUser() {
        u1 = new User();
     //   u1.username = e1.getText().toString().trim();
        password = e2.getText().toString();

        email = e4.getText().toString().trim();

     /*   if (TextUtils.isEmpty(u1.username)) {
            Toast.makeText(this, "Please enter your username!", Toast.LENGTH_SHORT).show();
            return;
        }*/
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your E-mail!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user... Please wait");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                           /* firebaseAuth.signInWithEmailAndPassword(email,password);
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            databaseReference.child(user.getUid()).setValue(u1);*/
                            Toast.makeText(signupactivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent("com.vishalsah.lostandfound.MainActivity");
                            startActivity(intent);
                        } else {
                            Toast.makeText(signupactivity.this, "Could not register, please try again...", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent("com.vishalsah.lostandfound.MainActivity");
                            startActivity(intent);
                        }
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            Toast.makeText(this, "Already signed in!", Toast.LENGTH_SHORT).show();
            finish();
        }
        progressDialog = new ProgressDialog(this);
        b1 = (Button) findViewById(R.id.button4);
      //  e1 = (EditText) findViewById(R.id.editText8);
        e2 = (EditText) findViewById(R.id.editText9);
        e4 = (EditText) findViewById(R.id.editText11);

        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerUser();
            }
        });
    }
}
