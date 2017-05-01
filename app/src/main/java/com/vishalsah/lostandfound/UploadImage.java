package com.vishalsah.lostandfound;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class UploadImage extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 234;
    private ImageView imageView;
    private Button b1, b2;
    private Uri filepath;
    private StorageReference storageReference;
    private Intent intent;
    private String keyy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        storageReference = FirebaseStorage.getInstance().getReference();
        imageView = (ImageView) findViewById(R.id.imageView);
        b1 = (Button) findViewById(R.id.button6);
        Bundle b = getIntent().getExtras();
        keyy = b.getString("put");
        b2 = (Button) findViewById(R.id.button7);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fileChooser();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                uploadFile();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void fileChooser() {
        intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    private void uploadFile() {
        if (filepath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading image... Please wait");
            progressDialog.show();
            StorageReference riversRef = storageReference.child("images/"+keyy+".jpg");
            UploadTask uploadTask = riversRef.putFile(filepath);

// Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                //    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "File uploaded successfully!", Toast.LENGTH_LONG).show();
                }

            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage((int) progress + "% uploaded...");
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Could not be uploaded...", Toast.LENGTH_LONG).show();
        }
    }
}
