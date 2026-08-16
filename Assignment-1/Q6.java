import java.util.Scanner;

public class Q6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first number: ");
        int a = sc.nextInt();

        System.out.print("Enter second number: ");
        int b = sc.nextInt();

        if (a == 0 || b == 0) {
            System.out.println("LCM = 0");
        } else {
            int x = Math.abs(a);
            int y = Math.abs(b);

            int p = x;
            int q = y;

            while (q != 0) {
                int remainder = p % q;
                p = q;
                q = remainder;
            }

            int gcd = p;
            int lcm = Math.abs((x / gcd) * y);

            System.out.println("LCM = " + lcm);
        }

        sc.close();
    }
}
