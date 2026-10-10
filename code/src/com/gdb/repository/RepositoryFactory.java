package com.gdb.repository;

import java.io.*;
import java.util.Properties;

/**
 * Factory creating and supplying repository instances based on persistence.properties configuration.
 */
public class RepositoryFactory {

    private static AccountRepository accountRepositoryInstance;
    private static TransactionRepository transactionRepositoryInstance;

    // ============================================================
    // 📝 STEP 14: Read persistence.mode from Configuration
    //
    // INSTRUCTIONS:
    //   1. Load properties from "config/persistence.properties" or classpath resource.
    //   2. Return property value for "persistence.mode" (default to "memory" if missing).
    // ============================================================
    public static String getPersistenceMode() {
        Properties props = new Properties();
        try (InputStream is = RepositoryFactory.class.getClassLoader().getResourceAsStream("config/persistence.properties")) {
            if (is != null) {
                props.load(is);
            } else {
                File f = new File("src/main/resources/config/persistence.properties");
                if (f.exists()) {
                    try (FileInputStream fis = new FileInputStream(f)) {
                        props.load(fis);
                    }
                } else {
                    File f2 = new File("config/persistence.properties");
                    if (f2.exists()) {
                        try (FileInputStream fis = new FileInputStream(f2)) {
                            props.load(fis);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Default to memory if loading fails
        }
        return props.getProperty("persistence.mode", "memory");
    }

    // ============================================================
    // 📝 STEP 15: Implement getAccountRepository()
    //
    // INSTRUCTIONS:
    //   1. Check persistence mode:
    //      - "memory" -> return singleton InMemoryAccountRepository
    //      - "jdbc"   -> throw UnsupportedOperationException("JDBC repository not implemented yet - coming in Activity 22")
    //      - "file"   -> throw UnsupportedOperationException("File repository not implemented yet")
    //      - default  -> return InMemoryAccountRepository
    // ============================================================
    public static synchronized AccountRepository getAccountRepository() {
        if (accountRepositoryInstance == null) {
            String mode = getPersistenceMode();
            if ("memory".equalsIgnoreCase(mode)) {
                accountRepositoryInstance = new InMemoryAccountRepository();
            } else if ("jdbc".equalsIgnoreCase(mode)) {
                throw new UnsupportedOperationException("JDBC repository not implemented yet - coming in Activity 22");
            } else if ("file".equalsIgnoreCase(mode)) {
                throw new UnsupportedOperationException("File repository not implemented yet");
            } else {
                accountRepositoryInstance = new InMemoryAccountRepository();
            }
        }
        return accountRepositoryInstance;
    }

    // ============================================================
    // 📝 STEP 16: Implement getTransactionRepository()
    //
    // INSTRUCTIONS:
    //   1. Check persistence mode:
    //      - "memory" -> return singleton InMemoryTransactionRepository
    //      - "jdbc"   -> throw UnsupportedOperationException("JDBC repository not implemented yet - coming in Activity 22")
    //      - "file"   -> throw UnsupportedOperationException("File repository not implemented yet")
    //      - default  -> return InMemoryTransactionRepository
    // ============================================================
    public static synchronized TransactionRepository getTransactionRepository() {
        if (transactionRepositoryInstance == null) {
            String mode = getPersistenceMode();
            if ("memory".equalsIgnoreCase(mode)) {
                transactionRepositoryInstance = new InMemoryTransactionRepository();
            } else if ("jdbc".equalsIgnoreCase(mode)) {
                throw new UnsupportedOperationException("JDBC repository not implemented yet - coming in Activity 22");
            } else if ("file".equalsIgnoreCase(mode)) {
                throw new UnsupportedOperationException("File repository not implemented yet");
            } else {
                transactionRepositoryInstance = new InMemoryTransactionRepository();
            }
        }
        return transactionRepositoryInstance;
    }

    public static synchronized void reset() {
        accountRepositoryInstance = null;
        transactionRepositoryInstance = null;
    }
}
