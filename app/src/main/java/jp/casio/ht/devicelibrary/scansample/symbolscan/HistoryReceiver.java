package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class HistoryReceiver extends AppCompatActivity {



    @SuppressLint("StaticFieldLeak")
    private static TextView mTextView17;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histpry_receiver);
        mTextView17 = findViewById(R.id.textView17);
        //mTextView5.setText("Initial text\nLine 1\nLine 2");
        //RecyclerView recyclerView;


        Button changeActivitybtnBack = findViewById(R.id.btnBack);

        changeActivitybtnBack.setOnClickListener(v -> ChangeActivity());
        summary();
    }

    private void ChangeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    final String FILENAME = "term001.dat";
    @SuppressLint("ObsoleteSdkInt")
    public void summary() {
//        String fileName = SessionInfo.filePath;
//        File file = new File(fileName);
//        File sdPath = Environment.getExternalStorageDirectory()
//        sdPath = new File(sdPath.getAbsolutePath() + "/Download/");
//        File file = new File(sdPath, FILENAME);
        File file = SessionInfo.getDatFile();
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Map<String, Integer> stock =
                        Files.readAllLines(file.toPath()).
                                stream().
                                map(StockRecord::new).
                                collect(Collectors.groupingBy(StockRecord::getBarcode,
                                        Collectors.summingInt(StockRecord::getQuantity)));

                String lines = stock.entrySet().stream().
                        map(e -> e.getKey() + '\t' + e.getValue()).
                        collect(Collectors.joining("\n"));
                mTextView17.setText(lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}