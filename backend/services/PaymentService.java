package backend.services;

/**
 * Validates and processes payments.
 */
public class PaymentService {

    public void validateMethod(String method) {
        if (!("Cash".equals(method) || "UPI".equals(method))) {
            throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
    }

    /**
     * Processes a payment and returns a confirmation message.
     *
     * @param method the payment method ("Cash" or "UPI")
     * @param amount the amount to charge
     * @return confirmation string
     * @throws IllegalArgumentException if the method is unsupported
     */
    public String processPayment(String method, double amount) {
        validateMethod(method);
        return "Payment of ₹" + (int) amount + " via " + method + " successful.";
    }
}
