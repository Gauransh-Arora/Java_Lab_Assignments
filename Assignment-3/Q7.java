import java.util.ArrayList;
import java.util.List;

class ApplicationException extends Exception {
    public ApplicationException(String message) { super(message); }
}

class ProductException extends ApplicationException {
    public ProductException(String message) { super(message); }
}

class ProductNotFoundException extends ProductException {
    public ProductNotFoundException(String message) { super(message); }
}

class OutOfStockException extends ProductException {
    public OutOfStockException(String message) { super(message); }
}

class PaymentException extends ApplicationException {
    public PaymentException(String message) { super(message); }
}

class InvalidPaymentException extends PaymentException {
    public InvalidPaymentException(String message) { super(message); }
}

class InsufficientFundsException extends PaymentException {
    public InsufficientFundsException(String message) { super(message); }
}

class OrderException extends ApplicationException {
    public OrderException(String message) { super(message); }
}

class EmptyCartException extends OrderException {
    public EmptyCartException(String message) { super(message); }
}

class Product {
    String id;
    String name;
    double price;
    int quantity;

    public Product(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}

class ShoppingCart {
    List<Product> items = new ArrayList<>();

    public void addProduct(Product p, int quantityRequested) throws OutOfStockException {
        if (p.quantity < quantityRequested) {
            throw new OutOfStockException("Not enough stock for product: " + p.name + ". Available: " + p.quantity);
        }
        items.add(p);
        System.out.println("Added " + p.name + " to cart.");
    }

    public double calculateTotal() throws EmptyCartException {
        if (items.isEmpty()) {
            throw new EmptyCartException("Cannot calculate total because the cart is empty.");
        }
        double total = 0;
        for (Product item : items) {
            total += item.price;
        }
        return total;
    }
}

public class Q7 {
    private List<Product> catalog = new ArrayList<>();
    private double userBalance = 100.0;

    public Q7() {
        catalog.add(new Product("P1", "Laptop", 1200.0, 5));
        catalog.add(new Product("P2", "Mouse", 25.0, 0));
        catalog.add(new Product("P3", "Keyboard", 50.0, 10));
    }

    public Product searchProduct(String id) throws ProductNotFoundException {
        for (Product p : catalog) {
            if (p.id.equals(id)) {
                return p;
            }
        }
        throw new ProductNotFoundException("Product with ID '" + id + "' not found in catalog.");
    }

    public void processPayment(double amount) throws PaymentException {
        if (amount <= 0) {
            throw new InvalidPaymentException("Payment amount must be greater than zero.");
        }
        if (amount > userBalance) {
            throw new InsufficientFundsException("Insufficient funds. Balance: $" + userBalance + ", Required: $" + amount);
        }
        userBalance -= amount;
        System.out.println("Payment of $" + amount + " successful. New balance: $" + userBalance);
    }

    public static void main(String[] args) {
        Q7 app = new Q7();
        ShoppingCart cart = new ShoppingCart();

        System.out.println("--- Welcome to the Shopping App ---");

        try {
            try {
                System.out.println("\nSearching for P99...");
                app.searchProduct("P99");
            } catch (ProductNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }

            try {
                System.out.println("\nAdding P2 to cart...");
                Product p2 = app.searchProduct("P2");
                cart.addProduct(p2, 1);
            } catch (ProductException e) {
                System.out.println("Error: " + e.getMessage());
            }

            try {
                System.out.println("\nChecking out...");
                double total = cart.calculateTotal();
            } catch (OrderException e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println("\nAdding P1 (Laptop, $1200) to cart...");
            Product p1 = app.searchProduct("P1");
            cart.addProduct(p1, 1);
            
            try {
                double total = cart.calculateTotal();
                System.out.println("Cart Total: $" + total);
                app.processPayment(total);
            } catch (PaymentException e) {
                System.out.println("Payment Error: " + e.getMessage());
            }
            
            System.out.println("\nAdding P3 (Keyboard, $50) to a new cart...");
            ShoppingCart cart2 = new ShoppingCart();
            Product p3 = app.searchProduct("P3");
            cart2.addProduct(p3, 1);
            
            double total2 = cart2.calculateTotal();
            System.out.println("Cart Total: $" + total2);
            app.processPayment(total2);
            
        } catch (ApplicationException e) {
            System.out.println("Application Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
    }
}
