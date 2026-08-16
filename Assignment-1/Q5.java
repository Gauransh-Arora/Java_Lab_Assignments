import java.util.Scanner;

public class Q5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first number: ");
        int a = sc.nextInt();

        System.out.print("Enter second number: ");
        int b = sc.nextInt();

        int x = Math.abs(a);
        int y = Math.abs(b);

        while (y != 0) {
            int remainder = x % y;
            x = y;
            y = remainder;
        }

        System.out.println("GCD = " + x);

        sc.close();
    }
}
