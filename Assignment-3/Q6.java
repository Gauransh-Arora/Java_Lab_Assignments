import java.util.Scanner;

class InvalidUsernameException extends Exception {
    public InvalidUsernameException(String message) { super(message); }
}

class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) { super(message); }
}

class AccountLockedException extends Exception {
    public AccountLockedException(String message) { super(message); }
}

public class Q6 {
    private static final String CORRECT_USERNAME = "admin";
    private static final String CORRECT_PASSWORD = "password123";
    private static final int MAX_ATTEMPTS = 3;
    
    private int failedAttempts = 0;
    private boolean isLocked = false;

    public void login(String username, String password) throws InvalidUsernameException, InvalidPasswordException, AccountLockedException {
        if (isLocked) {
            throw new AccountLockedException("Account is locked due to too many failed attempts.");
        }

        if (!CORRECT_USERNAME.equals(username)) {
            failedAttempts++;
            checkLockStatus();
            throw new InvalidUsernameException("Invalid username.");
        }

        if (!CORRECT_PASSWORD.equals(password)) {
            failedAttempts++;
            checkLockStatus();
            throw new InvalidPasswordException("Invalid password.");
        }

        failedAttempts = 0;
        System.out.println("Login successful! Welcome, " + username + ".");
    }

    private void checkLockStatus() throws AccountLockedException {
        if (failedAttempts >= MAX_ATTEMPTS) {
            isLocked = true;
            throw new AccountLockedException("Maximum login attempts exceeded. Account is now locked.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Q6 system = new Q6();

        System.out.println("--- Login System ---");

        while (true) {
            try {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                system.login(username, password);
                break;

            } catch (InvalidUsernameException | InvalidPasswordException e) {
                System.out.println("Login Failed: " + e.getMessage());
                System.out.println("Attempts remaining: " + (MAX_ATTEMPTS - system.failedAttempts));
            } catch (AccountLockedException e) {
                System.out.println("SECURITY ALERT: " + e.getMessage());
                break;
            } finally {
                System.out.println("--------------------");
            }
        }
        scanner.close();
    }
}
