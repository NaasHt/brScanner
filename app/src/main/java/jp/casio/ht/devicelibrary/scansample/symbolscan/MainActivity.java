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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Bundle savedInstanceState;
    AlertDialog.Builder builder;
    @SuppressLint("StaticFieldLeak")

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;



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
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);
        {
            final String FILENAME = "likuciai_ex.txt";
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
//                    Toast.makeText(this, "SD CAN'T BE USED: ", Toast.LENGTH_SHORT).show();

                Log.i("State", "Yes is readable!");
                return;
            }

            File sdPath = Environment.getExternalStorageDirectory();

            sdPath = new File(sdPath.getAbsolutePath() + "/Download/");
            File sdFile = new File(sdPath, FILENAME);

            if (sdFile.exists()) {
                Toast.makeText(MainActivity.this, "File exist", Toast.LENGTH_SHORT).show();

            } else {
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Error!")
                        .setMessage("There is no file. Contact with administrator! ")
                        .setCancelable(true);


                builder.create().show();
            }

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

        changeActivitybtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity4();
            }
        });

        btn_fileopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity5();
            }
        });



        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);




    }

    private void ChangeActivity(){
        Intent intent =new Intent (this, MainActivity2.class);
        startActivity(intent);
    }

    private void ChangeActivity2(){

        Intent intent = new Intent(this, HistoryReceiver.class);
        startActivity(intent);
    }
//    private void ChangeActivity3(){
//        Intent intent = new Intent(this, MainActivity4.class);
//        startActivity(intent);
//    }
    private void ChangeActivity4(){
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
    private void ChangeActivity5(){
        startActivity(new Intent(MainActivity.this, MainActivityLogFile.class));
    }



}