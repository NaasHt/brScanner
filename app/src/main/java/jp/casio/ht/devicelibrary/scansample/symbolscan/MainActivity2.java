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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.casio.ht.devicelibrary.ScannerLibrary;

public class MainActivity2 extends AppCompatActivity {

    //    EditText editTxtAmount;
    Button mBtnSave, mBtnExit;
    String mText;
    String mText1;
    String mName;
    //    String mtxtLines;
    String mPrice;
    View datName;
    ArrayList<String> list;
    AlertDialog.Builder builder;
    private long lastRecordStart = -1;
    static final int READ_BLOCK_SIZE = 100;

    @SuppressLint("StaticFieldLeak")
    private EditText editTxtAmount;
    private static TextView mTextView1, tvNextLine, mTextView2, textView19, txtLines, txtAmount, txtTotalAmount;
    private static ScannerLibrary mScanLib;
    private static ScannerLibrary.ScanResult mScanResult;
    private static ScanResultReceiver mScanResultReceiver;
    private Bundle savedInstanceState;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
    private static final int READ_EXTERNAL_STORAGE_CODE = 1;
    private TextView SmallBarcode;


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
        //editTxtAmount.setFocusableInTouchMode(true);
        editTxtAmount.requestFocus();
        mBtnSave = findViewById(R.id.btnSave);
        mBtnExit = findViewById(R.id.btnExit);

        //1. Init Scanner
        mScanLib = new ScannerLibrary();
        mScanResult = new ScannerLibrary.ScanResult();
        mScanResultReceiver = new ScanResultReceiver();
        getmScanLib().openScanner();
        mTextView1 = (TextView) findViewById(R.id.textView1);
        datName = findViewById(R.id.textView19);
        tvNextLine = (TextView) findViewById(R.id.textName);
        mTextView2 = (TextView) findViewById(R.id.textPrice);
        mTextView1 = (TextView) findViewById(R.id.textView1);
        SmallBarcode = (TextView) findViewById(R.id.txtBarcode2);
        //txtAmount = (TextView) findViewById(R.id.textViewAmount);
        txtLines = (TextView) findViewById(R.id.txtLines);
        txtTotalAmount = (TextView) findViewById(R.id.textViewAmount);
        TextView Name = findViewById(R.id.textView19);
        Name.setText(SessionInfo.userName);

        try {
            amount();
        } catch (Exception e) {
            e.printStackTrace();
        }




        //txtShow= (TextView) findViewById(R.id.txtShow);
        Button changeActivityExit = findViewById(R.id.btnExit);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


        mTextView1.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    Log.i("BarcodeLFocus","Focus lost");
                } else {
                    Log.i("BarcodeLFocus","Focus gained");
                }
            }
        });

        editTxtAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i("BarcodeLFocus","Amount Focus " + hasFocus);
            }
        });

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
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (mTextView1.getText().length() > 0) {
                        setBarcode(mTextView1.getText().toString());
                    }
                    return true;
                }
                return false;
            }

        });




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
                SmallBarcode.setText("");
            } else {
                Toast.makeText(this, "Storage permission is required to store data", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void saveToTxtFile(String barcode, String amount, String mName, String mPrice) {

        String timeStamp1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis());
        String documentId = SessionInfo.getUserName();//MainActivity3.txtLogin;//todo user login
        String documentIdContent = String.format("%-14s", documentId);
        String barcodeContent = String.format("%-20s", barcode);
        String amountContent = String.format("%14s", amount);
        String contestsToAppend = (documentIdContent + "," + barcodeContent + "," + amountContent + ",,, " + timeStamp1);
        try {
            File file = new File(SessionInfo.filePath);
//                        if (file.exists() || file.createNewFile()) {
//                SessionInfo.filePath = file.getAbsolutePath();
//                Toast.makeText(MainActivity2.this, "File created", Toast.LENGTH_SHORT).show();
//            }
//            if (lastRecordStart >= 0) {
//                try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")) {
//                    randomAccessFile.setLength(lastRecordStart);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                lastRecordStart = -1;
//            }
            try (FileOutputStream fOut = new FileOutputStream(file.getAbsoluteFile(), true);
                 OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut)) {
                myOutWriter.append(contestsToAppend).append("\n");
            }
            SessionInfo.append(new StockRecord(contestsToAppend));
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

    public class ScanResultReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            if (mTextView1.getText().length() > 0) {
                Toast.makeText(MainActivity2.this, "Save are not pressed", Toast.LENGTH_SHORT).show();
                return;
            }
            String digit = "";
            String barcode = "";
            if (mScanLib != null) {
                //3. Read barcode
                getmScanLib().getScanResult(mScanResult);
                if (mScanResult != null && mScanResult.length > 0) {
                    mTextView1.setText(new String(mScanResult.value));
                    barcode = new String(mScanResult.value).trim();
                    setBarcode(barcode);
                    digit = barcode.substring(barcode.length() - 4);
                    SmallBarcode.setText(digit);
                } else {
                    mTextView1.setText("");
                    SmallBarcode.setText("");
                }
            }
        }
    }

    private void setBarcode(String barcode) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.i("State", "Yes is readable!");
            return;
        }
        boolean wasBound = fillByBarcode(barcode);
        if (!wasBound) {
            tvNextLine.setText("------------");
            mTextView2.setText("------------");
            mTextView1.clearFocus();
            editTxtAmount.requestFocus();
            editTxtAmount.setFocusedByDefault(true);
            editTxtAmount.setText("1");
            editTxtAmount.setSelection(0, editTxtAmount.getText().length());
        } else {
            editTxtAmount.requestFocus();
            editTxtAmount.setFocusedByDefault(true);
            mTextView1.clearFocus();
            editTxtAmount.setText("1");
            editTxtAmount.setSelection(0, editTxtAmount.getText().length());
        }
        fillTotalAmount(barcode);
    }

    private boolean fillByBarcode(String barcode) {
        GoodsRecord goodsRecord = SessionInfo.getGoods(barcode);
        if (goodsRecord != null) {
            tvNextLine.setText(goodsRecord.getName());
            mTextView2.setText(goodsRecord.getPrice());
            return true;
        } else {
            return false;
        }
    }

    private void fillTotalAmount(String barcode) {
//        File file = SessionInfo.getDatFile();
//        try {
//            int totalAmount = Files.readAllLines(file.toPath()).
//                    stream().
//                    map(StockRecord::new).
//                    filter(stockRecord -> stockRecord.getBarcode().equals(barcode)).
//                    mapToInt(StockRecord::getQuantity).
//                    sum();
//            txtTotalAmount.setText(String.valueOf(totalAmount));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        txtTotalAmount.setText(String.valueOf(SessionInfo.getStockTotalAmount(barcode)));
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
//
//    public static int countLines(File aFile) throws IOException {
//        LineNumberReader reader = null;
//        try {
//            reader = new LineNumberReader(new FileReader(aFile));
//            while ((reader.readLine()) != null) ;
//            return reader.getLineNumber();
//        } catch (Exception ex) {
//            return -1;
//        } finally {
//            if (reader != null)
//                reader.close();
//        }
//    }

//
//    private void amount(String[] arg0) throws IOException {
//        File sdPath = Environment.getExternalStorageDirectory();
//        sdPath = new File(sdPath.getAbsolutePath() + "/Download/");
//        File file = new File(sdPath, "term001.dat");
//        if(file.exists()){
//            BufferedReader reader = new BufferedReader(new FileReader("term001.dat"));
//            int lines = 0;
//            while (reader.readLine() != null) lines++;
//            reader.close();
////            FileReader fr = new FileReader(file);
////            LineNumberReader lnr = new LineNumberReader(fr);
////            int number = 0;
////            while (lnr.readLine() != null){
////                number++;
////
////            }
//            txtLines.setText("1");
//
////            lnr.close(); setText(lines);
//        }
//
//
//
//    }






    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_F1 == keyCode) {
            mTextView1.setText("");
            editTxtAmount.setText("");
            tvNextLine.setText("");
            mTextView2.setText("");
            SmallBarcode.setText("");
            return true;
        } else if (KeyEvent.KEYCODE_F4 == keyCode) {
            editLast();
            return true;
        }
        return false;
    }

    private void editLast() {

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(SessionInfo.getDatFile(), "r")) {
            long length = randomAccessFile.length() - 1;
            byte b;
            do {
                length -= 1;
                randomAccessFile.seek(length);
                b = randomAccessFile.readByte();
            } while (b != 10 && length > 0);
            String lastLine = randomAccessFile.readLine();
            if (lastLine != null) {
                Toast.makeText(this, "Last is " + lastLine.split(" ")[0], Toast.LENGTH_LONG).show();
                StockRecord lastRecord = new StockRecord(lastLine);
                mTextView1.setText(lastRecord.getBarcode());
                fillByBarcode(lastRecord.getBarcode());
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

//    public void summary() {
//
//        File file = SessionInfo.getDatFile();
//        try {
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                Map<String, Integer> stock =
//                        Files.readAllLines(file.toPath()).
//                                stream().
//                                map(StockRecord::new).
//                                collect(Collectors.groupingBy(StockRecord::getBarcode,
//                                        Collectors.summingInt(StockRecord::getQuantity)));
//
//                String lines = stock.entrySet().stream().
//                        map(e -> e.getKey() + '\t' + e.getValue()).
//                        collect(Collectors.joining("\n"));
//                txtTotalAmount.setText(lines);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }

}

