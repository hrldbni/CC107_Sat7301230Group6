package com.example.Group6_SQLite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deardearyjaycabasag.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditingWindow extends AppCompatActivity {

    EditText diaryField;
    public DBHelper  myDb = new DBHelper(this);
    Date currentTime = Calendar.getInstance().getTime();
    DateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
    String strDate = dateFormat.format(currentTime);
    FloatingActionButton saveButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_window);
        getSupportActionBar().hide();

        diaryField =(EditText) findViewById(R.id.editDiaryField);
        saveButton = (FloatingActionButton) findViewById(R.id.saveButton);

        Intent intent;
        intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String diaryText = intent.getStringExtra("diarytext");
        diaryField.setText(diaryText);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean var = myDb.updateData(userId, strDate, diaryField.getText().toString());

                if (var) {
                    Toast.makeText(EditingWindow.this,
                            "Diary Updated, Please Log in again",
                            Toast.LENGTH_LONG).show();
                    Intent homeIntent;
                    homeIntent = new Intent(EditingWindow.this, HomeActivity.class);
                    startActivity(homeIntent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditingWindow.this);
                    builder.setTitle("Warning")
                            .setMessage("Error Updating Diary!")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();
                }


            }
        });





    }
}