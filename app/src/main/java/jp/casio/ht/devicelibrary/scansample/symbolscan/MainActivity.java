package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button changeActivityBTN = findViewById(R.id.btnInv);

        changeActivityBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity();}
        });

    }
    private void ChangeActivity(){
        Intent intent =new Intent (this, MainActivity3.class);
        startActivity(intent);
    }


}