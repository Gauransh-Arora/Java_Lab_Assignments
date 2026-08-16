package food.model;

public abstract class FoodOrder{
	private int orderId;
	private String customerName;
	private double amount;

	private static int orderCount = 0;
	private static String restaurantName = "Food Express";

	public FoodOrder(int orderId, String customerName, double amount){
		this.orderId = orderId;
		this.customerName = customerName;
		this.amount = amount;
		orderCount++;
	}

	public int getOrderId(){
		return orderId;
	}

	public void setOrder(int orderId){
		this.orderId = orderId;
	}

	public String getCustomerName(){
		return customerName;
	}

	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}

	public double getAmount() {
        	return amount;
	}

    	public void setAmount(double amount) {
        	this.amount = amount;
    	}

    	public static String getRestaurantName() {
        	return restaurantName;
    	}

    	public static void setRestaurantName(String restaurantName) {
        	FoodOrder.restaurantName = restaurantName;
    	}

    	public abstract double calculateDeliveryCharge();

    	public static void displayTotalOrders() {
        	System.out.println("Total Orders Created: " + orderCount);
    	}
}
