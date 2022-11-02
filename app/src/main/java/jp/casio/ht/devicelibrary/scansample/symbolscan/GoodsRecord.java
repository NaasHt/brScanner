package jp.casio.ht.devicelibrary.scansample.symbolscan;

public class GoodsRecord {

    private final String barcode;
    private final String name;
    private final String price;

    public GoodsRecord(String line) {
        String[] words = line.split("\\s{2,40}");
        barcode = words[0];
        name = words[1];
        price = words[2];
    }

    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
