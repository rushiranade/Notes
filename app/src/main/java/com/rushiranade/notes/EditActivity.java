package com.rushiranade.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        final Intent intent = getIntent();
        final String heading = intent.getStringExtra(Note.HEADER_MSG);
        try {
            FileInputStream fis = openFileInput(heading + ".txt");
            BufferedReader reader = new BufferedReader( new InputStreamReader( fis ) );
            String line;
            String whole = "";
            while ( (line = reader.readLine()) != null ) {
                if(whole == "") {
                    whole = whole + line;
                }else{
                    whole = whole + "\n" + line;
                }
            }
            reader.close();
            final EditText editText = (EditText) findViewById(R.id.contentfield);
            editText.setText(whole);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final EditText editText = (EditText) findViewById(R.id.contentfield);
        TextView textView = (TextView) findViewById(R.id.heading_view);
        textView.setText(heading);
        Button button_save = (Button) findViewById(R.id.savebutton);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = heading + ".txt";
                if(!editText.getText().toString().trim().isEmpty()){
                    File dir = getFilesDir();
                    File file = new File(dir, filename);
                    file.delete();
                    try {
                        FileOutputStream fOut = openFileOutput(filename, Context.MODE_PRIVATE);
                        fOut.write(editText.getText().toString().trim().getBytes());
                        fOut.close();
                        TextView status_view = (TextView) findViewById(R.id.status);
                        status_view.setText("SAVED!");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    editText.setError("Content can't be empty");
                }
            }
        });
        Button button = (Button) findViewById(R.id.back_home);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}