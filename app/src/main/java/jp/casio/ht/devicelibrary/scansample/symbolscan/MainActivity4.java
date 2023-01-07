package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {

    Button mBtnSave, mBtnExit;
    AlertDialog.Builder builder;

    @SuppressLint("StaticFieldLeak")
    private static TextView mTextView1, editAmount, tvNextLine, mTextView2;


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
        mBtnExit.setOnClickListener(v -> {
            builder = new AlertDialog.Builder(MainActivity4.this);
            builder.setTitle("Alert")
                    .setMessage("Are you sure to exit")
                    .setCancelable(true)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent i = new Intent(MainActivity4.this, MainActivity.class);
                        startActivity(i);
                    })
                    .setNegativeButton("No", null);

            builder.create().show();
        });


         Button changeActivitybtnEdit = findViewById(R.id.btnExit);

        changeActivitybtnEdit.setOnClickListener(v -> ChangeActivity());
    }



    private void ChangeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
