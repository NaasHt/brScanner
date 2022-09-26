package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import jp.casio.ht.devicelibrary.ScannerLibrary;

public class MainActivity3 extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    private static TextView txtLogin;
    private static ScannerLibrary mScanLib;
    private static ScannerLibrary.ScanResult mScanResult;
    private static MainActivity2.ScanResultReceiver mScanResultReceiver;
    private Bundle savedInstanceState;

    private static ScannerLibrary getmScanLib() {
        if (mScanLib == null) {
            mScanLib = new ScannerLibrary();
        }
        return mScanLib;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        1. Init Scanner
        mScanLib = new ScannerLibrary();
        mScanResult = new ScannerLibrary.ScanResult();
        mScanResultReceiver = new MainActivity2.ScanResultReceiver();
        getmScanLib().openScanner();

        txtLogin = (TextView) findViewById(R.id.txtLogin);
        //txtShow= (TextView) findViewById(R.id.txtShow);

    }


    @Override
    protected void onStart() {
        super.onStart();
        getmScanLib().setTriggerKeyTimeout(3000);
        getmScanLib().setOutputType(ScannerLibrary.CONSTANT.OUTPUT.USER);


    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("device.scanner.EVENT");
        filter.addAction("device.common.USERMSG");
        registerReceiver(mScanResultReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mScanResultReceiver);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //4. Deinit Scanner
        getmScanLib().closeScanner();
        mScanLib = null;
        mScanResult = null;
        super.onDestroy();
    }

    public static class ScanResultReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (mScanLib != null) {
                //3. Read barcode
                getmScanLib().getScanResult(mScanResult);
                if (mScanResult.length > 0) {
                    txtLogin.setText(new String(mScanResult.value));
                } else {
                    txtLogin.setText("");
                }
            }
        }
    }
//
//    private void ChangeActivity() {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }



//    private void initView(){
//        btnScanBarcode = findViewById(R.id.txtLogin);
//        btnScanBarcode.set
//    }

    public static class FileSearch {

        public void parseFile(String fileName, String searchStr) throws FileNotFoundException {
            Scanner scan = new Scanner(new File(fileName));
            while (scan.hasNext()) {
                String line = scan.nextLine().toLowerCase().toString();
                if (line.contains(searchStr)) {
                    System.out.println(line);
                }
            }
        }


        public static void main(String[] args) throws FileNotFoundException {
            FileSearch fileSearch = new FileSearch();
            fileSearch.parseFile("res/raw/likuciai_ex.txt", "");
        }

    }

    private  class ReadFromFileUsingScanner {
        public void main(String[] args) throws Exception {
            // pass the path to the file as a parameter
            File file = new File("res/raw/likuciai_ex.txt");
            Scanner sc = new Scanner(file);
            boolean found = true; //if file exist

            if (!found) {
                try (PrintWriter pw = new PrintWriter((new FileWriter(file, true)))) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity3.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Please, contact with administrator");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();


//                    println("smth"); //here add pop up message "file ex
                }
            }
            while (sc.hasNextLine())
                System.out.println(sc.nextLine());

//            else(ChangeActivity){
//                ChangeActivity
            }



        }


    }
