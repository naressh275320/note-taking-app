package com.example.notesapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements recyclerInterface{

    RecyclerView recyclerView;
    ArrayList<notes> note = new ArrayList<>();
    note_recycle_adapter adapter = new note_recycle_adapter(this,note,this);
    Button add;

    @SuppressLint({"Range", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent u = getIntent();
        recyclerView = findViewById(R.id.recycleView);
        add = findViewById(R.id.add);

        displayView();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details = new Intent(MainActivity.this, MainActivity3.class);
                //startActivity(details);
                getResult.launch(details);
            }
        });
    }

    public void onRecycleClick(notes note, int position) {
        Intent details = new Intent(MainActivity.this, MainActivity2.class);
        details.putExtra("Text", note.getText());
        details.putExtra("Title", note.getTitle()   );
        String p = Integer.toString(position);
        details.putExtra("position",p);

        //launching third activity
        getResult.launch(details);
    }

    ActivityResultLauncher<Intent> getResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            //if result code is 55 then the result comes from update button which is in second activity
            if (result.getResultCode() == 55) {

                //getting the intent and updated details of the note
                Intent intent = result.getData();
                String p = intent.getStringExtra("position");
                int position = Integer.parseInt(p);
                String bText = intent.getStringExtra("Text");
                String bTitle = intent.getStringExtra("Title");

                //adding new details of that note in same position
                note.set(position, new notes(bText, bTitle));
                Log.i("UPDATE", "uPDATED");
                //notifying the adapter for some changes done
                adapter.notifyItemChanged(position);
            }

            //if result code is 22 then the result comes from delete button which is in second activity
            else if (result.getResultCode() == 22) {

                //getting the intent and deleting the note details from the arraylist
                Intent intent = result.getData();
                String p = intent.getStringExtra("position");
                int position = Integer.parseInt(p);

                //removing the note
                note.remove(position);

                //notifying the adapter for deleting the note
                adapter.notifyItemRemoved(position);
            }

            //if result code is 99 then the result comes from add button which is in third activity
            else if (result.getResultCode() == 99) {
                if (adapter.getItemCount() != 0) {
                    Log.i("INSERTED", "inserted from main");

                    //getting the intent and updated details of the note
                    Intent intent = result.getData();

                    String bText = intent.getStringExtra("Text");
                    String bTitle = intent.getStringExtra("Title");

                    //adding the data at last position
                    note.add(new notes(bText, bTitle));

                    //notifying the adapter after inserting
                    adapter.notifyItemInserted(note.size() - 1);
                }
                else{
                    displayView();
                    Log.i("Print view","Printed recycler views");
                }
            }
        }
    });

    @SuppressLint("Range")
    public void displayView(){

        // creating a cursor object of the
        // content URI
        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI,
                null, null, null, null);

        // iteration of the cursor
        // to print whole table
        if(cursor != null){
            if(cursor.moveToFirst()) {
                String t, e;
                while (!cursor.isAfterLast()) {
                    t =cursor.getString(cursor.getColumnIndex(MyContentProvider.text));
                    e =cursor.getString(cursor.getColumnIndex(MyContentProvider.title));

                    note.add(new notes(t,e));
                    cursor.moveToNext();
                }
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
        }
    }
}