package com.gdb.service;

import com.gdb.domain.*;
import com.gdb.exceptions.*;
import java.time.LocalDateTime;

public class TransferService {

    public TransferService() {
    }

    public void transfer(IAccount from, IAccount to, double amount, int pin)
            throws AccountException {
        if (from == null || to == null) {
            throw new AccountException("Source and destination accounts are required");
        }
        if (!from.isActive() || !to.isActive()) {
            throw new InactiveAccountException("Both accounts must be active to transfer funds");
        }
        if (!from.verifyPin(pin)) {
            throw new InvalidPinException("Incorrect PIN");
        }
        if (!from.canWithdraw(amount)) {
            throw new InsufficientBalanceException("Insufficient balance for transfer of Rs. " + amount);
        }
        Account source = (Account) from;
        double dailyLimit = AccountRulesEngine.getInstance().getDailyTransferLimit(from.getAccountType(), from.getTenureYears());
        if (source.getDailyTransferTotal() + amount > dailyLimit) {
            throw new AccountException("Transfer of Rs. " + amount + " exceeds daily limit of Rs. " + dailyLimit);
        }
        from.withdraw(amount, pin);
        to.deposit(amount);
        source.updateDailyTransferTotal(amount);
    }

    // ============================================================
    // 📝 STEP 9: Return Transaction From transfer
    // ============================================================
    public Transaction transferWithTransaction(IAccount from, IAccount to, 
                                               double amount, int pin) throws AccountException {
        transfer(from, to, amount, pin);
        return new Transaction(
            Transaction.generateId(),
            LocalDateTime.now(),
            from.getAccountNumber(),
            TransactionType.TRANSFER,
            amount,
            from.getBalance(),
            "SUCCESS",
            "Transfer of Rs. " + amount + " to Account #" + to.getAccountNumber(),
            from.getAccountNumber(),
            to.getAccountNumber()
        );
    }
}
