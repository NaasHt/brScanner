package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

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
    private void init(){


    }


//    private static boolean isExternalStorageReadOnly() {
//        String extStorageState = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
//            return true;
//        }
//        return false;
//    }
//
//
//    private static boolean isExternalStorageAvailable() {
//        String extStorageState = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
//            return true;
//        }
//        return false;
//    }





}