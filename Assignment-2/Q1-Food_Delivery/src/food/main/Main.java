package food.main;

import food.model.FoodOrder;
import food.model.RegularOrder;
import food.model.PremiumOrder;
import food.utility.OrderUtility;

public class Main {

    public static void main(String[] args) {

        FoodOrder.setRestaurantName("Food Express");

        FoodOrder[] orders = new FoodOrder[6];

        orders[0] = new RegularOrder(101, "Rahul", 500);
        orders[1] = new PremiumOrder(102, "Priya", 1000);
        orders[2] = new RegularOrder(103, "Aman", 750);
        orders[3] = new PremiumOrder(104, "Simran", 1200);
        orders[4] = new RegularOrder(105, "Rohan", 650);
        orders[5] = new PremiumOrder(106, "Ananya", 900);

        System.out.println("========================================");
        System.out.println("          " + FoodOrder.getRestaurantName());
        System.out.println("========================================");

        for (FoodOrder order : orders) {

            if (!OrderUtility.validateAmount(order.getAmount())) {
                System.out.println("Invalid amount for Order ID: "
                        + order.getOrderId());
                continue;
            }

            if (!OrderUtility.validateCustomerName(order.getCustomerName())) {
                System.out.println("Invalid customer name for Order ID: "
                        + order.getOrderId());
                continue;
            }

            OrderUtility.generateOrderSummary(order);
        }

        FoodOrder.displayTotalOrders();
    }
}
