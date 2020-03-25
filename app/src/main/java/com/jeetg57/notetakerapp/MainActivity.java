package com.jeetg57.notetakerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> note_list = new ArrayList<>();
    public static final String EXTRA_MESSAGE = "com.jeetg57.notetakerapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNote.class);
                startActivity(intent);
            }
        });
        loadData();
    }
    private void loadData() {
        note_list.clear();
           Context context = this;
                File newfile = new File(context.getFilesDir() + "/MyNotes/");
                if(newfile.length() > 0){
                for (File f : Objects.requireNonNull(newfile.listFiles())) {
                    if (f.isFile())
                        if (!note_list.contains(f.getName())) {
                            String name = f.getName();
                            note_list.add(name);
                        }
                    }
                }
                if(!(note_list.isEmpty())) {
                    TextView isEmpty = findViewById(R.id.ifEmpty);
                    isEmpty.setVisibility(View.GONE);
                    final ListView list = findViewById(R.id.list);
                    list.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, note_list);
                    list.setAdapter(arrayAdapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String clickedItem = (String) list.getItemAtPosition(position);
                            Intent intent = new Intent(MainActivity.this, ViewNote.class);
                            intent.putExtra(EXTRA_MESSAGE, clickedItem);
                            startActivity(intent);
                        }
                    });
                }
                else{
                    final ListView list = findViewById(R.id.list);
                    list.setVisibility(View.GONE);
                    TextView isEmpty = findViewById(R.id.ifEmpty);
                    isEmpty.setVisibility(View.VISIBLE);
                }
            }


    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
