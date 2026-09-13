class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) { super(message); }
}

class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) { super(message); }
}

class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String message) { super(message); }
}

class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }

    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        }
        balance += amount;
        System.out.println("Deposited: " + amount + ". New Balance: " + balance);
    }

    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal. Current balance: " + balance);
        }
        balance -= amount;
        System.out.println("Withdrew: " + amount + ". New Balance: " + balance);
    }
}

public class Q5 {
    
    public static void performTransaction(BankAccount account, String type, double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        if (type.equals("DEPOSIT")) {
            account.deposit(amount);
        } else if (type.equals("WITHDRAW")) {
            account.withdraw(amount);
        }
    }

    public static BankAccount findAccount(String accountNumber, BankAccount[] accounts) throws AccountNotFoundException {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        throw new AccountNotFoundException("Account with number " + accountNumber + " not found.");
    }

    public static void main(String[] args) {
        BankAccount[] accounts = {
            new BankAccount("1001", 5000),
            new BankAccount("1002", 1500)
        };

        System.out.println("--- Banking Application ---");
        
        try {
            BankAccount acc1 = findAccount("1001", accounts);
            performTransaction(acc1, "DEPOSIT", 1000);
            
            try {
                performTransaction(acc1, "DEPOSIT", -500);
            } catch (InvalidAmountException e) {
                System.out.println("Transaction Failed: " + e.getMessage());
            }
            
            performTransaction(acc1, "WITHDRAW", 2000);
            
            try {
                performTransaction(acc1, "WITHDRAW", 10000);
            } catch (InsufficientBalanceException e) {
                System.out.println("Transaction Failed: " + e.getMessage());
            }

            BankAccount invalidAcc = findAccount("9999", accounts);
            
        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
    }
}
