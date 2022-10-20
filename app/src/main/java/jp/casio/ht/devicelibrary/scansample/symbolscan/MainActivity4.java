package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import jp.casio.ht.devicelibrary.ScannerLibrary;

public class MainActivity4 extends AppCompatActivity {

    Button mBtnSave, mBtnExit;
    String mText, mText1, mName, mPrice;
    ArrayList<String> list;
    AlertDialog.Builder builder;
    static final int READ_BLOCK_SIZE = 100;

    @SuppressLint("StaticFieldLeak")
    private static TextView mTextView1, editAmount, tvNextLine, mTextView2;
    private static ScannerLibrary mScanLib;
    private static ScannerLibrary.ScanResult mScanResult;
    private static MainActivity2.ScanResultReceiver mScanResultReceiver;
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
        editAmount = (EditText) findViewById(R.id.editAmount);
        mBtnSave = findViewById(R.id.btnSave);
        mBtnExit = findViewById(R.id.btnExit);
        tvNextLine = (TextView) findViewById(R.id.textName);
        mTextView2 = (TextView) findViewById(R.id.textPrice);
        mTextView1 = (TextView) findViewById(R.id.textView1);
        //txtShow= (TextView) findViewById(R.id.txtShow);
        //Button changeActivityExit = findViewById(R.id.btnExit);
        mBtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(MainActivity4.this);
                builder.setTitle("Alert")
                        .setMessage("Are you sure to exit")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(MainActivity4.this, MainActivity.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("No", null);

                builder.create().show();
            }
        });


        final String FILENAME = "likuciai_ex.txt";
//
//        public class textResultReceiver extends BroadcastReceiver {
//
//
//            public void onReceive(Context context, Intent intent) {
//                if (mTextView1.getText().length() > 0) {
//                    Toast.makeText(MainActivity4.this, "Save are not pressed", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//
//
//                if (!Environment.getExternalStorageState().equals(
//                        Environment.MEDIA_MOUNTED)) {
////                    Toast.makeText(this, "SD CAN'T BE USED: ", Toast.LENGTH_SHORT).show();
//
//                    Log.i("State", "Yes is readable!");
//                    return;
//                }
//
//                File sdPath = Environment.getExternalStorageDirectory();
//
//                sdPath = new File(sdPath.getAbsolutePath() + "/Downloads/");
//                File sdFile = new File(sdPath, FILENAME);
//                try {
//                    BufferedReader reader = new BufferedReader(new FileReader(sdFile));
//
////                    final InputStream file = getResources().openRawResource(R.raw.likuciai_ex);
////                    reader = new BufferedReader(new InputStreamReader(file));
//                    String line = null;
//
//                    while ((line = reader.readLine()) != null) {
//                        if (line.contains(mText1.trim())) {
//                            String[] words = line.split("\\s{2,40}");
//                            mTextView1.setText(words[0]);
//                            tvNextLine.setText(words[1]);
//                            mTextView2.setText(words[2]);
//                            break;
//                        }
//                    }
//
//                } catch (IOException ioe) {
//                    ioe.printStackTrace();
//                }
//
////
////        private void setSelectedText(EditText mTextView2, String text) {
////            mTextView2.setText("1");
////            mTextView2.requestFocus();
////            mTextView2.setSelection(0, mTextView2.getText().length());
////        }
//            }
//        }
//
//





        Button changeActivitybtnEdit = findViewById(R.id.btnExit);

        changeActivitybtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity();
            }
        });
    }



    private void ChangeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


//    private void removeLastLine(File file) throws IOException {
//        try(RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")){
//            long length = randomAccessFile.length() - 1;
//            byte b;
//            do {
//                length -= 1;
//                randomAccessFile.seek(length);
//                b = randomAccessFile.readByte();
//            } while (b != 10 && length > 0);
//            if (length == 0) {
//                randomAccessFile.setLength(length);
//            } else {
//                randomAccessFile.setLength(length + 1);
//            }
//        }
//    }







}
