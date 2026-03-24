package service;

public class PaymentService {

    public static String process(int choice) {
        if (choice == 1) return "Cash";
        else return "UPI";
    }
}
