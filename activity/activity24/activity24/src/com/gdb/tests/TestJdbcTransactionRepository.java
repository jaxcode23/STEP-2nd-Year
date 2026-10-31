package com.gdb.tests;

import com.gdb.command.*;
import com.gdb.db.*;
import com.gdb.domain.*;
import com.gdb.logging.*;
import com.gdb.repository.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class TestJdbcTransactionRepository {
    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("  ACTIVITY 24 — JDBC TRANSACTION REPOSITORY & MIGRATION");
        System.out.println("=".repeat(60));

        String testDbUrl = "jdbc:sqlite:test_txn_gdb.db";
        File dbFile = new File("test_txn_gdb.db");
        if (dbFile.exists()) dbFile.delete();

        ConnectionProvider provider = new JdbcConnectionProvider(testDbUrl);
        SchemaInitializer.initialize(provider);
        JdbcAccountRepository accRepo = new JdbcAccountRepository(provider);
        JdbcTransactionRepository txnRepo = new JdbcTransactionRepository(provider);

        // ============================================================
        // 📝 TEST 1: Transaction Save & Query by Account
        // ============================================================
        System.out.println("[TEST 1] Transaction Save & Query:");
        Transaction t1 = new Transaction(Transaction.generateId(), LocalDateTime.now(), 1001, TransactionType.DEPOSIT, 20000.0, 50000.0, "SUCCESS", "Deposit", 0, 0);
        Transaction t2 = new Transaction(Transaction.generateId(), LocalDateTime.now(), 1001, TransactionType.WITHDRAW, 5000.0, 45000.0, "SUCCESS", "Withdraw", 0, 0);
        txnRepo.save(t1);
        txnRepo.save(t2);
        System.out.println("  Saved 2 transaction records.");

        List<Transaction> txns1001 = txnRepo.findByAccount(1001);
        System.out.println("  Found transactions for Account #1001: " + txns1001.size());
        assert txns1001.size() == 2 : "Expected 2 transactions for 1001";
        System.out.println("  -> PASSED");

        // ============================================================
        // 📝 TEST 2: Atomic Transfer Execution
        // ============================================================
        System.out.println("\n[TEST 2] Atomic Transfer Execution:");
        accRepo.delete(1001);
        accRepo.delete(1002);
        IAccount acc1 = AccountFactory.createAccount("SAVINGS", 1001, "Rajesh Sharma", 30, 50000.0);
        IAccount acc2 = AccountFactory.createAccount("CURRENT", 1002, "Priya Patel", 28, 30000.0);
        accRepo.save(acc1);
        accRepo.save(acc2);

        System.out.println("  Initial Sender Balance: Rs. " + acc1.getBalance() + ", Receiver Balance: Rs. " + acc2.getBalance());
        System.out.println("  Executing atomic transfer of Rs. 10000.0...");
        txnRepo.transfer(1001, 1002, 10000.0);

        IAccount updated1 = accRepo.findById(1001);
        IAccount updated2 = accRepo.findById(1002);
        System.out.println("  Updated Sender Balance: Rs. " + updated1.getBalance());
        System.out.println("  Updated Receiver Balance: Rs. " + updated2.getBalance());
        assert updated1.getBalance() == 40000.0 : "Sender balance should be 40000.0";
        assert updated2.getBalance() == 40000.0 : "Receiver balance should be 40000.0";

        List<Transaction> transferTxns = txnRepo.findByAccount(1001);
        assert transferTxns.stream().anyMatch(t -> t.getType() == TransactionType.TRANSFER) : "Transfer transaction should exist";
        System.out.println("  Transfer audit transaction recorded in database.");
        System.out.println("  -> PASSED");

        // ============================================================
        // 📝 TEST 3: Atomic Rollback on Insufficient Balance
        // ============================================================
        System.out.println("\n[TEST 3] Atomic Rollback on Insufficient Balance:");
        System.out.println("  Attempting transfer of Rs. 999999.0 (exceeds balance)...");
        boolean caught = false;
        try {
            txnRepo.transfer(1001, 1002, 999999.0);
        } catch (Exception e) {
            caught = true;
            String msg = e.getMessage();
            if (msg != null && msg.contains("Insufficient balance")) {
                System.out.println("  Caught expected exception: Insufficient balance");
            } else {
                System.out.println("  Caught expected exception: " + msg);
            }
        }
        assert caught : "Should throw exception on insufficient balance";

        IAccount unchanged1 = accRepo.findById(1001);
        IAccount unchanged2 = accRepo.findById(1002);
        System.out.println("  Sender Balance unchanged: Rs. " + unchanged1.getBalance());
        System.out.println("  Receiver Balance unchanged: Rs. " + unchanged2.getBalance());
        assert unchanged1.getBalance() == 40000.0 : "Sender balance should remain 40000.0";
        assert unchanged2.getBalance() == 40000.0 : "Receiver balance should remain 40000.0";
        System.out.println("  -> PASSED");

        // ============================================================
        // 📝 TEST 4: Data Migration from Memory to JDBC
        // ============================================================
        System.out.println("\n[TEST 4] Data Migration from Memory to JDBC:");
        AccountRepository memAccRepo = new InMemoryAccountRepository();
        TransactionRepository memTxnRepo = new InMemoryTransactionRepository();

        memAccRepo.save(AccountFactory.createAccount("SAVINGS", 1003, "Amit Kumar", 35, 25000.0));
        memAccRepo.save(AccountFactory.createAccount("CURRENT", 1004, "Sunita Rao", 40, 60000.0));
        memAccRepo.save(AccountFactory.createAccount("FIXEDDEPOSIT", 1005, "Vikas Gupta", 29, 75000.0));

        for (int i = 1; i <= 5; i++) {
            memTxnRepo.save(new Transaction("TXN-MIG-" + i, LocalDateTime.now(), 1003, TransactionType.DEPOSIT, 1000.0 * i, 25000.0 + (1000.0 * i), "SUCCESS", "Migration Txn " + i, 0, 0));
        }

        int[] stats = DataMigrator.migrate(memAccRepo, accRepo, memTxnRepo, txnRepo);
        System.out.println("  Migrated " + stats[0] + " accounts and " + stats[1] + " transaction records.");
        assert stats[0] == 3 : "Should migrate 3 accounts";
        assert stats[1] == 5 : "Should migrate 5 transactions";

        assert accRepo.exists(1003) : "Account 1003 should exist in DB";
        assert accRepo.exists(1004) : "Account 1004 should exist in DB";
        assert accRepo.exists(1005) : "Account 1005 should exist in DB";
        System.out.println("  Verified migrated accounts in SQLite database.");
        System.out.println("  -> PASSED");

        // Verify DataMigrator with LogDestination as well
        MemoryLogDestination memLog = new MemoryLogDestination();
        SavingsAccount sa = (SavingsAccount) AccountFactory.createAccount("SAVINGS", 1006, "Log Test", 30, 10000.0);
        sa.setPin(1234);
        DepositCommand cmd = new DepositCommand(sa, 2000.0);
        cmd.execute();
        memLog.write(cmd);
        int logMigrated = DataMigrator.migrate(memLog, txnRepo);
        assert logMigrated == 1 : "Should migrate 1 command log";

        // Clean up
        provider.shutdown();
        if (dbFile.exists()) dbFile.delete();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("  ALL ACTIVITY 24 JDBC TRANSACTION TESTS PASSED!");
        System.out.println("=".repeat(60));
    }
}
