package com.vishalsah.lostandfound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profileactivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView textView;
    private Button button, button16, button17, button18, button19;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileactivity);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            Intent intent = new Intent("com.vishalsah.lostandfound.MainActivity");
            startActivity(intent);
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button5);
        button16 = (Button) findViewById(R.id.button16);
        button17 = (Button) findViewById(R.id.button17);
        button18 = (Button) findViewById(R.id.button18);
        textView.setText("Welcome " + user.getEmail() + "!");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("com.vishalsah.lostandfound.MainActivity");
                firebaseAuth.signOut();
                Toast.makeText(profileactivity.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(intent);
            }
        });
        button16.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("com.vishalsah.lostandfound.LostActivity");
                startActivity(intent);
            }
        });
        button17.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("com.vishalsah.lostandfound.FoundActivity");
                startActivity(intent);
            }
        });
        button18.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("com.vishalsah.lostandfound.searchactivity");
                startActivity(intent);
            }
        });

    }
}
