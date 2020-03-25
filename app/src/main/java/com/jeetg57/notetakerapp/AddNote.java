package com.jeetg57.notetakerapp;

import android.content.Context;
import android.content.DialogInterface;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNote extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        File file = new File(this.getFilesDir(), "/MyNotes" );
        if(!(file.exists())) {
            file.mkdir();
        }
}

    public void saveData(View view) {
        EditText title = findViewById(R.id.fileName);
        String titles = title.getText().toString().trim();
        EditText editText = findViewById(R.id.scrollView);
        String fileContents = editText.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy \nHH:mm", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        String fileCont = "Created: \n" + currentDateandTime + "\n\n" + fileContents;
        if (titles.length() != 0 && fileContents.length() != 0) {
            Context  context   = this;
            File file = new File(context.getFilesDir(), "/MyNotes/" + titles);

            if (!file.exists()) {
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    fos.write(fileCont.getBytes());
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
                Toast.makeText(AddNote.this,
                        "Note Added Successfully", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(AddNote.this,
                        "A note with this name already exists", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(AddNote.this,
                    "Make sure you have a title and some content", Toast.LENGTH_LONG).show();
        }
    }



    public void cancel(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setTitle("Are you sure you want to cancel?")
                .setMessage("You will lose everything you might have typed")
                .setIcon(R.drawable.ic_error_outline_24px)
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        finish();
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
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

}
