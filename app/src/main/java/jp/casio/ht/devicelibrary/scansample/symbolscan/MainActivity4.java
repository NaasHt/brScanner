package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class MainActivity4 extends AppCompatActivity {

    //private  ActivityM
    //EditText mEditTxtAmount;
    Button mBtnSave;
    String mText, mText1;
    static final int READ_BLOCK_SIZE = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        EditText editTxtAmnt = (findViewById(R.id.editTxtAmount));
        Button btnSave = (findViewById(R.id.btnSave));
//
//        try {
//            final File file = new File(Environment.getExternalStorageDirectory()
////                    .getAbsolutePath(), filename);
////            FileInputStream fileInputStream = openFileInput();
//            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            StringBuffer stringBuffer = new StringBuffer();
//            String star;
//            while ((star=bufferedReader.readLine()) != null){
//                stringBuffer.append(star);
//            }
//            editTxtAmnt.setText(stringBuffer.toString());
//        }
//        catch (FileNotFoundException e){
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        File dir = new File("/Downloads/");
//
//        File[] matches = dir.listFiles(new FilenameFilter()
//            {
//                public boolean accept(File dir, String name)
//                {
//                    return name.startsWith("MyFile") && name.endsWith(".txt");
//                }
////                if(File){
////                Toast.makeText(MainActivity4.this, "Saved in file", Toast.LENGTH_SHORT).show();
//
//
//            });
//
//        BufferedReader reader;
//
//        try {
//            File path = Environment.getExternalStorageDirectory();
//            File dir = new File(path + "/Downloads/");
////                File root = null; //directory/
////                File file = null;
////                for(File fileInDirectory : root.listFiles()) {
////                    file = fileInDirectory;
////                }
//            reader = new BufferedReader(new InputStreamReader(file));
//            String line = null;
//
//            while ((line = reader.readLine()) != null) {
//                if (line.contains(barcode.trim())) {
//                    String[] words = line.split("\\s{2,40}");
//                    tvNextLine.setText(words[1]);
//                    mTextView2.setText(words[2]);
//                    break;
//                }
//            }
//
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
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