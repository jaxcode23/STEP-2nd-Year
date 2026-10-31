package com.gdb.db;

import com.gdb.command.TransactionCommand;
import com.gdb.domain.IAccount;
import com.gdb.domain.Transaction;
import com.gdb.logging.LogDestination;
import com.gdb.repository.AccountRepository;
import com.gdb.repository.TransactionRepository;

import java.util.List;

/**
 * Utility for migrating legacy file/in-memory transaction command logs into relational SQLite database.
 */
public class DataMigrator {

    public static int migrate(LogDestination source, TransactionRepository target) {
        if (source == null || target == null) {
            return 0;
        }
        List<TransactionCommand> commands = source.readAll();
        int count = 0;
        for (TransactionCommand cmd : commands) {
            if (cmd != null && cmd.getTransaction() != null) {
                target.save(cmd.getTransaction());
                count++;
            }
        }
        return count;
    }

    public static int[] migrate(AccountRepository srcAcc, AccountRepository destAcc,
                                TransactionRepository srcTxn, TransactionRepository destTxn) {
        int accCount = 0;
        if (srcAcc != null && destAcc != null) {
            for (IAccount acc : srcAcc.findAll()) {
                if (acc != null) {
                    destAcc.save(acc);
                    accCount++;
                }
            }
        }
        int txnCount = 0;
        if (srcTxn != null && destTxn != null) {
            for (Transaction txn : srcTxn.findAll()) {
                if (txn != null) {
                    destTxn.save(txn);
                    txnCount++;
                }
            }
        }
        return new int[]{accCount, txnCount};
    }
}
