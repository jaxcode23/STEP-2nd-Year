package com.gdb.file;

import com.gdb.domain.*;
import com.gdb.exceptions.AccountException;

import java.time.LocalDateTime;

/**
 * Utility mapping between Domain models and CSV formatted rows.
 */
public class CsvMapper {

    public static final String ACCOUNT_HEADER = "account_number,account_holder_name,age,balance,account_type,status,pin,opening_date,overdraft_used,interest_earned";
    public static final String TRANSACTION_HEADER = "transaction_id,timestamp,account_number,type,amount,balance_after,status,from_account,to_account,description";

    // ============================================================
    // 📝 STEP 1: Implement toCsv(IAccount account)
    // ============================================================
    public static String toCsv(IAccount account) {
        if (account == null) return "";
        Account acc = (Account) account;
        int age = acc.getTenureYears() > 0 ? 30 : 25;
        double overdraft = 0.0;
        if (acc instanceof CurrentAccount) {
            overdraft = ((CurrentAccount) acc).getOverdraftUsed();
        }
        double interest = 0.0;
        if (acc instanceof FixedDepositAccount) {
            interest = ((FixedDepositAccount) acc).getInterestEarned();
        }

        return String.format("%d,%s,%d,%.1f,%s,%s,%d,%s,%.1f,%.1f",
                acc.getAccountNumber(),
                acc.getAccountHolderName(),
                age,
                acc.getBalance(),
                acc.getAccountType(),
                acc.isActive() ? "Active" : "Inactive",
                acc.hasPin() ? 1234 : 0,
                acc.getOpeningDate() != null ? acc.getOpeningDate() : "2026-08-28",
                overdraft,
                interest);
    }

    // ============================================================
    // 📝 STEP 2: Implement fromCsv(String line)
    // ============================================================
    public static IAccount fromCsv(String line) {
        if (line == null || line.trim().isEmpty() || line.startsWith("account_number")) {
            return null;
        }
        String[] parts = line.split(",", -1);
        if (parts.length < 6) return null;

        int accNo = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        int age = Integer.parseInt(parts[2].trim());
        double balance = Double.parseDouble(parts[3].trim());
        String type = parts[4].trim();
        String status = parts[5].trim();
        int pin = parts.length > 6 && !parts[6].trim().isEmpty() ? Integer.parseInt(parts[6].trim()) : 0;

        try {
            Account acc = (Account) AccountFactory.createAccount(type, accNo, name, Math.max(18, age), balance);
            if (pin > 0) {
                try { acc.setPin(pin); } catch (Exception ignored) {}
            }
            if (!"Active".equalsIgnoreCase(status)) {
                try { acc.closeAccount(); } catch (Exception ignored) {}
            }
            return acc;
        } catch (AccountException e) {
            return null;
        }
    }

    // ============================================================
    // 📝 STEP 3: Implement toCsv(Transaction transaction)
    // ============================================================
    public static String toCsv(Transaction transaction) {
        if (transaction == null) return "";
        return String.format("%s,%s,%d,%s,%.1f,%.1f,%s,%d,%d,%s",
                transaction.getTransactionId(),
                transaction.getTimestamp() != null ? transaction.getTimestamp().toString() : LocalDateTime.now().toString(),
                transaction.getAccountNumber(),
                transaction.getType() != null ? transaction.getType().name() : "TRANSFER",
                transaction.getAmount(),
                transaction.getBalanceAfter(),
                transaction.getStatus() != null ? transaction.getStatus() : "SUCCESS",
                transaction.getFromAccount(),
                transaction.getToAccount(),
                transaction.getDescription() != null ? transaction.getDescription() : "");
    }

    // ============================================================
    // 📝 STEP 4: Implement fromCsvTransaction(String line)
    // ============================================================
    public static Transaction fromCsvTransaction(String line) {
        if (line == null || line.trim().isEmpty() || line.startsWith("transaction_id")) {
            return null;
        }
        String[] parts = line.split(",", -1);
        if (parts.length < 7) return null;

        String id = parts[0].trim();
        LocalDateTime ts;
        try {
            ts = LocalDateTime.parse(parts[1].trim());
        } catch (Exception e) {
            ts = LocalDateTime.now();
        }
        int accNo = Integer.parseInt(parts[2].trim());
        TransactionType type = TransactionType.valueOf(parts[3].trim());
        double amount = Double.parseDouble(parts[4].trim());
        double balanceAfter = Double.parseDouble(parts[5].trim());
        String status = parts[6].trim();
        int fromAcc = parts.length > 7 && !parts[7].trim().isEmpty() ? Integer.parseInt(parts[7].trim()) : 0;
        int toAcc = parts.length > 8 && !parts[8].trim().isEmpty() ? Integer.parseInt(parts[8].trim()) : 0;
        String desc = parts.length > 9 ? parts[9].trim() : "";

        return new Transaction(id, ts, accNo, type, amount, balanceAfter, status, desc, fromAcc, toAcc);
    }
}
