public class TestAccountEnhanced {

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println("  ENHANCED ACCOUNT TEST (BOOLEAN RETURNS)");
        System.out.println("============================================================");
        System.out.println();
        // Test 1: create a normal account
        System.out.println(">>> Test 1: Valid Account Creation");
        
        AccountEnhanced account1 = new AccountEnhanced(
            1001, "John Doe", 25, 1000.0, "Savings"
        );
        
        displayAccount(account1);
        
        
        // Test 2: age 16 should become 18
        System.out.println();
        System.out.println(">>> Test 2: Invalid Age (under 18)");
        System.out.println("Creating account with age 16");

        AccountEnhanced account2 = new AccountEnhanced(
                1002, "Young Kid", 16, 500.0, "Savings"
        );

        System.out.println("Age auto-corrected to: " + account2.getAge());
        displayAccount(account2);
        
        
        // Test 3: invalid type should become Savings
        System.out.println();
        System.out.println(">>> Test 3: Invalid Account Type");
        System.out.println("Creating account with type \"Invalid\"");
        
        AccountEnhanced account3 = new AccountEnhanced(
            1003, "Test User", 25, 500.0, "Invalid"
        );

        System.out.println(
                "Account type defaulted to: " + account3.getAccountType()
        );
        displayAccount(account3);

        
        // Test 4: balance below 500 should become 500
        System.out.println();
        System.out.println(">>> Test 4: Minimum Balance Enforcement on Creation");
        System.out.println("Creating Savings account with Rs300 (below minimum)");
        
        AccountEnhanced account4 = new AccountEnhanced(
            1004, "Bob Wilson", 25, 300.0, "Savings"
        );
        
        System.out.println(
            "Balance auto-corrected to minimum: Rs"
            + account4.getBalance()
        );
        displayAccount(account4);
        
        
        // Test 5: withdrawal cannot break the minimum balance
        System.out.println();
        System.out.println(">>> Test 5: Withdrawal with Minimum Balance");
        
        AccountEnhanced account5 = new AccountEnhanced(
            1005, "Alice Brown", 30, 1000.0, "Savings"
        );
        
        account5.setPin(1234);

        System.out.println("Initial:");
        displayAccount(account5);

        boolean result = account5.withdraw(200.0, 1234);

        System.out.println(
            "Withdrawing Rs200.0: "
            + (result ? "SUCCESS" : "FAILED")
        );
        
        if (result) {
            System.out.println("New balance: Rs" + account5.getBalance());
        }

        System.out.println("After withdrawal:");
        displayAccount(account5);

        // This would take the balance below the Rs500 minimum
        result = account5.withdraw(600.0, 1234);
        
        System.out.println(
            "Withdrawing Rs600.0 (would leave Rs200): "
                        + (result
                        ? "SUCCESS"
                        : "FAILED (Minimum balance violation)")
        );

        System.out.println("Current balance: Rs" + account5.getBalance());


        // Test 6: close the account and then open it again
        System.out.println();
        System.out.println(">>> Test 6: Account Status Management");
        
        AccountEnhanced account6 = new AccountEnhanced(
            1006, "Charlie Green", 35, 2000.0, "Savings"
        );

        System.out.println("Initial:");
        displayAccount(account6);
        
        boolean closeResult = account6.closeAccount();

        System.out.println(
                "Closing account: "
                        + (closeResult ? "SUCCESS" : "FAILED")
        );

        System.out.println("After close:");
        displayAccount(account6);
        
        boolean depositResult = account6.deposit(500.0);
        
        System.out.println(
                "Depositing Rs500.0 to closed account: "
                + (depositResult
                        ? "SUCCESS"
                        : "FAILED (Account inactive)")
        );

        boolean reopenResult = account6.reopenAccount();

        System.out.println(
            "Reopening account: "
            + (reopenResult ? "SUCCESS" : "FAILED")
        );
        
        System.out.println("After reopen:");
        displayAccount(account6);


        // Test 7: check correct and incorrect PINs
        System.out.println();
        System.out.println(">>> Test 7: PIN Protection");
        
        AccountEnhanced account7 = new AccountEnhanced(
                1007, "Diana Prince", 28, 1500.0, "Savings"
        );
        
        boolean pinResult = account7.setPin(1234);

        System.out.println(
                "Setting PIN 1234: "
                        + (pinResult ? "SUCCESS" : "FAILED")
                    );
                    
        boolean correctPinResult = account7.withdraw(200.0, 1234);

        System.out.println(
                "Withdrawing Rs200.0 with correct PIN (1234): "
                + (correctPinResult ? "SUCCESS" : "FAILED")
        );

        if (correctPinResult) {
            System.out.println("New balance: Rs" + account7.getBalance());
        }

        boolean incorrectPinResult = account7.withdraw(100.0, 9999);

        System.out.println(
                "Withdrawing Rs100.0 with incorrect PIN (9999): "
                        + (incorrectPinResult
                        ? "SUCCESS"
                        : "FAILED (Incorrect PIN)")
        );
        
        // account1 does not have a PIN
        boolean noPinResult = account1.withdraw(100.0, 1234);

        System.out.println(
                "Withdrawing Rs100.0 with PIN not set: "
                        + (noPinResult
                        ? "SUCCESS"
                        : "FAILED (PIN not set)")
                    );
                    
                    
                    // Show all the accounts created above
                    System.out.println();
                    System.out.println(">>> Test 8: All Accounts Summary");

        displayAccount(account1);
        displayAccount(account2);
        displayAccount(account3);
        displayAccount(account4);
        displayAccount(account5);
        displayAccount(account6);
        displayAccount(account7);
        
        
        System.out.println();
        System.out.println("============================================================");
        System.out.println("  ENHANCED TEST COMPLETED!");
        System.out.println("============================================================");
    }

    public static void displayAccount(AccountEnhanced account) {

        System.out.println(
                "Account #"
                        + account.getAccountNumber()
                        + " | "
                        + account.getName()
                        + " ("
                        + account.getAge()
                        + " yrs)"
                        + " | "
                        + account.getAccountType()
                        + " | Rs"
                        + account.getBalance()
                        + " | "
                        + account.getStatus()
                        + " | PIN: "
                        + (account.hasPin() ? "Yes" : "No")
        );
    }
}