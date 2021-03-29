package com.example.activity4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button userAddName, retrieveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userAddName = (Button) findViewById(R.id.userAddName);
        retrieveBtn = (Button) findViewById(R.id.retrivedStudentBtn);

        userAddName.setOnClickListener(this::onClickAddName);
        retrieveBtn.setOnClickListener(this::onClickRetrieveStudents);


    }



    public void onClickAddName(View view) {
        // Add a new student record
        ContentValues values = new ContentValues();
        values.put(StudentsProvider.NAME,
                ((EditText)findViewById(R.id.editText2)).getText().toString());
        values.put(StudentsProvider.GRADE,
                ((EditText)findViewById(R.id.editText3)).getText().toString());
        Uri uri = getContentResolver().insert(
                StudentsProvider.CONTENT_URI, values);
        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void onClickRetrieveStudents(View view) {
        // Retrieve student records
        String URL = "content://com.example.Activity4.StudentsProvider";
        Uri students = Uri.parse(URL);
        Cursor c = getContentResolver().query(students, null, null, null, "name");
        if (c.moveToFirst()) {
            do{
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(StudentsProvider._ID)) +
                                ", " + c.getString(c.getColumnIndex(
                                StudentsProvider.NAME)) +
                                " " + c.getString(c.getColumnIndex(
                                StudentsProvider.GRADE)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }

}