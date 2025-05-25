import java.io.Serializable;

public class Account implements Serializable {
    private String name;
    private String accountNumber;
    private String pin;
    private double balance;

    public Account(String name, String accountNumber, String pin, double initialBalance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = initialBalance;
    }

    public boolean authenticate(String pin) {
        return this.pin.equals(pin);
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) return false;
        this.balance -= amount;
        return true;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }
}
