package com.jeetg57.notetakerapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ViewNote extends AppCompatActivity {
    String filename;
    public static final String EXTRA_MESSAGE = "com.jeetg57.notetakerapp.FILENAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        Intent intent = getIntent();
        filename = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        loadData();

    }

    private void loadData() {
        TextView note = findViewById(R.id.note);
        TextView title = findViewById(R.id.title);
        title.setText(filename);
        final File newfile = new File(this.getFilesDir() + "/MyNotes/" + filename);
        boolean isPresent = true;
        if (!newfile.exists()) {
            isPresent = newfile.mkdir();
        }
        if (isPresent) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(newfile));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {
            }
            String result = text.toString();
            note.setText(result);
        }
    }
    public void update(View v){
        Intent intent = new Intent(this,UpdateNote.class);
        intent.putExtra(EXTRA_MESSAGE, filename);
        startActivity(intent);
    }

    public void delete(){
        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code

                        File docsFolder = new File(context.getFilesDir(), "/MyNotes/" + filename);
                        boolean isPresent = true;
                        if (!docsFolder.exists()) {
                            isPresent = docsFolder.mkdir();
                        }
                        if (isPresent) {
                            boolean deleted = docsFolder.delete();
                            Log.v("log_tag", "deleted: " + deleted);
                            finish();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_some_notes, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.delete_note:
                delete();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
