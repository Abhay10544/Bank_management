import java.io.*;
import java.util.*;

public class Bank {
    private static final String FILE_NAME = "accounts.dat";
    private List<Account> accounts;

    public Bank() {
        accounts = loadAccounts();
    }

    public void addAccount(Account acc) {
        accounts.add(acc);
        saveAccounts();
    }

    public Account login(String accNum, String pin) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accNum) && acc.authenticate(pin)) {
                return acc;
            }
        }
        return null;
    }

    private void saveAccounts() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Account> loadAccounts() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Account>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
