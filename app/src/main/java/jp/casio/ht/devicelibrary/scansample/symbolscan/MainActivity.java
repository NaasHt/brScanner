package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button changeActivityBTN = findViewById(R.id.btnInv);
        Button changeActivityBTNSum = findViewById(R.id.btnSum);
        Button changeActivityBTNEdit = findViewById(R.id.btnEdit);
        Button changeActivitybtnLogout = findViewById(R.id.btnLogout);
        Button btn_fileopen = findViewById(R.id.btnLogf);



        changeActivityBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity();
            }
        });

        changeActivityBTNSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity2();

            }
        });

        changeActivityBTNEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity3();
            }
        });

        changeActivitybtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity4();
            }
        });




    }

    private void ChangeActivity(){
        Intent intent =new Intent (this, MainActivity4.class);
        startActivity(intent);

     }

    private void ChangeActivity2(){

        Intent intent = new Intent(this, HistoryReceiver.class);
        startActivity(intent);
    }
    private void ChangeActivity3(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
    private void ChangeActivity4(){
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }



}