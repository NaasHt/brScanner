package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.os.Environment;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SessionInfo {
//    public static String filePath;
    public static File getDatFile() {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String fileName = "term001"+".dat";
        return new File(path, fileName);
    }

    private static Map<String, GoodsRecord> goods = null;

    public static void installGoods() {
        goods = initGoods();
    }

    public static GoodsRecord getGoods(String barcode) {
        if(goods == null || goods.isEmpty()) {
            goods = initGoods();
        }
        return goods.get(barcode);
    }

    private static Map<String, GoodsRecord> initGoods() {
        Map<String, GoodsRecord> goods = new HashMap<>();
        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + "/Download/");
        File sdFile = new File(sdPath, "likuciai_ex.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(sdFile));
            String line = null;
            //try {
            while ((line = reader.readLine()) != null) {
                try {
                    if(!line.isEmpty()) {
                        GoodsRecord goodsRecord = new GoodsRecord(line);
                        goods.put(goodsRecord.getBarcode(), goodsRecord);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return goods;
    }
}
