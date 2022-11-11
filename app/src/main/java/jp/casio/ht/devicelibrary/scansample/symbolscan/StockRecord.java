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
//        String[] record = line.split("\\s{2,40}+(,)");
//        if (record.length > 3) { //with name
//            this.barcode = record[0];
//            this.name = record[1];
//            this.quantity = Integer.parseInt(record[2]);
//        } else {
//            this.barcode = record[0];
//            this.name = null;
//            this.quantity = Integer.parseInt(record[1]);
//        }
    }


    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public String getBarcodeAndName() {
        return barcode + " " + name;
    }

    public int getQuantity() {
        return quantity;
    }
}