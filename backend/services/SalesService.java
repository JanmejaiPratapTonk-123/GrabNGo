package backend.services;

/**
 * Tracks cumulative sales revenue.
 */
public class SalesService {

    private double totalSales = 0;

    /**
     * Records a sale amount.
     */
    public void addSale(double amount) {
        totalSales += amount;
    }

    /**
     * Returns total sales revenue so far.
     */
    public double getTotalSales() {
        return totalSales;
    }
}
