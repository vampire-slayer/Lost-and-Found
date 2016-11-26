package com.vishalsah.lostandfound;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.io.IOException;

public class Main3Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        RelativeLayout relativeLayout;
        relativeLayout = (RelativeLayout) findViewById(R.id.relay);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("com.vishalsah.lostandfound.MainActivity");
            //    Intent intent = new Intent("com.vishalsah.lostandfound.showimage" );
                startActivity(intent);
            }
        });

    }

}
