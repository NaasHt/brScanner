package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import jp.casio.ht.devicelibrary.ScannerLibrary;

public class MainActivity3 extends AppCompatActivity {

    String mText, mText1;
    static final int READ_BLOCK_SIZE = 100;

    @SuppressLint("StaticFieldLeak")
    private static TextView mTextView1, editTxtAmount;
    private static ScannerLibrary mScanLib;
    private static ScannerLibrary.ScanResult mScanResult;
    //private static MainActivity2.ScanResultReceiver mScanResultReceiver;
    private Bundle savedInstanceState;
    private static final int WRITE_EXTERNAL_STORAGE_CODE =1;



    private static ScannerLibrary getmScanLib() {
        if (mScanLib == null) {
            mScanLib = new ScannerLibrary();
        }
        return mScanLib;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Button btnChangeBack = findViewById(R.id.btnBack2);


        btnChangeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity8();

            }
        });
        {
            File path = Environment.getDataDirectory();
            File dir = getFilesDir();
            File file = new File( dir, path + "likuciai_ex.txt");

            if (file.exists()){
                Toast.makeText(getBaseContext(), "File exist", Toast.LENGTH_SHORT).show();
            }


            else{
                // Print message if it does not exists
                Toast.makeText(getBaseContext(), "File does not exist", Toast.LENGTH_SHORT).show();
            }



        }

    }

//    public void OnClick(View v){
//        File path = Environment.getDataDirectory();
//        File file = new File(path +"worker");
//
//        BufferedReader = new BufferedReader(bw);
//
//        {
//
//        }
//
//
//    }






//            public void onClick(View v) {
//
//                if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
//
//                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                            == PackageManager.PERMISSION_GRANTED) {
//                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                        requestPermissions(permissions, WRITE_EXTERNAL_STORAGE_CODE);
//
//
//                    }
//                }
//            }

//    public class ScanResultReceiver extends BroadcastReceiver {
//
//
//        public void onReceive(Context context, Intent intent) {
//
//            if (mScanLib != null) {
//                //3. Read barcode
//                getmScanLib().getScanResult(mScanResult);
//                if (mScanResult.length > 0) {
//                    mTextView1.setText(new String(mScanResult.value));
//
//                } else {
//                    mTextView1.setText("");
//                }
//            }
//            TextView tvNextLine = findViewById(R.id.textView8);
//            TextView mTextView2 = findViewById(R.id.textView2);
////            TextView barcode=(TextView) mTextView1.findViewById(R.id.textView1);
////            barcode.setText(mTextView1);
//
//            BufferedReader reader;
//
//            try{
//                final InputStream file = getResources().openRawResource(R.raw.likuciai_ex);
//                reader = new BufferedReader(new InputStreamReader(file));
//
//                String line = reader.readLine();
//
//                while(line != null){
//                    line = reader.readLine();
//                    if(line.contains("0011210000018"))
//                    {
//                        String[] words = line.split("\\s{2,40}");
//                        tvNextLine.setText(words[1]);
//                        break;
//                    }
//                }
//            } catch(IOException ioe){
//                ioe.printStackTrace();
//            }
//        }
//    }
//





    private void ChangeActivity8(){
        Intent intent =new Intent (this, MainActivity.class);
        startActivity(intent);
    }









}
