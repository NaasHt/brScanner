package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


    }

    public static void main(String args[])
    {
        File path = Environment.getDataDirectory();
        File file = new File(path+ "/raw/");

        // Checking if the specified file
        // exists or not
        if (file.exists())

            // Print message if it exists
            System.out.println("Exists");
        else

            // Print message if it does not exists
            System.out.println("Does not Exists");

    }







}
