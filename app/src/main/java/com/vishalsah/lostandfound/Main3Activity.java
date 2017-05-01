package com.vishalsah.lostandfound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;

public class Main3Activity extends AppCompatActivity {

    RelativeLayout l;
    ImageView iv;
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    Thread splashTread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        StartAnimations();

    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        l=(RelativeLayout) findViewById(R.id.relay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    RelativeLayout relativeLayout;
                    relativeLayout = (RelativeLayout) findViewById(R.id.relay);
                    relativeLayout.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            Intent intent = new Intent("com.vishalsah.lostandfound.MainActivity");
                            //    Intent intent = new Intent("com.vishalsah.lostandfound.showimage" );
                            //    Intent intent = new Intent("com.vishalsah.lostandfound.speech_to_text");
                            startActivity(intent);
                        }
                    });
                    //Main3Activity.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    //iv.setVisibility(View.INVISIBLE);
                    RelativeLayout relativeLayout;
                    relativeLayout = (RelativeLayout) findViewById(R.id.relay);
                    relativeLayout.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            Intent intent = new Intent("com.vishalsah.lostandfound.MainActivity");
                            //    Intent intent = new Intent("com.vishalsah.lostandfound.showimage" );
                            //    Intent intent = new Intent("com.vishalsah.lostandfound.speech_to_text");
                            startActivity(intent);
                        }
                    });
                    //Main3Activity.this.finish();
                }
            }
        };
        splashTread.start();

    }
}
