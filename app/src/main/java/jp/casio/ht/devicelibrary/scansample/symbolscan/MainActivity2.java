package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Locale;

import jp.casio.ht.devicelibrary.ScannerLibrary;

public class MainActivity2 extends AppCompatActivity {

    Button mBtnSave, mBtnExit;
    String mText;
    String mText1;
    String mName;
    String mPrice;
    View datName;
    AlertDialog.Builder builder;

    @SuppressLint("StaticFieldLeak")
    private EditText mTextView1, editTxtAmount;
    @SuppressLint("StaticFieldLeak")
    private static TextView tvNextLine, mTextView2, txtLines, txtTotalAmount;
    private static ScannerLibrary mScanLib;
    private static ScannerLibrary.ScanResult mScanResult;
    private static ScanResultReceiver mScanResultReceiver;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
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
        mTextView1 = findViewById(R.id.textView1);
        editTxtAmount = findViewById(R.id.editAmount);
        editTxtAmount.setTextIsSelectable(true);
        mBtnSave = findViewById(R.id.btnSave);
        mBtnExit = findViewById(R.id.btnExit);
        mScanLib = new ScannerLibrary();
        mScanResult = new ScannerLibrary.ScanResult();
        mScanResultReceiver = new ScanResultReceiver();
        getmScanLib().openScanner();
        datName = findViewById(R.id.textView19);
        tvNextLine = findViewById(R.id.textName);
        mTextView2 =  findViewById(R.id.textPrice);
        SmallBarcode = findViewById(R.id.txtBarcode2);
        txtLines = findViewById(R.id.txtLines);
        txtTotalAmount = findViewById(R.id.textViewAmount);
        TextView Name = findViewById(R.id.textView19);
        Name.setText(SessionInfo.userName);
        requestFocusToBarcode();
        try {
            amount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        mBtnExit.setOnClickListener(v -> {
            builder = new AlertDialog.Builder(MainActivity2.this);
            builder.setTitle("Alert")
                    .setMessage("Are you sure to exit")
                    .setCancelable(true)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent i = new Intent(MainActivity2.this, MainActivity.class);
                        startActivity(i);
                    })
                    .setNegativeButton("No", null);

            builder.create().show();
        });


        mBtnSave.setOnClickListener(v -> {

        });

        editTxtAmount.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                mText1 = mTextView1.getText().toString().trim();
                mText = editTxtAmount.getText().toString().trim();
                mName = tvNextLine.getText().toString().trim();
                mPrice = mTextView2.getText().toString().trim();
                if (mText.isEmpty()) {
                    new Handler().post(mBtnExit::requestFocus);
                } else {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, WRITE_EXTERNAL_STORAGE_CODE);
                    }
                }
            } else if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                requestFocusToBarcode();
            }
            return false;
        });

        mTextView1.setOnKeyListener((v, keyCode, event) -> {

            if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
                if(mTextView1.getText().length()==0){
                    mBtnExit.requestFocus();
                }

            }
            if(mTextView1.getText().length()>0 && keyCode == KeyEvent.KEYCODE_ENTER){
                setBarcode(mTextView1.getText().toString());
            }
            return false;
        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveToTxtFile(mText1, mText);
                mTextView1.setText("");
                editTxtAmount.setText("");
                tvNextLine.setText("");
                mTextView2.setText("");
                SmallBarcode.setText("");
                txtTotalAmount.setText("");
                requestFocusToBarcode();
            } else {
                Toast.makeText(this, "Storage permission is required to store data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveToTxtFile(String barcode, String amount) {

        String timeStamp1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis());
        String documentId = "In" + SessionInfo.getUserName();
        String documentIdContent = String.format("%-14s", documentId);
        String barcodeContent = String.format("%-20s", barcode);
        String amountContent = String.format("%14s", amount);
        String contestsToAppend = (documentIdContent + "," + barcodeContent + "," + amountContent + ",,, " + timeStamp1);
        try {
            File file = new File(SessionInfo.filePath);
            try (FileOutputStream fOut = new FileOutputStream(file.getAbsoluteFile(), true);
                 OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut)) {
                myOutWriter.append(contestsToAppend).append("\n");
            }
            SessionInfo.append(new StockRecord(contestsToAppend));
            //Toast.makeText(MainActivity2.this, "Saved in file", Toast.LENGTH_SHORT).show();
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
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

            String barcode;
            if (mScanLib != null) {
                //3. Read barcode
                getmScanLib().getScanResult(mScanResult);
                if (mScanResult != null && mScanResult.length > 0) {
                    mTextView1.setText(new String(mScanResult.value));
                    barcode = new String(mScanResult.value).trim();
                    setBarcode(barcode);
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
        if(barcode.length()>=4) {
            SmallBarcode.setText(barcode.substring(barcode.length() - 4));
        }


        boolean wasBound = fillByBarcode(barcode);
        if (wasBound) {
            fillDefaultQuantity("1");

        } else {
            tvNextLine.setText("------------");
            mTextView2.setText("------------");
            if (barcode.startsWith("23")) {
                double weight = Integer.parseInt(barcode.substring(8, 12)) / 1000.0;
                fillDefaultQuantity((String.valueOf(weight)));
            } else if (barcode.startsWith("260")) {
                int quantity =  Integer.parseInt(barcode.substring(7, 12));
                fillDefaultQuantity(String.valueOf(quantity));
            } else if (barcode.startsWith("27")) {
                double weight = Integer.parseInt(barcode.substring(7, 12)) / 1000.0;
                fillDefaultQuantity((String.valueOf(weight)));
            } else {
                fillDefaultQuantity("1");
            }
        }
        fillTotalAmount(barcode);
    }


    private void fillDefaultQuantity(String quantity) {
        editTxtAmount.setText(quantity);
        editTxtAmount.setSelection(0, editTxtAmount.getText().length());
        requestFocusToAmount();
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

        txtTotalAmount.setText(String.valueOf(SessionInfo.getStockTotalAmount(barcode)));
    }


    public void amount() throws Exception {
        File file = SessionInfo.getDatFile();
        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader)) {
            int count = 0;
            for (; ; ) {
                String line = reader.readLine();
                if (line == null)
                    break;
                count++;

            }
            txtLines.setText(String.valueOf(count));
        }

    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_F1 == keyCode) {
            mTextView1.setText("");
            editTxtAmount.setText("");
            tvNextLine.setText("");
            mTextView2.setText("");
            SmallBarcode.setText("");
            txtTotalAmount.setText("");
            requestFocusToBarcode();
            return true;
        }else if (KeyEvent.KEYCODE_F4 == keyCode) {
            editLast();
            return true;
        }

        return false;
    }

    private void requestFocusToBarcode() {

        final Handler handler = new Handler();
        handler.post(
                () -> {
                    editTxtAmount.clearFocus();
                    editTxtAmount.post(() -> mTextView1.requestFocus());
                }
        );
    }

    private void requestFocusToAmount() {

        final Handler handler = new Handler();
        handler.post(() -> {
            mTextView1.clearFocus();
            mTextView1.post(() -> editTxtAmount.requestFocus());
        });
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
            long lastRecordStart = -1;
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