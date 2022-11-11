package jp.casio.ht.devicelibrary.scansample.symbolscan;

import android.os.Environment;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionInfo {

    public static String userName = "Inkl"
            ;
    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        SessionInfo.userName = userName;
    }

    public static String filePath;
    public static File getDatFile() {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String fileName = "term001"+".dat";
        return new File(path, fileName);
    }

    private static Map<String, GoodsRecord> goods = null;
    private static Map<String, List<StockRecord>> stock = null;

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


    public static int getStockTotalAmount(String barcode) {
        List<StockRecord> stockList = getStock().get(barcode);
        if(stockList == null || stockList.isEmpty()) {
            return 0;
        } else {
            return stockList.stream().mapToInt(StockRecord::getQuantity).sum();
        }
    }
    public static void append(StockRecord stockRecord) {
        getStock().computeIfAbsent(stockRecord.getBarcode(), (barcode) -> new ArrayList<>()).add(stockRecord);
    }

    private static Map<String, List<StockRecord>> getStock() {
        if(stock == null) {
            stock = initStock();
        }
        return stock;
    }

    private static Map<String, List<StockRecord>> initStock() {
        Map<String, List<StockRecord>> stock = new HashMap<>();
        File sdPath = getDatFile();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(sdPath));
            String line = null;
            //try {
            while ((line = reader.readLine()) != null) {
                try {
                    if(!line.isEmpty()) {
                        StockRecord stockRecord = new StockRecord(line);
                        stock.computeIfAbsent(stockRecord.getBarcode(), (barcode) -> new ArrayList<>()).add(stockRecord);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return stock;
    }
}
