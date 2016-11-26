package com.vishalsah.lostandfound;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class showimage extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 234;
    StorageReference storageReference, xy;
    private ImageView imageView;
    Uri filepath;
    private String keyy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimage);
        imageView = (ImageView) findViewById(R.id.imageView2);
        storageReference = FirebaseStorage.getInstance().getReference();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        Bundle b = getIntent().getExtras();
        keyy = b.getString("put");
        progressDialog.setMessage("Downloading image, please wait...");
        progressDialog.show();
        StorageReference x = storageReference.child("images/" + keyy + ".jpg");
        if (x != null) {
            x.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Use the bytes to display the image
                    Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    progressDialog.dismiss();
                    imageView.setImageBitmap(bm);
                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Toast.makeText(showimage.this, "Either no image uploaded or failed to fetch image", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(showimage.this, "Either no image uploaded or failed to fetch image", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }


}
