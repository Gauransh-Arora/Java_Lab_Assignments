import java.util.Scanner;

public class Q10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = sc.nextInt();

        int original = number;
        int n = Math.abs(number);
        int reverse = 0;

        while (n != 0) {
            int digit = n % 10;
            reverse = reverse * 10 + digit;
            n /= 10;
        }

        if (number >= 0 && original == reverse) {
            System.out.println(number + " is a Palindrome.");
        } else {
            System.out.println(number + " is not a Palindrome.");
        }

        sc.close();
    }
}
