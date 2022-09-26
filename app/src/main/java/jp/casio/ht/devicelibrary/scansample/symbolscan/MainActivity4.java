package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity4 extends AppCompatActivity {

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




}