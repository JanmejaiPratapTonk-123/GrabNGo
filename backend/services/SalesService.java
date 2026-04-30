package backend.services;

public class SalesService {

    private double totalSales = 0;

    public void addSale(double amount) {
        totalSales += amount;
    }

    public double getTotalSales() {
        return totalSales;
    }
}
