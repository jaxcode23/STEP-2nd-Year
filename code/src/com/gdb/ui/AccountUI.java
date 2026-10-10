package com.gdb.ui;

import com.gdb.domain.IAccount;
import com.gdb.domain.Transaction;
import com.gdb.command.TransactionCommand;
import com.gdb.service.AccountService;

import java.util.*;

public class AccountUI {
    // ============================================================
    // 📝 STEP 1: Declare Fields
    // ============================================================
    private final AccountService service;
    private final Scanner scanner;

    // ============================================================
    // 📝 STEP 2: Constructor
    // ============================================================
    public AccountUI(AccountService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    // ============================================================
    // 📝 STEP 3: Implement start() (Main Menu Loop)
    // ============================================================
    public void start() {
        while (true) {
            displayMainMenu();
            int choice = readInt("Enter your choice: ");
            try {
                switch (choice) {
                    case 1: handleOpenAccount(); break;
                    case 2: handleDeposit(); break;
                    case 3: handleWithdraw(); break;
                    case 4: handleTransfer(); break;
                    case 5: handleCloseAccount(); break;
                    case 6: handleViewAccount(); break;
                    case 7: handleViewTransactions(); break;
                    case 8: System.out.println("Thank you! Goodbye."); return;
                    default: System.out.println("Invalid choice. Please enter 1-8.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    // ============================================================
    // 📝 STEP 4: Implement displayMainMenu()
    // ============================================================
    private void displayMainMenu() {
        System.out.println("\n========================================");
        System.out.println("   GLOBAL DIGITAL BANK");
        System.out.println("========================================");
        System.out.println("1. Open Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Close Account");
        System.out.println("6. View Account Details");
        System.out.println("7. View Transaction History");
        System.out.println("8. Exit");
        System.out.println("========================================");
    }

    // ============================================================
    // 📝 STEP 5: Implement handleOpenAccount()
    // ============================================================
    private void handleOpenAccount() throws Exception {
        System.out.println("\n--- Open Account ---");
        String type = readString("Account Type (Savings/Current/FixedDeposit/Salary): ");
        String name = readString("Name: ");
        int age = readInt("Age: ");
        double amount = readDouble("Initial Balance: ");

        IAccount acc = service.openAccount(type, name, age, amount);
        System.out.println("SUCCESS: " + acc.getAccountInfo());

        int pin = readInt("Set 4-digit PIN: ");
        acc.setPin(pin);
        System.out.println("PIN set successfully.");
    }

    // ============================================================
    // 📝 STEP 6: Implement handleDeposit()
    // ============================================================
    private void handleDeposit() throws Exception {
        System.out.println("\n--- Deposit ---");
        int accNo = readInt("Account Number: ");
        double amount = readDouble("Amount to deposit: ");
        Transaction txn = service.deposit(accNo, amount);
        System.out.println("SUCCESS: Deposited Rs. " + amount + " to #" + accNo + ". New balance: Rs. " + txn.getBalanceAfter());
    }

    // ============================================================
    // 📝 STEP 7: Implement handleWithdraw()
    // ============================================================
    private void handleWithdraw() throws Exception {
        System.out.println("\n--- Withdraw ---");
        int accNo = readInt("Account Number: ");
        double amount = readDouble("Amount to withdraw: ");
        int pin = readInt("PIN: ");
        Transaction txn = service.withdraw(accNo, amount, pin);
        System.out.println("SUCCESS: Withdrew Rs. " + amount + " from #" + accNo + ". New balance: Rs. " + txn.getBalanceAfter());
    }

    // ============================================================
    // 📝 STEP 8: Implement handleTransfer()
    // ============================================================
    private void handleTransfer() throws Exception {
        System.out.println("\n--- Transfer ---");
        int fromAcc = readInt("From Account: ");
        int toAcc = readInt("To Account: ");
        double amount = readDouble("Amount: ");
        int pin = readInt("PIN: ");
        service.transfer(fromAcc, toAcc, amount, pin);
        System.out.println("SUCCESS: Transferred Rs. " + amount + " from #" + fromAcc + " to #" + toAcc);
    }

    // ============================================================
    // 📝 STEP 9: Implement handleCloseAccount()
    // ============================================================
    private void handleCloseAccount() throws Exception {
        System.out.println("\n--- Close Account ---");
        int accNo = readInt("Account Number: ");
        int pin = readInt("PIN: ");
        service.closeAccount(accNo, pin);
        System.out.println("SUCCESS: Account #" + accNo + " has been closed.");
    }

    // ============================================================
    // 📝 STEP 10: Implement handleViewAccount()
    // ============================================================
    private void handleViewAccount() {
        System.out.println("\n--- View Account Details ---");
        int accNo = readInt("Account Number: ");
        IAccount acc = service.getAccount(accNo);
        if (acc == null) {
            System.out.println("ERROR: Account not found: " + accNo);
        } else {
            System.out.println(acc.getAccountInfo());
        }
    }

    // ============================================================
    // 📝 STEP 11: Implement handleViewTransactions()
    // ============================================================
    private void handleViewTransactions() {
        System.out.println("\n--- Transaction History ---");
        List<TransactionCommand> history = service.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions logged.");
        } else {
            for (int i = 0; i < history.size(); i++) {
                System.out.println("  [" + (i + 1) + "] " + history.get(i).getTransaction());
            }
        }
    }

    // ============================================================
    // 📝 STEP 12: Implement readInt(String prompt)
    // ============================================================
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer number. Please try again.");
            }
        }
    }

    // ============================================================
    // 📝 STEP 13: Implement readDouble(String prompt)
    // ============================================================
    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid decimal number. Please try again.");
            }
        }
    }

    // ============================================================
    // 📝 STEP 14: Implement readString(String prompt)
    // ============================================================
    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
