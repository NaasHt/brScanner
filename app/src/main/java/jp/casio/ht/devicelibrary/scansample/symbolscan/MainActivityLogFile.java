
package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
        myTextLogFile = findViewById(R.id.textViewLogFile);

        ChangeActivity10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity();
            }
        });

        try {
//            File sdPath = Environment.getExternalStorageDirectory();
//            sdPath = new File(sdPath.getAbsolutePath() + "/Download/");
//            File file = new File(sdPath, FILENAME);
            File file = SessionInfo.getDatFile();
            //File file = new File(fileName);
            StringBuilder text = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                StockRecord record = new StockRecord(line);
                    text.append(record.getBarcode()).append("\t").
                            append(record.getQuantity()).append("\n");
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