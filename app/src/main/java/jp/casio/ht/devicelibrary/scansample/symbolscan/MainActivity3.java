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
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import jp.casio.ht.devicelibrary.ScannerLibrary;

public class MainActivity3 extends AppCompatActivity {


    //EditText txtLogin;
    AlertDialog.Builder builder;
    static final int READ_BLOCK_SIZE = 100;

    @SuppressLint("StaticFieldLeak")
    private static TextView muserID;
    private static ScannerLibrary mScanLib;
    private static ScannerLibrary.ScanResult mScanResult;
    private static MainActivity3.ScanResultReceiver mScanResultReceiver;
    private Bundle savedInstanceState;
    private static final int READ_EXTERNAL_STORAGE_CODE = 1;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;


    private static ScannerLibrary getmScanLib() {
        if (mScanLib == null) {
            mScanLib = new ScannerLibrary();
        }
        return mScanLib;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        EditText txtLogin = findViewById(R.id.txtLogin);
        mScanLib = new ScannerLibrary();
        mScanResult = new ScannerLibrary.ScanResult();
        mScanResultReceiver = new MainActivity3.ScanResultReceiver();
        getmScanLib().openScanner();


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
                SessionInfo.installGoods();
                Toast.makeText(MainActivity3.this, "Please enter your ID", Toast.LENGTH_SHORT).show();
            } else {
                builder = new AlertDialog.Builder(MainActivity3.this);
                builder.setTitle("Error!")
                        .setMessage("There is no file. Contact with administrator! ")
                        .setCancelable(true);


                builder.create().show();
            }

        }
        txtLogin.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    try{
                        SessionInfo.setUserName(txtLogin.getText().toString()); //user login
                    try {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED) {
                            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permissions, WRITE_EXTERNAL_STORAGE_CODE);

                        } else {
                            Toast.makeText(MainActivity3.this, "Don't have permission", Toast.LENGTH_SHORT).show();
                        }
                        try {
//                            String documentId = txtLogin;//MainActivity3.txtLogin;//todo user login


                            boolean usedAnotherUser = false;
                            File file = SessionInfo.getDatFile();
                            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                            String fileName = "term001"  + ".dat"; //+ txtLogin.getText()


                            //File file = new File(dir, fileName);
                            if(!file.exists()) {
                                Optional<String> result = Stream.of(dir.list()).
                                        filter(name -> name.startsWith("term") && name.endsWith(".dat")).
                                        findAny();
                                if(result.isPresent()) {
                                    usedAnotherUser = true;
                                    String anotherUser = result.get().replace("term", "").replace(".dat","");
                                    Toast.makeText(MainActivity3.this, "Error.Used by " + anotherUser, Toast.LENGTH_SHORT).show();
                                }
                            }
                            if(!usedAnotherUser) {
                            if (file.exists() || file.createNewFile()) {
                                SessionInfo.filePath = file.getAbsolutePath();
                                Toast.makeText(MainActivity3.this, "File created", Toast.LENGTH_SHORT).show();
                            }
                            if (!file.exists()) {
                                Toast.makeText(MainActivity3.this, "Error.File not created", Toast.LENGTH_SHORT).show();
                            }
                            if(usedAnotherUser){
                                builder = new AlertDialog.Builder(MainActivity3.this);
                                builder.setTitle("Error!")
                                        .setMessage("Scanner is already taken!")
                                        .setCancelable(true);


                                builder.create().show();
                            }


                            else {
                                ChangeActivity8();
                            }
                        }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                return false;
            }
        });

    }


    public class ScanResultReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String txtLogin = "";
            if (mScanLib != null) {
                //3. Read barcode
                getmScanLib().getScanResult(mScanResult);
                if (mScanResult != null && mScanResult.length > 0) {
                    txtLogin = new String(mScanResult.value);
                }
            }
        }
    }

    private void ChangeActivity8() {
        Intent intent = new Intent(this, MainActivity.class); //MainActivity2
        startActivity(intent);
    }


}









