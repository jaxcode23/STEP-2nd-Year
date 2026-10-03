import java.util.Scanner;

public class TestAccount {

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            Account account1 = null;
            Account account2 = null;
            
            int choice;
            
            do {
                System.out.println("=".repeat(50));
                System.out.println("       GLOBAL DIGITAL BANK - ACCOUNT MENU");
                System.out.println("==================================================");
                System.out.println("1. Create Account");
                System.out.println("2. Deposit Money");
                System.out.println("3. Withdraw Money");
                System.out.println("4. Display Account");
                System.out.println("5. Display All Accounts");
                System.out.println("6. Exit");
                System.out.println("==================================================");
                System.out.print("Enter your choice: ");
                
                choice = sc.nextInt();
                
                switch (choice) {
                    
                    case 1 -> {
                        System.out.print("Enter Account Number: ");
                        int accountNumber = sc.nextInt();
                        
                        sc.nextLine(); // consume newline
                        
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        
                        System.out.print("Enter Initial Balance: ");
                        double balance = sc.nextDouble();
                        
                        sc.nextLine();
                        
                        System.out.print("Enter Account Type: ");
                        String accountType = sc.nextLine();
                        
                        Account newAccount = new Account(
                                accountNumber,
                                name,
                                age,
                                balance,
                                accountType
                        );
                        
                        if (account1 == null) {
                            account1 = newAccount;
                            System.out.println("Account 1 created successfully!");
                        } else if (account2 == null) {
                            account2 = newAccount;
                            System.out.println("Account 2 created successfully!");
                        } else {
                            System.out.println("Maximum of 2 accounts allowed.");
                        }
                    }
                    
                    case 2 -> {
                        if (account1 == null && account2 == null) {
                            System.out.println("No accounts available.");
                            break;
                        }
                        
                        System.out.print("Enter Account Number: ");
                        int depositAccount = sc.nextInt();
                        
                        Account depositAcc = findAccount(
                                account1, account2, depositAccount
                        );
                        
                        if (depositAcc == null) {
                            System.out.println("Account not found.");
                            break;
                        }
                        
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = sc.nextDouble();
                        
                        boolean depositResult = depositAcc.deposit(depositAmount);
                        
                        if (depositResult) {
                            System.out.println("Deposit successful!");
                            System.out.println(
                                    "New balance: ₹" + depositAcc.getBalance()
                            );
                        } else {
                            System.out.println("Deposit failed! Invalid amount.");
                        }
                    }
                    
                    case 3 -> {
                        if (account1 == null && account2 == null) {
                            System.out.println("No accounts available.");
                            break;
                        }
                        
                        System.out.print("Enter Account Number: ");
                        int withdrawAccount = sc.nextInt();
                        
                        Account withdrawAcc = findAccount(
                                account1, account2, withdrawAccount
                        );
                        
                        if (withdrawAcc == null) {
                            System.out.println("Account not found.");
                            break;
                        }
                        
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawAmount = sc.nextDouble();
                        
                        boolean withdrawResult =
                                withdrawAcc.withdraw(withdrawAmount);
                        
                        if (withdrawResult) {
                            System.out.println("Withdrawal successful!");
                            System.out.println(
                                    "New balance: ₹" + withdrawAcc.getBalance()
                            );
                        } else {
                            System.out.println(
                                    "Withdrawal failed! Insufficient balance or invalid amount."
                            );
                        }
                    }
                    
                    case 4 -> {
                        System.out.print("Enter Account Number: ");
                        int displayAccountNumber = sc.nextInt();
                        
                        Account displayAcc = findAccount(
                                account1, account2, displayAccountNumber
                        );
                        
                        if (displayAcc == null) {
                            System.out.println("Account not found.");
                        } else {
                            displayAccount(displayAcc);
                        }
                    }
                    
                    case 5 -> {
                        System.out.println("\n>>> ALL ACCOUNTS");
                        
                        if (account1 == null && account2 == null) {
                            System.out.println("No accounts available.");
                        } else {
                            if (account1 != null) {
                                displayAccount(account1);
                            }
                            
                            if (account2 != null) {
                                displayAccount(account2);
                            }
                        }
                    }
                    
                    case 6 -> System.out.println("Thank you for using Global Digital Bank!");
                    
                    default -> System.out.println("Invalid choice. Please try again.");
                }
                
            } while (choice != 6);
        }
    }


    // Find account using account number
    public static Account findAccount(
            Account account1,
            Account account2,
            int accountNumber) {

        if (account1 != null &&
                account1.getAccountNumber() == accountNumber) {
            return account1;
        }

        if (account2 != null &&
                account2.getAccountNumber() == accountNumber) {
            return account2;
        }

        return null;
    }


    // Display account information
    public static void displayAccount(Account account) {

        System.out.println(
                "Account #" + account.getAccountNumber()
                + " | " + account.getName()
                + " (" + account.getAge() + " yrs)"
                + " | " + account.getAccountType()
                + " | ₹" + account.getBalance()
                + " | " + account.getStatus()
        );
    }
}