package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.casio.ht.devicelibrary.ScannerLibrary;

public class MainActivity2 extends AppCompatActivity {

    private static TextView mTextView1;
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
                }
            }
        }
    }

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
    Date now = new Date();
    String fileName = formatter.format(now) + ".txt";//like 2016_01_12.txt


         try
    {
        File root = new File(Environment.getExternalStorageDirectory()+File.separator+"Music_Folder", "Report Files");
        //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
        if (!root.exists())
        {
            root.mkdirs();
        }
        File gpxfile = new File(root, fileName);


        FileWriter writer = new FileWriter(gpxfile,true);
        writer.append(Body+"\n\n");
        writer.flush();
        writer.close();
        Toast.makeText(this, "Data has been written to Report File", Toast.LENGTH_SHORT).show();
    }
            catch(IOException e)
    {
        e.printStackTrace();

    }
}



