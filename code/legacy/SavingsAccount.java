public class SavingsAccount extends Account {

    private double minBalance;
    private double interestRate;

    public SavingsAccount(int accountNumber, String name, int age,
                          double initialBalance) {
        this(accountNumber, name, age, initialBalance, 1000.0, 4.0);
    }

    public SavingsAccount(int accountNumber, String name, int age,
                          double initialBalance, double minBalance,
                          double interestRate) {
        super(accountNumber, name, age, initialBalance, "SAVINGS");
        this.minBalance = minBalance;
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        deposit(getBalance() * interestRate / 100.0);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > getBalance() - minBalance) {
            throw new MinimumBalanceViolationException(
                    "Withdrawal would breach the minimum balance");
        }
        return super.withdraw(amount);
    }

    public double getMinBalance() {
        return minBalance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setMinBalance(double minBalance) {
        this.minBalance = minBalance;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}