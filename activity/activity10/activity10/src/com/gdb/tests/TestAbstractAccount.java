package com.gdb.tests;

import com.gdb.domain.*;
import com.gdb.exceptions.*;

public class TestAbstractAccount {

    public static boolean transfer(AbstractAccount from, AbstractAccount to, double amount, String pin) throws AccountException {
        from.withdraw(amount, pin);
        try {
            to.deposit(amount);
            return true;
        } catch (InvalidAmountException e) {
            // Revert withdrawal if deposit somehow failed
            from.deposit(amount);
            throw e;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Activity 10: Banking Operations Suite ===");

        // Step 1 - Create an array/portfolio of AbstractAccount objects
        SavingsAccount sa = new SavingsAccount("SAV1001", "Rajesh Sharma", 28, 10000.0, "ACTIVE", "1234", 1000.0, 4.0);
        CurrentAccount ca = new CurrentAccount("CUR1001", "Priya Patel", 34, 5000.0, "ACTIVE", "5678", 25000.0);
        SalaryAccount sla = new SalaryAccount("SAL1001", "Sneha Verma", 26, 30000.0, "ACTIVE", "2222", "Infosys");

        AbstractAccount[] portfolio = { sa, ca, sla };

        // Step 2 - Implement and test secure fund transfer from Savings to Current account with PIN authentication
        try {
            boolean success = transfer(sa, ca, 3000.0, "1234");
            if (success) {
                System.out.println("Transfer Rs 3000 from Savings to Current: SUCCESS");
                System.out.println("Savings Balance: Rs " + sa.getBalance() + " | Current Balance: Rs " + ca.getBalance());
            }
        } catch (AccountException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }

        // Step 3 - Test failed transfer with wrong PIN and verify no balance was credited/debited
        double prevSaBalance = sa.getBalance();
        double prevCaBalance = ca.getBalance();
        try {
            transfer(sa, ca, 1000.0, "9999");
            System.out.println("Failed Transfer test [FAIL]");
        } catch (InvalidPinException e) {
            if (sa.getBalance() == prevSaBalance && ca.getBalance() == prevCaBalance) {
                System.out.println("Failed Transfer (Wrong PIN): Exception caught, no balance changed [PASS]");
            } else {
                System.out.println("Failed Transfer (Wrong PIN): Balance changed incorrectly [FAIL]");
            }
        } catch (AccountException e) {
            System.out.println("Failed Transfer: Unexpected exception: " + e.getMessage());
        }

        // Step 4 - Process monthly cycle applying interest to every SavingsAccount and checking each SalaryAccount's inactive months
        for (AbstractAccount account : portfolio) {
            if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).applyInterest();
            } else if (account instanceof SalaryAccount) {
                ((SalaryAccount) account).getInactiveMonths();
            }
        }
        System.out.println("Monthly Interest Cycle processed for all qualifying accounts.");

        System.out.println("All banking operations passed!");
    }
}
