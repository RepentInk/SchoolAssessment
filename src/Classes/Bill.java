package Classes;

public class Bill {
    
    private int id;
    private double amount;
    private String billItem;

    public Bill() {
    }

    public Bill(double amount, String billItem) {
        this.amount = amount;
        this.billItem = billItem;
    }

    public Bill(int id, double amount, String billItem) {
        this.id = id;
        this.amount = amount;
        this.billItem = billItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBillItem() {
        return billItem;
    }

    public void setBillItem(String billItem) {
        this.billItem = billItem;
    }
    
}
