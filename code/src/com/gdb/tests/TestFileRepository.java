package com.gdb.tests;

import com.gdb.domain.*;
import com.gdb.repository.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class TestFileRepository {
    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("  ACTIVITY 26 — FILE REPOSITORY (CSV + ATOMIC WRITES + BACKUP)");
        System.out.println("=".repeat(60));

        String accPath = "test_data/accounts.csv";
        String txnPath = "test_data/transactions.csv";

        File testDir = new File("test_data");
        if (testDir.exists()) {
            File[] files = testDir.listFiles();
            if (files != null) {
                for (File f : files) f.delete();
            }
        } else {
            testDir.mkdirs();
        }

        // ============================================================
        // 📝 TEST 1: Account Save & Read Back from CSV
        // ============================================================
        System.out.println("[TEST 1] Account Save & Read Back from CSV:");
        FileAccountRepository accRepo = new FileAccountRepository(accPath);
        IAccount acc1 = AccountFactory.createAccount("SAVINGS", 1001, "Rajesh Sharma", 30, 50000.0);
        acc1.setPin(1234);
        System.out.println("  Saved: " + acc1.getAccountInfo());
        accRepo.save(acc1);

        FileAccountRepository diskRepo1 = new FileAccountRepository(accPath);
        IAccount found = diskRepo1.findById(1001);
        System.out.println("  Found: " + found.getAccountInfo());
        assert found != null : "Account 1001 should be found";
        assert found.getAccountNumber() == 1001 : "Account number should match";
        assert "Rajesh Sharma".equals(found.getAccountHolderName()) : "Name should match";
        assert found.getBalance() == 50000.0 : "Balance should match";
        System.out.println("  -> PASSED");

        // Small pause to guarantee distinct timestamps for backups
        Thread.sleep(10);

        // ============================================================
        // 📝 TEST 2: Multiple Accounts & Atomic Update
        // ============================================================
        System.out.println("\n[TEST 2] Multiple Accounts & Atomic Update:");
        IAccount acc2 = AccountFactory.createAccount("CURRENT", 1002, "Priya Patel", 28, 40000.0);
        accRepo.save(acc2);

        Thread.sleep(10);
        found.deposit(10000.0);
        accRepo.update(found);

        FileAccountRepository diskRepo2 = new FileAccountRepository(accPath);
        IAccount updated = diskRepo2.findById(1001);
        System.out.println("  Updated balance: Rs. " + updated.getBalance() + " (Expected: 60000.0)");
        assert updated.getBalance() == 60000.0 : "Updated balance should be 60000.0";

        List<IAccount> all = diskRepo2.findAll();
        System.out.println("  Total accounts in CSV: " + all.size() + " (Expected: 2)");
        assert all.size() == 2 : "Total accounts in CSV should be 2";
        System.out.println("  -> PASSED");

        // ============================================================
        // 📝 TEST 3: Transaction Save & Query in CSV
        // ============================================================
        System.out.println("\n[TEST 3] Transaction Save & Query in CSV:");
        FileTransactionRepository txnRepo = new FileTransactionRepository(txnPath, accRepo);
        Transaction t1 = new Transaction("TXN-101", LocalDateTime.now(), 1001, TransactionType.DEPOSIT, 10000.0, 60000.0, "SUCCESS", "Deposit", 0, 0);
        Transaction t2 = new Transaction("TXN-102", LocalDateTime.now(), 1001, TransactionType.WITHDRAW, 2000.0, 58000.0, "SUCCESS", "Withdrawal", 0, 0);
        Transaction t3 = new Transaction("TXN-103", LocalDateTime.now(), 1002, TransactionType.DEPOSIT, 5000.0, 45000.0, "SUCCESS", "Deposit", 0, 0);
        txnRepo.save(t1);
        txnRepo.save(t2);
        txnRepo.save(t3);

        FileTransactionRepository diskTxnRepo = new FileTransactionRepository(txnPath, accRepo);
        List<Transaction> txns1001 = diskTxnRepo.findByAccount(1001);
        List<Transaction> txns1002 = diskTxnRepo.findByAccount(1002);
        System.out.println("  Found transactions for #1001: " + txns1001.size() + " (Expected: 2)");
        System.out.println("  Found transactions for #1002: " + txns1002.size() + " (Expected: 1)");
        assert txns1001.size() == 2 : "Expected 2 transactions for 1001";
        assert txns1002.size() == 1 : "Expected 1 transaction for 1002";
        System.out.println("  -> PASSED");

        // ============================================================
        // 📝 TEST 4: Atomic Transfer via FileAccountRepository
        // ============================================================
        System.out.println("\n[TEST 4] Atomic Transfer via FileAccountRepository:");
        txnRepo.transfer(1001, 1002, 5000.0);
        IAccount senderAfter = accRepo.findById(1001);
        IAccount receiverAfter = accRepo.findById(1002);
        System.out.println("  Sender balance: Rs. " + senderAfter.getBalance() + " (Expected: 55000.0)");
        System.out.println("  Receiver balance: Rs. " + receiverAfter.getBalance() + " (Expected: 45000.0)");
        assert senderAfter.getBalance() == 55000.0 : "Sender balance should be 55000.0";
        assert receiverAfter.getBalance() == 45000.0 : "Receiver balance should be 45000.0";
        System.out.println("  -> PASSED");

        // ============================================================
        // 📝 TEST 5: Verify Timestamped Backups Created
        // ============================================================
        System.out.println("\n[TEST 5] Verify Timestamped Backups Created:");
        File[] backups = testDir.listFiles((dir, name) -> name.startsWith("accounts.csv.bak."));
        int backupCount = backups != null ? backups.length : 0;
        System.out.println("  Found " + backupCount + " backup files for accounts.csv.");
        assert backupCount == 2 : "Expected exactly 2 backup files preserved under maxBackups retention";
        System.out.println("  -> PASSED");

        // Clean up
        if (testDir.exists()) {
            File[] files = testDir.listFiles();
            if (files != null) {
                for (File f : files) f.delete();
            }
            testDir.delete();
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("  ALL ACTIVITY 26 FILE REPOSITORY TESTS PASSED!");
        System.out.println("=".repeat(60));
    }
}
