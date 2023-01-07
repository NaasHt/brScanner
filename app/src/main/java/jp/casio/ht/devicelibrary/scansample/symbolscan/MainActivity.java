package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    @SuppressLint("StaticFieldLeak")

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
    @SuppressLint("StaticFieldLeak")
    private static TextView txtLines;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button changeActivityBTN = findViewById(R.id.btnInv);
        Button changeActivityBTNSum = findViewById(R.id.btnSum);
        Button changeActivitybtnLogout = findViewById(R.id.btnLogout);
        Button btn_fileopen = findViewById(R.id.btnLogf);
        TextView Name = findViewById(R.id.textView19);
        txtLines = (TextView) findViewById(R.id.txtLines);
        Name.setText(SessionInfo.userName);

        try {
            amount();
        } catch (Exception e) {
            e.printStackTrace();
        }



        changeActivityBTN.setOnClickListener(v -> ChangeActivity());

        changeActivityBTNSum.setOnClickListener(v -> ChangeActivity2());


        changeActivitybtnLogout.setOnClickListener(v -> {
            builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Alert")
                    .setMessage("Are you sure to exit")
                    .setCancelable(true)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent i = new Intent(MainActivity.this, MainActivity3.class);
                        startActivity(i);
                    })
                    .setNegativeButton("No", null);

            builder.create().show();
        });



        btn_fileopen.setOnClickListener(v -> ChangeActivity5());
    }
    public void amount() throws Exception {
        File file = SessionInfo.getDatFile();
        try (FileReader fileReader =new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader)) {
            int count = 0;
            for (;;) {
                String line = reader.readLine();
                if (line == null)
                    break;
                count++;
            }
            txtLines.setText(String.valueOf(count));
        }
    }



    private void ChangeActivity(){
        Intent intent =new Intent (this, MainActivity2.class);
        startActivity(intent);
    }

    private void ChangeActivity2(){

        Intent intent = new Intent(this, HistoryReceiver.class);
        startActivity(intent);
    }

    private void ChangeActivity5(){
        startActivity(new Intent(MainActivity.this, MainActivityLogFile.class));
    }



}