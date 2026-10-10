package com.gdb;

import com.gdb.domain.IAccount;
import com.gdb.logging.FileLogDestination;
import com.gdb.logging.LogDestination;
import com.gdb.logging.TransactionLogger;
import com.gdb.service.AccountService;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Global Digital Bank — Service Demo");
        System.out.println("=".repeat(60));

        // ============================================================
        // 📝 STEP 20: Wire Up Dependencies
        // ============================================================
        LogDestination dest = new FileLogDestination();
        TransactionLogger logger = new TransactionLogger(dest);
        AccountService service = new AccountService(logger);

        // ============================================================
        // 📝 STEP 21: Run A Demo Workflow
        // ============================================================
        IAccount a1 = service.openAccount("SAVINGS", "Demo User", 30, 20000);
        a1.setPin(1234);
        IAccount a2 = service.openAccount("SAVINGS", "Demo Receiver", 25, 10000);
        service.deposit(a1.getAccountNumber(), 5000);
        service.withdraw(a1.getAccountNumber(), 2000, 1234);
        service.transfer(a1.getAccountNumber(), a2.getAccountNumber(), 1000, 1234);
        System.out.println("Demo Account 1 balance: " + a1.getBalance());
        System.out.println("Demo Account 2 balance: " + a2.getBalance());
        System.out.println("Logged transactions: " + service.getTransactionHistory().size());
    }
}
