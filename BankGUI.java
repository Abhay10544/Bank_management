import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankGUI {
    private Bank bank;
    private JFrame frame;
    private Account currentAccount;

    public BankGUI() {
        bank = new Bank();
        frame = new JFrame("Basic Banking System");
        showWelcomeScreen();
    }

    private void showWelcomeScreen() {
        frame.getContentPane().removeAll();

        JButton createBtn = new JButton("Create Account");
        JButton loginBtn = new JButton("Login");

        createBtn.addActionListener(e -> showCreateAccount());
        loginBtn.addActionListener(e -> showLogin());

        frame.setLayout(new GridLayout(2, 1));
        frame.add(createBtn);
        frame.add(loginBtn);

        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void showCreateAccount() {
        JTextField nameField = new JTextField();
        JTextField accField = new JTextField();
        JTextField pinField = new JPasswordField();
        JTextField initBalField = new JTextField();

        Object[] fields = {
            "Name:", nameField,
            "Account Number:", accField,
            "PIN:", pinField,
            "Initial Balance:", initBalField
        };

        int option = JOptionPane.showConfirmDialog(frame, fields, "Create Account", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String accNum = accField.getText();
            String pin = pinField.getText();
            double balance = Double.parseDouble(initBalField.getText());

            bank.addAccount(new Account(name, accNum, pin, balance));
            JOptionPane.showMessageDialog(frame, "Account Created!");
        }
    }

    private void showLogin() {
        JTextField accField = new JTextField();
        JTextField pinField = new JPasswordField();

        Object[] fields = {
            "Account Number:", accField,
            "PIN:", pinField
        };

        int option = JOptionPane.showConfirmDialog(frame, fields, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String accNum = accField.getText();
            String pin = pinField.getText();

            currentAccount = bank.login(accNum, pin);
            if (currentAccount != null) {
                showDashboard();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials!");
            }
        }
    }

    private void showDashboard() {
        frame.getContentPane().removeAll();

        JLabel nameLabel = new JLabel("Welcome, " + currentAccount.getName());
        JLabel balanceLabel = new JLabel("Balance: $" + currentAccount.getBalance());

        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton logoutBtn = new JButton("Logout");

        depositBtn.addActionListener(e -> {
            String amountStr = JOptionPane.showInputDialog("Amount to deposit:");
            if (amountStr != null) {
                double amount = Double.parseDouble(amountStr);
                currentAccount.deposit(amount);
                bank.addAccount(currentAccount); // Save
                balanceLabel.setText("Balance: $" + currentAccount.getBalance());
            }
        });

        withdrawBtn.addActionListener(e -> {
            String amountStr = JOptionPane.showInputDialog("Amount to withdraw:");
            if (amountStr != null) {
                double amount = Double.parseDouble(amountStr);
                if (currentAccount.withdraw(amount)) {
                    bank.addAccount(currentAccount); // Save
                    balanceLabel.setText("Balance: $" + currentAccount.getBalance());
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient balance!");
                }
            }
        });

        logoutBtn.addActionListener(e -> {
            currentAccount = null;
            showWelcomeScreen();
        });

        frame.setLayout(new GridLayout(5, 1));
        frame.add(nameLabel);
        frame.add(balanceLabel);
        frame.add(depositBtn);
        frame.add(withdrawBtn);
        frame.add(logoutBtn);

        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        new BankGUI();
    }
}
