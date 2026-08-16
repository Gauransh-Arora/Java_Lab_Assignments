import java.util.Scanner;

public class Q8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = sc.nextInt();

        int n = Math.abs(number);
        int reverse = 0;

        while (n != 0) {
            int digit = n % 10;
            reverse = reverse * 10 + digit;
            n /= 10;
        }

        if (number < 0) {
            reverse = -reverse;
        }

        System.out.println("Reversed number = " + reverse);

        sc.close();
    }
}
