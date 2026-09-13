public class Q2 {
    public static void main(String[] args) {
        try {
            System.out.println("Outer try block started.");
            
            try {
                System.out.println("Inner try block started.");
                int result = 10 / 0;
            } catch (ArithmeticException e) {
                System.out.println("Inner Catch: Caught " + e.getClass().getSimpleName() + " - " + e.getMessage());
            }
            
            String str = null;
            str.length();

            System.out.println("Outer try block ended.");
        } catch (NullPointerException e) {
            System.out.println("Outer Catch: Caught " + e.getClass().getSimpleName() + " - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Outer Catch: Caught Exception - " + e.getMessage());
        }
    }
}
