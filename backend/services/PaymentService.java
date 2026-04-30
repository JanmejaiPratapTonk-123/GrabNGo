package backend.services;

public class PaymentService {

    public void validateMethod(String method) {
        if (!("Cash".equals(method) || "UPI".equals(method))) {
            throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
    }

    public String processPayment(String method, double amount) {
        validateMethod(method);
        return "Payment of ₹" + (int) amount + " via " + method + " successful.";
    }
}
