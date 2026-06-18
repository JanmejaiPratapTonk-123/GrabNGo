package backend.models;

public class OrderRecord {

    private final int token;
    private final String dateTime;
    private final String items;
    private final int itemCount;
    private final int total;
    private final String paymentMethod;
    private final String status;

    public OrderRecord(int token, String dateTime, String items, int itemCount, int total, String paymentMethod, String status) {
        this.token = token;
        this.dateTime = dateTime;
        this.items = items;
        this.itemCount = itemCount;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public int getToken()           { return token; }
    public String getDateTime()     { return dateTime; }
    public String getItems()        { return items; }
    public int getItemCount()       { return itemCount; }
    public int getTotal()           { return total; }
    public String getPaymentMethod(){ return paymentMethod; }
    public String getStatus()       { return status; }
}
