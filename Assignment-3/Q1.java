public class Q1 {
    public static void main(String[] args) {
        System.out.println("Demonstrating common exceptions:\n");

        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("Caught Exception: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage() + "\n");
        }

        try {
            String str = null;
            int length = str.length();
        } catch (NullPointerException e) {
            System.out.println("Caught Exception: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage() + "\n");
        }

        try {
            int[] arr = new int[5];
            int value = arr[10];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught Exception: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage() + "\n");
        }

        try {
            int num = Integer.parseInt("NotANumber");
        } catch (NumberFormatException e) {
            System.out.println("Caught Exception: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage() + "\n");
        }
    }
}
