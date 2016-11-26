package com.vishalsah.lostandfound;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private Button button, button2;
    private EditText email, password;
    private TextView t1, t2, t3;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private void userlogin() {
        String email1, password1;
        email1 = email.getText().toString().trim();
        password1 = password.getText().toString();
        if (TextUtils.isEmpty(email1)) {
            Toast.makeText(this, "Please enter your E-mail address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password1)) {
            Toast.makeText(this, "Please enter your password!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Logging in... Please wait");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email1, password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent("com.vishalsah.lostandfound.profileactivity");
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Could not log in! Please try again...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            Intent intent = new Intent("com.vishalsah.lostandfound.profileactivity");
            startActivity(intent);
        }
        progressDialog = new ProgressDialog(this);
        email = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        t3 = (TextView) findViewById(R.id.textView12);
        t3.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* username.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                t3.setVisibility(View.VISIBLE);
                t3.setText("Welcome " + username.getText() + "!");
                Intent intent = new Intent("com.vishalsah.lostandfound.Main2Activity");
                startActivity(intent);*/
                userlogin();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent("com.vishalsah.lostandfound.signupactivity");
                startActivity(intent);
            }
        });
    }
}
