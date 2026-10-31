package com.gdb.repository;

import com.gdb.domain.*;
import com.gdb.exceptions.DataAccessException;
import com.gdb.file.AtomicFileWriter;
import com.gdb.file.BackupManager;
import com.gdb.file.CsvMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * File-backed implementation of AccountRepository.
 * Stores account records in a CSV/text file with atomic writes.
 */
public class FileAccountRepository implements AccountRepository {

    private final String filePath;
    private final boolean autosave;
    private int nextAccountNumber;
    private final Map<Integer, IAccount> cache = new HashMap<>();

    public FileAccountRepository(String filePath, boolean autosave, int startAccountNumber) {
        this.filePath = filePath;
        this.autosave = autosave;
        this.nextAccountNumber = startAccountNumber;
        load();
    }

    public FileAccountRepository(String filePath, int startAccountNumber) {
        this(filePath, true, startAccountNumber);
    }

    public FileAccountRepository(String filePath) {
        this(filePath, true, 1001);
    }

    // ============================================================
    // 📝 STEP 1: Implement load()
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
                IAccount acc = CsvMapper.fromCsv(line);
                if (acc != null) {
                    cache.put(acc.getAccountNumber(), acc);
                    if (acc.getAccountNumber() >= nextAccountNumber) {
                        nextAccountNumber = acc.getAccountNumber() + 1;
                    }
                }
            }
        } catch (IOException e) {
            throw new DataAccessException("Failed to load accounts from file: " + e.getMessage(), e);
        }
    }

    // ============================================================
    // 📝 STEP 2: Implement flush()
    // ============================================================
    public synchronized void flush() {
        try {
            if (Files.exists(Paths.get(filePath))) {
                BackupManager.backup(filePath, 2);
            }
            List<String> lines = new ArrayList<>();
            lines.add(CsvMapper.ACCOUNT_HEADER);
            List<IAccount> list = new ArrayList<>(cache.values());
            list.sort(Comparator.comparingInt(IAccount::getAccountNumber));
            for (IAccount acc : list) {
                lines.add(CsvMapper.toCsv(acc));
            }
            AtomicFileWriter.write(filePath, lines);
        } catch (IOException e) {
            throw new DataAccessException("Failed to flush accounts to file: " + e.getMessage(), e);
        }
    }

    // ============================================================
    // 📝 STEP 3: Implement save(IAccount account)
    // ============================================================
    @Override
    public synchronized void save(IAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        cache.put(account.getAccountNumber(), account);
        if (account.getAccountNumber() >= nextAccountNumber) {
            nextAccountNumber = account.getAccountNumber() + 1;
        }
        if (autosave) {
            flush();
        }
    }

    // ============================================================
    // 📝 STEP 4: Implement findById(int accountNumber)
    // ============================================================
    @Override
    public synchronized IAccount findById(int accountNumber) {
        return cache.get(accountNumber);
    }

    // ============================================================
    // 📝 STEP 5: Implement findAll()
    // ============================================================
    @Override
    public synchronized List<IAccount> findAll() {
        return new ArrayList<>(cache.values());
    }

    // ============================================================
    // 📝 STEP 6: Implement update(IAccount account)
    // ============================================================
    @Override
    public synchronized void update(IAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        cache.put(account.getAccountNumber(), account);
        if (autosave) {
            flush();
        }
    }

    // ============================================================
    // 📝 STEP 7: Implement delete(int accountNumber)
    // ============================================================
    @Override
    public synchronized void delete(int accountNumber) {
        cache.remove(accountNumber);
        if (autosave) {
            flush();
        }
    }

    // ============================================================
    // 📝 STEP 8: Implement exists(int accountNumber)
    // ============================================================
    @Override
    public synchronized boolean exists(int accountNumber) {
        return cache.containsKey(accountNumber);
    }

    // ============================================================
    // 📝 STEP 9: Implement nextAccountNumber()
    // ============================================================
    @Override
    public synchronized int nextAccountNumber() {
        return nextAccountNumber++;
    }
}
