package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.BreakIterator;

import jp.casio.ht.devicelibrary.ScannerLibrary;

public class MainActivity2 extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static TextView mTextView1, txtShow,txtNumber;
    private static ScannerLibrary mScanLib;
    private static ScannerLibrary.ScanResult mScanResult;
    private static ScanResultReceiver mScanResultReceiver;

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

        //1. Init Scanner
        mScanLib = new ScannerLibrary();
        mScanResult = new ScannerLibrary.ScanResult();
        mScanResultReceiver = new ScanResultReceiver();
        getmScanLib().openScanner();

        mTextView1 = (TextView) findViewById(R.id.textView1);
        txtShow= (TextView) findViewById(R.id.txtShow);

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
                    mTextView1.setText(new String(mScanResult.value));
                } else {
                    mTextView1.setText("");
                    //txtNumber.setText("");
                }
            }
        }
    }



    public void write(View view) throws IOException {

        String TEXT_FILE = "C:/Users/osipo/term002.txt";
        //File term002 = new File("C:\\Users\\osipo\\Downloads");
        String myTxt = mTextView1.getText().toString();


        try {
            FileOutputStream fileOutput = openFileOutput("term002.txt", MODE_PRIVATE);
            fileOutput.write(myTxt.getBytes());
            fileOutput.close();
            //txtNumber.setText("");
            Toast.makeText(MainActivity2.this, "TEXT SAVED",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void read(View view){
        try{

            FileInputStream fileInput = openFileInput("term002.txt");
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader buffer = new BufferedReader(reader);
            StringBuffer strBuffer = new StringBuffer();
            String lines;
            while((lines = buffer.readLine())!= null){
                strBuffer.append(lines).append("\n");
            }
            txtShow.setText(strBuffer.toString());
            fileInput.close();
        }catch(FileNotFoundException e){e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
//
//   public static void main(String[] args) throws IOException {
//        String fileSeparator = System.getProperty("file.separator");
//
//    //absolute file name with path
//        String absoluteFilePath = fileSeparator+"Users"+fileSeparator+"osipo"+fileSeparator+"Downloads"+fileSeparator+"term002.txt";
//        File file = new File(absoluteFilePath);
//            if(file.createNewFile()){
//            System.out.println(absoluteFilePath+" File Created");
//        }else System.out.println("File "+absoluteFilePath+" already exists");
////
////
//            //file name only
//            file = new File("term002.txt");
//            if(file.createNewFile()){
//                System.out.println("file.txt File Created in Project root directory");
//            }else System.out.println("File file.txt already exists in the project root directory");
//
//            //relative path
//            // String relativePath = "tmp"+fileSeparator+"file.txt";
//            file = new File(absoluteFilePath);
//            if(file.createNewFile()){
//                System.out.println(absoluteFilePath+" File Created in Project root directory");
//            }else System.out.println("File "+absoluteFilePath+" already exists in the project root directory");
//
//    }





}






