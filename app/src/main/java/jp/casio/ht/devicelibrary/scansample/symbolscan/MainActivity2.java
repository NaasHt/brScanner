package jp.casio.ht.devicelibrary.scansample.symbolscan;

import static java.lang.System.out;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import jp.casio.ht.devicelibrary.ScannerLibrary;

public class MainActivity2 extends AppCompatActivity {

    //EditText mEditTxtAmount;
    Button mBtnSave;
    String mText, mText1;
    static final int READ_BLOCK_SIZE = 100;

    @SuppressLint("StaticFieldLeak")
    private static TextView mTextView1, editTxtAmount, tvNextLine, mTextView2;;
    private static ScannerLibrary mScanLib;
    private static ScannerLibrary.ScanResult mScanResult;
    private static ScanResultReceiver mScanResultReceiver;
    private Bundle savedInstanceState;
    private static final int WRITE_EXTERNAL_STORAGE_CODE =1;



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
        editTxtAmount = (EditText) findViewById(R.id.editTxtAmount);
        mBtnSave = findViewById(R.id.btnSave);


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
        Button saveData = findViewById(R.id.btnSave);


        changeActivityExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity();

            }
        });




        mBtnSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {

                mText1 = mTextView1.getText().toString().trim();
                mText = editTxtAmount.getText().toString().trim();

                if (mText.isEmpty()) {
                    Toast.makeText(MainActivity2.this, "Please enter anything", Toast.LENGTH_SHORT).show();
                    if (mText1.isEmpty()) {
                        Toast.makeText(MainActivity2.this, "Please enter anything", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {

                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED) {
                            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permissions, WRITE_EXTERNAL_STORAGE_CODE);

//                        } else {
//                            saveToTxtFile(mText1,mText);
//                        }
                    } else {
                        saveToTxtFile(mText1, mText);
                    }
                }
            }


        });



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveToTxtFile(mText1, mText);
            } else {
                Toast.makeText(this, "Storage permission is required to store data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveToTxtFile(String mText1, String mText) {


        String timeStamp = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(System.currentTimeMillis());
        String timeStamp1 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis());
        String contestsToAppend = (mText1 + "   " + mText + "  " + timeStamp1 + "\n");
        try {
            InputStream input = new FileInputStream("/My File/");
            File path = Environment.getExternalStorageDirectory();
            File dir = new File(path + "/My File/");
            String fileName = "MyFile" + timeStamp + ".dat";
            File file = new File(dir, fileName);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            {
                bw.write(contestsToAppend);
                bw.close();

//                FileOutputStream output = new FileOutputStream(file);
////                input.write(contestsToAppend);
            }
////            openOutput().close();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void saveToTxtFile(String mText1, String mText){
//        String timeStamp = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(System.currentTimeMillis());
//        String timeStamp1 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis());
//        String contestsToAppend = (mText1 + "   " + mText + "  " + timeStamp1 + "\n");
//        try {
//            File path = Environment.getExternalStorageDirectory();
//            File dir = new File(path + "/My File/");
//            String fileName = "MyFile" + timeStamp + ".dat";
//            File file = new File(dir, fileName);
//            FileOutputStream fOut = new FileOutputStream(file.getAbsoluteFile(),true);
//            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
//            myOutWriter.append(contestsToAppend);
//            myOutWriter.close();
//            fOut.close();
//        } catch (FileNotFoundException fileNotFoundException) {
//            fileNotFoundException.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }


        private void ChangeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
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
//            String barcode="";
            if (mScanLib != null) {
                //3. Read barcode
                getmScanLib().getScanResult(mScanResult);
                if (mScanResult.length > 0) {
                    mTextView1.setText(new String(mScanResult.value));
//                    barcode= new String(mScanResult.value);
                } else {
                    mTextView1.setText("");
                }
            }


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
//
//                    if(line.contains(""))
//                    {
//                        String[] words = line.split("\\s{2,40}");
//                        tvNextLine.setText(words[1]);
//                        mTextView2.setText(words[2]);
//                        break;
//                    }
//                }
//            } catch(IOException ioe){
//                ioe.printStackTrace();
//            }
//        }
    }
//
//    public static void main(String args[]) {
//        try {
//            FileReader fr = new FileReader("likuciai_ex.txt");   //reads the file
//            BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream....change
//            StringBuffer sb = new StringBuffer();    //constructs a string buffer with no characters
//            String line;
//            while ((line = br.readLine()) != null) {
//                sb.append(line);      //appends line to string buffer
//                sb.append("/n");     //line feed
//            }
//            fr.close();    //closes the stream and release the resources
//            out.println("Contents of File: ");
//            out.println(sb.toString());   //returns a string that textually represents the object
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



    }



        //File save

//    public void WriteBtn(View v) {
//        // add-write text into file
//        try {
//            FileOutputStream fileout=openFileOutput("mytextfile.txt", MODE_PRIVATE);
//            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
//            outputWriter.write(textmsg.getText().toString());
//            outputWriter.close();
//            //display file saved message
//            Toast.makeText(getBaseContext(), "File saved successfully!",
//                    Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }




    //write to fail(tam pravda fail ne sozdajotsa, tak chto dorabotaju




//
//    public void WriteBtn(Context context, String sFileName, String sBody) {
//
//        try {
//            File root = new File(Environment.getExternalStorageDirectory(), "Note");
//            if (!root.exists()) {
//                root.mkdirs();
//            }
//            FileOutputStream fileout=openFileOutput("/Download/", MODE_PRIVATE);
//            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
//            outputWriter.write(editTxtAmount.getText().toString());
//            outputWriter.close();
//            File gpxfile = new File(root, sFileName);
//            FileWriter writer = new FileWriter(gpxfile);
//            writer.append(sBody);
//            writer.flush();
//            writer.close();
//            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


//
//    public void write(View view) {
//        File term002 = new File("C:\\Users\\Liza\\Downloads");
//        String myTxt = mTextView1.getText().toString();
//        try {
//            FileOutputStream fileOutput = openFileOutput("term002.txt", MODE_PRIVATE);
//            fileOutput.write(myTxt.getBytes());
//            fileOutput.close();
//            mTextView1.setText("");
//            Toast.makeText(MainActivity2.this, "TEXT SAVED",Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    public void read(View view){
//        try{
//            FileInputStream fileInput = openFileInput("term002.txt");
//            InputStreamReader reader = new InputStreamReader(fileInput);
//            BufferedReader buffer = new BufferedReader(reader);
//            StringBuffer strBuffer = new StringBuffer();
//            String lines;
//            while((lines = buffer.readLine())!= null){
//                strBuffer.append(lines).append("\n");
//            }
//            txtShow.setText(strBuffer.toString());
//            fileInput.close();
//        }catch(FileNotFoundException e){e.printStackTrace();
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }

//
//   public static void main(String[] args) throws IOException {
//        String fileSeparator = System.getProperty("file.separator");
//
//   //absolute file name with path
//        String absoluteFilePath = fileSeparator+"Users"+fileSeparator+"osipo"+fileSeparator+"Downloads"+fileSeparator+"term002.txt";
//        File file = new File(absoluteFilePath);
//            if(file.createNewFile()){
//            System.out.println(absoluteFilePath+" File Created");
//        }else System.out.println("File "+absoluteFilePath+" already exists");
//
//
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












