package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

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

//    public static void main(String[] args) throws IOException {
//
//        String fileName = "";
//
//        Path file = Paths.get(fileName);
//        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
//        FileTime lastModifiedTime = attr.lastModifiedTime();
//
//        // print original last modified time
//        System.out.println("[original] lastModifiedTime:" + lastModifiedTime);
//
//        LocalDate newLocalDate = LocalDate.of(1998, 9, 30);
//        // convert LocalDate to instant, need time zone
//        Instant instant = newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
//
//        // convert instant to filetime
//        // update last modified time
//        Files.setLastModifiedTime(file, FileTime.from(instant));
//
//        // read last modified time again
//        FileTime newLastModifiedTime = Files.readAttributes(file,
//                BasicFileAttributes.class).lastModifiedTime();
//        System.out.println("[updated] lastModifiedTime:" + newLastModifiedTime);
//
//    }



}