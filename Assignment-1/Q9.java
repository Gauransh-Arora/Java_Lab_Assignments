import java.util.Scanner;

public class Q9 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the base: ");
        double base = sc.nextDouble();

        System.out.print("Enter the exponent: ");
        int exponent = sc.nextInt();

        double result = 1;

        if (exponent >= 0) {
            for (int i = 1; i <= exponent; i++) {
                result *= base;
            }
        } else {
            for (int i = 1; i <= -exponent; i++) {
                result *= base;
            }

            result = 1 / result;
        }

        System.out.println(base + " raised to the power " + exponent + " = " + result);

        sc.close();
    }
}
