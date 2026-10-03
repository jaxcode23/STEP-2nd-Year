public class CurrentAccount extends Account {

    private double overdraftLimit;

    public CurrentAccount(int accountNumber, String name, int age,
                          double initialBalance) {
        this(accountNumber, name, age, initialBalance, 25000.0);
    }

    public CurrentAccount(int accountNumber, String name, int age,
                          double initialBalance, double overdraftLimit) {
        super(accountNumber, name, age, initialBalance, "CURRENT");
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean withdraw(double amount) {
        if (!"Active".equals(getStatus()) || amount <= 0) {
            return false;
        }
        if (amount > getBalance() + overdraftLimit) {
            throw new InsufficientBalanceException(
                    "Withdrawal exceeds the overdraft limit");
        }
        deductBalance(amount);
        return true;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
}