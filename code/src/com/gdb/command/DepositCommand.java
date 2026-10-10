package com.gdb.command;

import com.gdb.domain.Account;
import com.gdb.domain.IAccount;
import com.gdb.domain.Transaction;

public class DepositCommand implements TransactionCommand {
    private static final long serialVersionUID = 1L;

    // ============================================================
    // 📝 STEP 3: Declare Fields
    // ============================================================
    private IAccount account;
    private double amount;
    private Transaction transaction;

    // ============================================================
    // 📝 STEP 4: Write Constructor
    // ============================================================
    public DepositCommand(IAccount account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    // ============================================================
    // 📝 STEP 5: Implement execute()
    // ============================================================
    @Override
    public void execute() throws Exception {
        if (account instanceof Account) {
            this.transaction = ((Account) account).depositWithTransaction(amount);
        } else {
            account.deposit(amount);
        }
    }

    // ============================================================
    // 📝 STEP 6: Implement getTransaction()
    // ============================================================
    @Override
    public Transaction getTransaction() {
        return this.transaction;
    }
}
