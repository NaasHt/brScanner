package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.stream.Collectors;

public class HistoryReceiver extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    private static TextView mTextView5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histpry_receiver);
        mTextView5 = (TextView) findViewById(R.id.textViewSummary);
        mTextView5.setText("Initial text\nLine 1\nLine 2");

        Button changeActivitybtnBack = findViewById(R.id.btnBack);

        changeActivitybtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity();
            }
        });
        summary();
    }

    private void ChangeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void summary() {
        String fileName = SessionInfo.filePath;
        File file = new File(fileName);
//        String lines = "";
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Map<String, Integer> stock =
                        Files.readAllLines(file.toPath()).
                                stream().
                                map(StockRecord::new).
                                collect(Collectors.groupingBy(StockRecord::getBarcodeAndName,
                                        Collectors.summingInt(StockRecord::getQuantity)));

                String lines = stock.entrySet().stream().
                        map(e -> e.getKey() + ' ' + e.getValue()).
                        collect(Collectors.joining("\n"));
//                mTextView5.setText(lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Runnable runnable=new Runnable() {
//            @Override
//            public void run() {
//
//                mTextView5.post(new Runnable() {
//                    public void run() {
//        mTextView5.setText(lines);
//                    }
//                });
//            }
//        };
    }

    private static class StockRecord {
        private final String barcode;
        private final String name;
        private final int quantity;

        public StockRecord(String line) {
            String[] record = line.split("\\s{2,40}");
            this.barcode = record[0];
            this.name = record[1];
            this.quantity = Integer.parseInt(record[2]);
        }

        public String getBarcode() {
            return barcode;
        }

        public String getBarcodeAndName() {
            return barcode + " " + name;
        }

        public int getQuantity() {
            return quantity;
        }
    }


}


//
////    public void class ReadFile {
////        TextView tvNextLine = findViewById(R.id.textView8);
////        BufferedReader FileRead;
////     try
////        {
////            final InputStream file = getResources().openRawResource(R.raw.likuciai_ex);
////            FileRead = new BufferedReader(new InputStreamReader(file));
////
////            String line = FileRead.readLine();
////
////            while (line != null) {
////                line = FileRead.readLine();
////                if (line.contains(barcode)) {
////                    String[] words = line.split("\\s{2,40}");
////                    tvNextLine.setText(words[1]);
////                    break;
////                }
////            }
////        } catch(IOException ioe)
////        {
////            ioe.printStackTrace();
////        }
////    }