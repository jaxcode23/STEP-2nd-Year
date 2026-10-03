package com.gdb.tests;

import com.gdb.domain.Account;
import com.gdb.exceptions.*;

public class TestAccountExceptions {
    public static void main(String[] args) {
        System.out.println("=== Activity 6: Exception Handling Suite ===");

        // NOTE: If you completed Activity 5 successfully, paste your working Account.java into src/com/gdb/domain and your exception classes into src/com/gdb/exceptions (replacing the provided versions).

        Account acc = new Account("ACC1001", "Rajesh Sharma", 28, 5000.0, "SAVINGS", "ACTIVE", "1234");

        // Step 1: Test Invalid PIN Exception & Catch InvalidPinException specifically
        try {
            acc.withdraw(500.0, "9999");
            System.out.println("[Test 1] Failed to catch Invalid PIN [FAIL]");
        } catch (InvalidPinException e) {
            System.out.println("[Test 1] Caught Invalid PIN: " + e.getMessage() + " [PASS]");
        } catch (AccountException e) {
            System.out.println("[Test 1] Unexpected exception: " + e.getMessage());
        }

        // Step 2: Test Inactive Account Exception (Suspend account, attempt withdrawal, catch InactiveAccountException)
        acc.suspend();
        try {
            acc.withdraw(500.0, "1234");
            System.out.println("[Test 2] Failed to catch Inactive Account [FAIL]");
        } catch (InactiveAccountException e) {
            System.out.println("[Test 2] Caught Inactive Account: " + e.getMessage() + " [PASS]");
        } catch (AccountException e) {
            System.out.println("[Test 2] Unexpected exception: " + e.getMessage());
        }
        acc.activate();

        // Step 3: Test Invalid Amount Exception (Deposit negative amount, catch InvalidAmountException)
        try {
            acc.deposit(-500.0);
            System.out.println("[Test 3] Failed to catch Invalid Amount [FAIL]");
        } catch (InvalidAmountException e) {
            System.out.println("[Test 3] Caught Invalid Amount: " + e.getMessage() + " [PASS]");
        }

        // Step 4: Test Insufficient Balance Exception (Withdraw > balance, catch InsufficientBalanceException)
        try {
            acc.withdraw(10000.0, "1234");
            System.out.println("[Test 4] Failed to catch Insufficient Funds [FAIL]");
        } catch (InsufficientBalanceException e) {
            System.out.println("[Test 4] Caught Insufficient Funds: " + e.getMessage() + " [PASS]");
        } catch (AccountException e) {
            System.out.println("[Test 4] Unexpected exception: " + e.getMessage());
        }

        // Step 5: Test Polymorphic Catch with Base AccountException (Close account, attempt withdrawal, catch AccountException)
        acc.close();
        try {
            acc.withdraw(500.0, "1234");
            System.out.println("[Test 5] Failed polymorphic catch [FAIL]");
        } catch (AccountException e) {
            System.out.println("[Test 5] Polymorphic Handler caught: " + e.getMessage() + " [PASS]");
        }

        System.out.println("All exception handling tests completed successfully!");
    }
}
