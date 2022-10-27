package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import jp.casio.ht.devicelibrary.ScannerLibrary;

public class MainActivity2 extends AppCompatActivity {

    //EditText mEditTxtAmount;
    Button mBtnSave, mBtnExit;
    String mText, mText1, mName, mPrice;
    ArrayList<String> list;
    AlertDialog.Builder builder;
    private long lastRecordStart = -1;
    static final int READ_BLOCK_SIZE = 100;

    @SuppressLint("StaticFieldLeak")
    private EditText editTxtAmount;
    private static TextView mTextView1, tvNextLine, mTextView2;
    private static ScannerLibrary mScanLib;
    private static ScannerLibrary.ScanResult mScanResult;
    private static ScanResultReceiver mScanResultReceiver;
    private Bundle savedInstanceState;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
    private static final int READ_EXTERNAL_STORAGE_CODE = 1;


    private static ScannerLibrary getmScanLib() {
        if (mScanLib == null) {
            mScanLib = new ScannerLibrary();
        }
        return mScanLib;
    }

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editTxtAmount = (EditText) findViewById(R.id.editAmount);
        editTxtAmount.setTextIsSelectable(true);
        editTxtAmount.setFocusableInTouchMode(true);
        editTxtAmount.requestFocus();
        mBtnSave = findViewById(R.id.btnSave);
        mBtnSave.setFocusableInTouchMode(true);
        mBtnSave.requestFocus();
        mBtnExit = findViewById(R.id.btnExit);


        //1. Init Scanner
        mScanLib = new ScannerLibrary();
        mScanResult = new ScannerLibrary.ScanResult();
        mScanResultReceiver = new ScanResultReceiver();
        getmScanLib().openScanner();
        mTextView1 = (TextView) findViewById(R.id.textView1);

        tvNextLine = (TextView) findViewById(R.id.textName);
        mTextView2 = (TextView) findViewById(R.id.textPrice);

        mTextView1 = (TextView) findViewById(R.id.textView1);
        //txtShow= (TextView) findViewById(R.id.txtShow);
        Button changeActivityExit = findViewById(R.id.btnExit);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        mBtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(MainActivity2.this);
                builder.setTitle("Alert")
                        .setMessage("Are you sure to exit")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(MainActivity2.this, MainActivity.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("No", null);

                builder.create().show();
            }
        });



        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mText1 = mTextView1.getText().toString().trim();
                mText = editTxtAmount.getText().toString().trim();
                mName = tvNextLine.getText().toString().trim();
                mPrice = mTextView2.getText().toString().trim();


                if (mText1.isEmpty()) {
                    Toast.makeText(MainActivity2.this, "Please enter anything", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (mText.isEmpty()) {
                        Toast.makeText(MainActivity2.this, "Please enter anything", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions, WRITE_EXTERNAL_STORAGE_CODE);
                }
            }

        });

        mTextView1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey (View view,int keyCode, KeyEvent event){
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(mTextView1.getText().length() >0) {
                        setBarcode(mTextView1.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });

//        mTextView1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(!hasFocus) {
//                    editTxtAmount.requestFocus();
//                }
//            }
//        });

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveToTxtFile(mText1, mText, mName, mPrice);
                mTextView1.setText("");
                editTxtAmount.setText("");
                tvNextLine.setText("");
                mTextView2.setText("");
            }
            else {
                Toast.makeText(this, "Storage permission is required to store data", Toast.LENGTH_SHORT).show();
            }
        }

    }



    private void saveToTxtFile(String mText1, String mText, String mName, String mPrice) {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(System.currentTimeMillis());
        String timeStamp1 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis());
        String contestsToAppend = (mText1 + "   " + mName + "   " + mText + "   " + mPrice + "   " + timeStamp1 + "\n");

        File file = new File(SessionInfo.filePath);
        if(lastRecordStart >= 0) {
            try(RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")) {
                randomAccessFile.setLength(lastRecordStart);
            } catch (Exception e) {
                e.printStackTrace();
            }
            lastRecordStart = -1;
        }
        try {
//            File path = Environment.getExternalStorageDirectory();
//            File dir = new File(path + "/Downloads/");
//            String fileName = "MyFile" + timeStamp + ".txt";
            //FileOutputStream fOut=openFileOutput("MyFile" + ".txt", MODE_PRIVATE);
//            File[] file = dir.listFiles(new FileFilter() {
//
//                @Override
//                public boolean accept(File file) {
//                    return file.getName().startsWith("My File");
//                }
//            });

            FileOutputStream fOut = new FileOutputStream(file.getAbsoluteFile(), true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(contestsToAppend);
            myOutWriter.close();
            fOut.close();
            Toast.makeText(MainActivity2.this, "Saved in file", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void ChangeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

    final String FILENAME = "likuciai_ex.txt";

    public class ScanResultReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            if (mTextView1.getText().length() > 0) {
                Toast.makeText(MainActivity2.this, "Save are not pressed", Toast.LENGTH_SHORT).show();
                return;
            }

            String barcode = "";
            if (mScanLib != null) {
                //3. Read barcode
                getmScanLib().getScanResult(mScanResult);
                if (mScanResult != null && mScanResult.length > 0) {
                    mTextView1.setText(new String(mScanResult.value));
                    barcode = new String(mScanResult.value);
                    setBarcode(barcode);
                } else {
                    mTextView1.setText("");
                }
            }
        }
    }

    private void setBarcode(String barcode) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
//                    Toast.makeText(this, "SD CAN'T BE USED: ", Toast.LENGTH_SHORT).show();

            Log.i("State", "Yes is readable!");
            return;
        }

        File sdPath = Environment.getExternalStorageDirectory();

        sdPath = new File(sdPath.getAbsolutePath() + "/Download/");
        File sdFile = new File(sdPath, FILENAME);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(sdFile));
            boolean wasBound = false;
            String line = null;

            //try {
            while ((line = reader.readLine()) != null) {
                if (line.contains(barcode.trim())) {
                    String[] words = line.split("\\s{2,40}");
                    tvNextLine.setText(words[1]);
                    mTextView2.setText(words[2]);
                    wasBound = true;
                    break;
                }
            }
            if (!wasBound) {
                tvNextLine.setText("----");
                mTextView2.setText("----");
            } else {
                mTextView1.clearFocus();
                editTxtAmount.setText("1");
                editTxtAmount.setSelection(0, editTxtAmount.getText().length());
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }



    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_F1 == keyCode) {
            mTextView1.setText("");
            editTxtAmount.setText("");
            tvNextLine.setText("");
            mTextView2.setText("");
            return true;
        } else if(KeyEvent.KEYCODE_F4 == keyCode){
            editLast();
            return true;
        }
        return false;
    }

    private void editLast() {
        try(RandomAccessFile randomAccessFile  = new RandomAccessFile(SessionInfo.filePath, "r")) {
            long length = randomAccessFile.length() - 1;
            byte b;
            do {
                length -= 1;
                randomAccessFile.seek(length);
                b = randomAccessFile.readByte();
            } while (b != 10 && length > 0);
            String lastLine = randomAccessFile.readLine();
            if(lastLine != null) {
                Toast.makeText(this, "Last is " + lastLine.split(" ")[0], Toast.LENGTH_LONG).show();
                StockRecord lastRecord = new StockRecord(lastLine);
                mTextView1.setText(lastRecord.getBarcode());
                tvNextLine.setText(lastRecord.getName());
                editTxtAmount.setText(String.valueOf(lastRecord.getQuantity()));
            }
            if (length == 0) {
                lastRecordStart = length;
            } else {
                lastRecordStart = length + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
