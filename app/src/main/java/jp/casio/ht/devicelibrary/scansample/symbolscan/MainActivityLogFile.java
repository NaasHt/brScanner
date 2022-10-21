package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class MainActivityLogFile extends AppCompatActivity {
    private Bundle savedInstanceState;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView myTextLogFile;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logfile);
        Button ChangeActivity10 = findViewById(R.id.btnBack);
        myTextLogFile = findViewById(R.id.textViewLogFIle);

        ChangeActivity10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity();
            }
        });

        try {
            String fileName = SessionInfo.filePath;
            File file = new File(fileName);
            StringBuilder text = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\s{2,40}");
                if(data.length > 3) { //with name
                    text.append(data[0]).append(" ").
                            append(data[1]).append(" ").
                            append(data[2]).append("\n").
                            append(data[4]).append("\n");
                } else {
                    text.append(data[0]).append(" ").
                            append(data[1]).append("\n").
                            append(data[2]).append("\n");
                }
            }
            myTextLogFile.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void ChangeActivity(){
        Intent intent =new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}