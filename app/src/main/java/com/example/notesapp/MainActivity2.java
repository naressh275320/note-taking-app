package com.example.notesapp;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    EditText text, title;
    Button delete, update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //getting intent from second activity
        Intent noteDetails = getIntent();

        //finding the id of editText and button
        // assigning it to variables
        text=findViewById(R.id.ntext);
        title = findViewById(R.id.ntitle);
        delete = findViewById(R.id.ndelete);
        update = findViewById(R.id.nupdate);

        //storing the current note name
        String textid = noteDetails.getStringExtra("Text");
        String titleid = noteDetails.getStringExtra("Title");
        String p = noteDetails.getStringExtra("position");

        //setting the text of the editText from details given from second activity
        text.setText(textid);
        title.setText(titleid);

        //creating a intent to send result to second activity
        Intent setResult = new Intent();

        //setting onClickListener for update button
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting the values from the editText and storing it as string
                String eText = text.getText().toString();
                String eTitle = title.getText().toString();

                //checking whether all fields are filled or not
                //if not then toast message will pop up
                if (eTitle.isEmpty() || eText.isEmpty()) {
                    android.widget.Toast.makeText(getApplicationContext(), "Please Enter Fields", Toast.LENGTH_LONG).show();
                } else
                {
                    //setting the selection to title column
                    String select = "Text =?";

                    //search for note name and update its details
                    String args[] = {textid};

                    // class to add values in the database
                    ContentValues values = new ContentValues();

                    // fetching text from user
                    values.put(MyContentProvider.text,eText);
                    values.put(MyContentProvider.title,eTitle);

                    // updating a row in database through content URI
                    getContentResolver().update(MyContentProvider.CONTENT_URI, values,select,args);
                    // displaying a toast message
                    android.widget.Toast.makeText(getApplicationContext(), "Notes Updated", Toast.LENGTH_LONG).show();

                    //sending the details of note as result
                    setResult.putExtra("position",p);
                    setResult.putExtra("Text",eText);
                    setResult.putExtra("Title",eTitle);

                    //when successful completion of third activity it sends result to main activity
                    setResult(55,setResult);

                    //finishing the second activity
                    finish();
                }
            }
        });

        //setting onClickListener for delete button
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //setting the selection to title column
                String select = "Text =?";
                String select2 = "Title =?";

                //search for note name and deletes its details
                String args[] = {textid};

                // deleting a row in database through content URI
                getContentResolver().delete(MyContentProvider.CONTENT_URI,select,args);

                // displaying a toast message
                android.widget.Toast.makeText(getApplicationContext(), "Note Deleted", Toast.LENGTH_LONG).show();
                setResult.putExtra("position",p);

                //when successful completion of third activity it sends result to main activity
                setResult(22,setResult);

                //finishing the third activity
                finish();
            }
        });
    }
}
