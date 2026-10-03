public class FixedDepositAccount extends Account {

    private int tenureMonths;
    private double interestRate;

    public FixedDepositAccount(int accountNumber, String name, int age,
                               double initialBalance) {
        this(accountNumber, name, age, initialBalance, 12, 6.5);
    }

    public FixedDepositAccount(int accountNumber, String name, int age,
                               double initialBalance, int tenureMonths,
                               double interestRate) {
        super(accountNumber, name, age, initialBalance, "FIXED_DEPOSIT");
        this.tenureMonths = tenureMonths;
        this.interestRate = interestRate;
    }

    public double calculateMaturityAmount() {
        return getBalance() * Math.pow(1 + interestRate / 100.0,
                tenureMonths / 12.0);
    }

    @Override
    public boolean withdraw(double amount) {
        throw new AccountException(
                "Premature withdrawals are not permitted on Fixed Deposit accounts");
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public double getInterestRate() {
        return interestRate;
    }
}