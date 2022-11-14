package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {

    private Bundle savedInstanceState;
    AlertDialog.Builder builder;
    @SuppressLint("StaticFieldLeak")

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
    private static TextView txtLines;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button changeActivityBTN = findViewById(R.id.btnInv);
        Button changeActivityBTNSum = findViewById(R.id.btnSum);
        //Button changeActivityBTNEdit = findViewById(R.id.btnEdit);
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



        changeActivityBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity();
            }
        });

        changeActivityBTNSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity2();

            }
        });
//
//        changeActivityBTNEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ChangeActivity3();
//            }
//        });
        changeActivitybtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Alert")
                        .setMessage("Are you sure to exit")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(MainActivity.this, MainActivity3.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("No", null);

                builder.create().show();
            }
        });



        btn_fileopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity5();
            }
        });
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

    private void ChangeActivity4(){
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
    private void ChangeActivity5(){
        startActivity(new Intent(MainActivity.this, MainActivityLogFile.class));
    }



}