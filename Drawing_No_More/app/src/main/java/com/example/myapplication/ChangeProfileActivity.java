package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class ChangeProfileActivity extends AppCompatActivity {

    Button btnChoose, btnUpload;
    ImageView imageUpload;
    final int CODE_GALLERY_REQUEST = 999;
    Bitmap bitmap;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        getSupportActionBar().hide();


        btnChoose = findViewById(R.id.selectPhoto);
        btnUpload = findViewById(R.id.uploadPhoto);
        imageUpload = findViewById(R.id.profilePicture);

        Glide.with(ChangeProfileActivity.this)
                .load(SharedPrefManager.getProfile())
                .into(imageUpload);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ChangeProfileActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST

                );
            }
        });

        progressDialog = new ProgressDialog(ChangeProfileActivity.this);
        progressDialog.setMessage("Uploading your photo \nPlease wait");

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        Constants.URL_UPLOADPROFILE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try {
                                    JSONObject obj = new JSONObject(response);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeProfileActivity.this);
                                    builder.setTitle("Warning");
                                    builder.setMessage(obj.getString("message") + "\nYou need to re-log in to view changes.");
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            logout();
                                        }
                                    });

                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Do something when No button clicked
                                            Toast.makeText(getApplicationContext(),
                                                    "Cancelled, \nUploaded picture will not show up",Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                } catch (JSONException e) {
                                    progressDialog.dismiss();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeProfileActivity.this);
                                    builder.setTitle("Warning");
                                    builder.setMessage("Uploaded Successfully" + "\nYou need to re-log in to view changes.");
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            logout();
                                        }
                                    });
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Error Uploading image, \nPlease try again", Toast.LENGTH_LONG).show();
                            }
                        }

                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        String imageData = imageToString(bitmap);
                        String userid = String.valueOf(SharedPrefManager.getInstance(ChangeProfileActivity.this).getUid());
                        params.put("image", imageData);
                        params.put("userid", userid);
                        return params;
                    }
                };

                RequestHandler.getInstance(ChangeProfileActivity.this).addToRequestQueue(stringRequest);


            }
        });


    }

    public void logout() {
        SharedPrefManager.getInstance(this).loggedOut();
        startActivity(new Intent(getApplicationContext(), LoginPanel.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CODE_GALLERY_REQUEST){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Select image "), CODE_GALLERY_REQUEST);
            } else
            {
                Toast.makeText(getApplicationContext(), "Permission not Granted", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                 bitmap = BitmapFactory.decodeStream(inputStream);
                imageUpload.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String imageToString(Bitmap bitmap){

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }
}