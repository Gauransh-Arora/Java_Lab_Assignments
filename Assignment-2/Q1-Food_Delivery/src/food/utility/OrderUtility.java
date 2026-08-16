package food.utility;

import food.model.FoodOrder;
import food.service.Discountable;

public class OrderUtility {

    public static boolean validateAmount(double amount) {
        return amount > 0;
    }

    public static boolean validateCustomerName(String customerName) {
        return customerName != null && !customerName.trim().isEmpty();
    }

    public static void generateOrderSummary(FoodOrder order) {
        double discount = ((Discountable) order).applyDiscount();
        double deliveryCharge = order.calculateDeliveryCharge();
        double finalAmount = order.getAmount() - discount + deliveryCharge;

        System.out.println("----------------------------------------");
        System.out.println("Order ID       : " + order.getOrderId());
        System.out.println("Customer Name  : " + order.getCustomerName());
        System.out.println("Order Amount   : Rs. " + order.getAmount());
        System.out.println("Discount       : Rs. " + discount);
        System.out.println("Delivery Charge: Rs. " + deliveryCharge);
        System.out.println("Final Payable  : Rs. " + finalAmount);
        System.out.println("----------------------------------------");
    }
}
