package service;

public class SalesService {
    private double totalSales = 0;

    public void addSale(double amount) {
        totalSales += amount;
    }

    public void showSales() {
        System.out.println("Total Sales: ₹" + totalSales);
    }
}
