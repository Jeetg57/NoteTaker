package com.jeetg57.notetakerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class UpdateNote extends AppCompatActivity {
    String filename;
    File newfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        Intent intent = getIntent();
        filename = intent.getStringExtra(ViewNote.EXTRA_MESSAGE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        newfile = new File(this.getFilesDir(), "/MyNotes");


        if(!newfile.exists()) {
            newfile.mkdir();
        }
        loadData();
    }
    private void loadData() {
        EditText note = findViewById(R.id.note_et);
        EditText title = findViewById(R.id.title);
        title.setText(filename);
        File fileEvents = new File(this.getFilesDir(),"/MyNotes/" + filename);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileEvents));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException ignored) {
        }
        String result = text.toString();
        note.setText(result);
        note.requestFocus();
    }
    public void update(View v){
        final Context context = this;
        EditText title = findViewById(R.id.title);
        String filecompare = title.getText().toString();
        if(filecompare.isEmpty()){
            Toast.makeText(context, "Please input a title", Toast.LENGTH_SHORT).show();
        }
        else if(!filename.contains(filecompare)){
            File docsFolder = new File(context.getFilesDir(), "/MyNotes/" + filename);
            boolean deleted = docsFolder.delete();
            updateNote();
            Toast.makeText(context, filename + "and "+ filecompare, Toast.LENGTH_SHORT).show();
            Toast.makeText(UpdateNote.this,
                    "Note Updated Successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
    }
        else{
            updateNote();
            Toast.makeText(UpdateNote.this,
                    "Note Updated Successfully", Toast.LENGTH_LONG).show();
            finish();
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    private void updateNote(){
        EditText title = findViewById(R.id.title);
        String titles = title.getText().toString().trim();
        EditText editText = findViewById(R.id.note_et);
        String fileContents = editText.getText().toString();
        File file = new File(this.getFilesDir(), "/MyNotes/" + titles);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(fileContents.getBytes());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
