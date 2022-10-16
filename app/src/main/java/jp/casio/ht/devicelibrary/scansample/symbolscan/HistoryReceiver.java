package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HistoryReceiver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histpry_receiver);

        Button changeActivitybtnBack = findViewById(R.id.btnBack);

        changeActivitybtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity();
            }
        });
    }

    private void ChangeActivity(){
        Intent intent =new Intent (this, MainActivity.class);
        startActivity(intent);
    }


}