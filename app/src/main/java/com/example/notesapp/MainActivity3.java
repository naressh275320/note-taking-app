package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {

    Button addnew;
    EditText text, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent noteDetails = getIntent();
        Intent setResult = new Intent();

        String textid = noteDetails.getStringExtra("Text");
        String titleid = noteDetails.getStringExtra("Title");
        String p = noteDetails.getStringExtra("position");

        addnew=findViewById(R.id.addnew);
        text = findViewById(R.id.mtext);
        title = findViewById(R.id.mtitle);

        addnew.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View view) {
                String eTitle = title.getText().toString();
                String eText = text.getText().toString();

                // class to add values in the database
                ContentValues values = new ContentValues();

                // fetching text from user
                values.put(MyContentProvider.text,eText);
                values.put(MyContentProvider.title,eTitle);

                // inserting into database through content URI
                getContentResolver().insert(MyContentProvider.CONTENT_URI, values);

                // displaying a toast message
                android.widget.Toast.makeText(getApplicationContext(), "New Record Inserted", Toast.LENGTH_LONG).show();

                //setResult.putExtra("Text",eText);
                setResult.putExtra("Text",eText);
                setResult.putExtra("Title",eTitle);
                setResult.putExtra("position",p);

                Log.i("INSERTED", "inserted");

                setResult(99,setResult);
                finish();
            }
        });
    }
}