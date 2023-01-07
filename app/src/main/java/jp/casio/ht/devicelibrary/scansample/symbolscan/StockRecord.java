package jp.casio.ht.devicelibrary.scansample.symbolscan;

class StockRecord {
    private final String barcode;
    private final String name;
    private final int quantity;

    public StockRecord(String line) {
        String[] record = line.split(",");
        this.barcode = record[1].trim();
        this.name = null;
        this.quantity = Integer.parseInt(record[2].trim());
    }


    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {

        return quantity;
    }
}