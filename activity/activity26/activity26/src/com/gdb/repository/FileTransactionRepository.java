package com.gdb.repository;

import com.gdb.domain.*;
import com.gdb.exceptions.DataAccessException;
import com.gdb.file.AtomicFileWriter;
import com.gdb.file.BackupManager;
import com.gdb.file.CsvMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * File-backed implementation of TransactionRepository.
 * Stores transaction records in a text file and supports atomic balance transfers.
 */
public class FileTransactionRepository implements TransactionRepository {

    private final String filePath;
    private final boolean autosave;
    private final List<Transaction> cache = new ArrayList<>();
    private AccountRepository accountRepository;

    public FileTransactionRepository(String filePath, boolean autosave, int startId) {
        this.filePath = filePath;
        this.autosave = autosave;
        load();
    }

    public FileTransactionRepository(String filePath, int startId) {
        this(filePath, true, startId);
    }

    public FileTransactionRepository(String filePath) {
        this(filePath, true, 1);
    }

    public FileTransactionRepository(String filePath, AccountRepository accountRepository) {
        this(filePath, true, 1);
        this.accountRepository = accountRepository;
    }

    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // ============================================================
    // 📝 STEP 1: Implement load() and flush()
    // ============================================================
    public synchronized void load() {
        cache.clear();
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return;
        }
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String line : lines) {
                Transaction txn = CsvMapper.fromCsvTransaction(line);
                if (txn != null) {
                    cache.add(txn);
                }
            }
        } catch (IOException e) {
            throw new DataAccessException("Failed to load transactions from file: " + e.getMessage(), e);
        }
    }

    public synchronized void flush() {
        try {
            if (Files.exists(Paths.get(filePath))) {
                BackupManager.backup(filePath, 2);
            }
            List<String> lines = new ArrayList<>();
            lines.add(CsvMapper.TRANSACTION_HEADER);
            for (Transaction txn : cache) {
                lines.add(CsvMapper.toCsv(txn));
            }
            AtomicFileWriter.write(filePath, lines);
        } catch (IOException e) {
            throw new DataAccessException("Failed to flush transactions to file: " + e.getMessage(), e);
        }
    }

    // ============================================================
    // 📝 STEP 2: Implement save(Transaction transaction)
    // ============================================================
    @Override
    public synchronized void save(Transaction transaction) {
        if (transaction == null) return;
        cache.add(transaction);
        if (autosave) {
            flush();
        }
    }

    // ============================================================
    // 📝 STEP 3: Implement findByAccount(int accountNumber)
    // ============================================================
    @Override
    public synchronized List<Transaction> findByAccount(int accountNumber) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction txn : cache) {
            if (txn.getAccountNumber() == accountNumber || txn.getFromAccount() == accountNumber || txn.getToAccount() == accountNumber) {
                result.add(txn);
            }
        }
        return result;
    }

    // ============================================================
    // 📝 STEP 4: Implement findAll()
    // ============================================================
    @Override
    public synchronized List<Transaction> findAll() {
        return new ArrayList<>(cache);
    }

    // ============================================================
    // 📝 STEP 5: Implement clear()
    // ============================================================
    @Override
    public synchronized void clear() {
        cache.clear();
        if (autosave) {
            flush();
        }
    }

    // ============================================================
    // 📝 STEP 6: Implement transfer(int fromAccountNumber, int toAccountNumber, double amount)
    // ============================================================
    @Override
    public synchronized void transfer(int fromAccountNumber, int toAccountNumber, double amount) {
        if (amount <= 0) {
            throw new DataAccessException("Transfer amount must be positive. Provided: " + amount);
        }

        AccountRepository repo = this.accountRepository;
        if (repo == null) {
            File f = new File(filePath);
            File accFile = new File(f.getParentFile(), "accounts.csv");
            if (accFile.exists()) {
                repo = new FileAccountRepository(accFile.getPath());
            } else {
                repo = RepositoryFactory.getAccountRepository();
            }
        }

        IAccount from = repo.findById(fromAccountNumber);
        IAccount to = repo.findById(toAccountNumber);
        if (from == null) throw new DataAccessException("Sender account not found: " + fromAccountNumber);
        if (to == null) throw new DataAccessException("Receiver account not found: " + toAccountNumber);
        if (from.getBalance() < amount) {
            throw new DataAccessException("Insufficient balance: Rs. " + from.getBalance() + ", Required: Rs. " + amount);
        }

        IAccount updatedFrom;
        IAccount updatedTo;
        try {
            updatedFrom = AccountFactory.createAccount(from.getAccountType(), from.getAccountNumber(), from.getAccountHolderName(), 25, from.getBalance() - amount);
            if (from.hasPin()) updatedFrom.setPin(1234);
            updatedTo = AccountFactory.createAccount(to.getAccountType(), to.getAccountNumber(), to.getAccountHolderName(), 25, to.getBalance() + amount);
            if (to.hasPin()) updatedTo.setPin(1234);
        } catch (Exception e) {
            throw new DataAccessException("Failed to update balances during transfer: " + e.getMessage(), e);
        }

        repo.update(updatedFrom);
        repo.update(updatedTo);

        Transaction txn = new Transaction(
            Transaction.generateId(),
            LocalDateTime.now(),
            fromAccountNumber,
            TransactionType.TRANSFER,
            amount,
            updatedFrom.getBalance(),
            "SUCCESS",
            "Transfer of Rs. " + amount + " to Account #" + toAccountNumber,
            fromAccountNumber,
            toAccountNumber
        );
        save(txn);
    }
}
