public class AccountEnhanced {

    
    private static final double MIN_BALANCE_SAVINGS = 500.0;
    private static final double MIN_BALANCE_CURRENT = 1000.0;
    private static final int MIN_AGE = 18;
    private static final int MIN_PIN = 1000;
    private static final int MAX_PIN = 9999;

    private final int accountNumber;
    private String name;
    private int age;
    private double balance;
    private final String accountType;
    private String status;
    private Integer pin;

   
    public AccountEnhanced(int accountNumber, String name, int age,
                           double initialBalance, String accountType) {

        this.accountNumber = accountNumber;
        this.name = name;

        
        if (age < MIN_AGE) {
            this.age = MIN_AGE;
        } else {
            this.age = age;
        }

        
        if (accountType.equals("Savings") || accountType.equals("Current")) {
            this.accountType = accountType;
        } else {
            this.accountType = "Savings";
        }

        if (this.accountType.equals("Savings")) {
            if (initialBalance < MIN_BALANCE_SAVINGS) {
                this.balance = MIN_BALANCE_SAVINGS;
            } else {
                this.balance = initialBalance;
            }
        } else {
            if (initialBalance < MIN_BALANCE_CURRENT) {
                this.balance = MIN_BALANCE_CURRENT;
            } else {
                this.balance = initialBalance;
            }
        }

        this.status = "Active";
        this.pin = null;
    }

    
    public boolean deposit(double amount) {
        if (!status.equals("Active")) {
            return false;
        }

        if (amount <= 0) {
            return false;
        }

        balance += amount;
        return true;
    }

        public boolean withdraw(double amount, int pin) {
        if (!status.equals("Active")) {
            return false;
        }

        if (!verifyPin(pin)) {
            return false;
        }

        if (amount <= 0) {
            return false;
        }

        double minimumBalance;

        if (accountType.equals("Savings")) {
            minimumBalance = MIN_BALANCE_SAVINGS;
        } else {
            minimumBalance = MIN_BALANCE_CURRENT;
        }

        if (balance - amount < minimumBalance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    
    public boolean closeAccount() {
        if (status.equals("Inactive")) {
            return false;
        }

        status = "Inactive";
        return true;
    }

   
    public boolean reopenAccount() {
        if (status.equals("Active")) {
            return false;
        }

        status = "Active";
        return true;
    }

   
    public boolean setPin(int pin) {
        if (pin < MIN_PIN || pin > MAX_PIN) {
            return false;
        }

        this.pin = pin;
        return true;
    }
    public boolean verifyPin(int pin) {
        if (this.pin == null) {
            return false;
        }

        return this.pin == pin;
    }
    public boolean hasPin() {
        return pin != null;
    }

    
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getStatus() {
        return status;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
