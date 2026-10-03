public class TestAccountSubclasses {

    public static void main(String[] args) {
        System.out.println("=== Activity 7: Account Subclasses Test ===");

        SavingsAccount savings = new SavingsAccount(
                2001, "Aarav", 25, 10000.0);
        System.out.println("Savings Account Created: Balance Rs "
                + savings.getBalance() + " | Min Balance: Rs "
                + savings.getMinBalance());

        CurrentAccount current = new CurrentAccount(
                2002, "Diya", 30, 5000.0);
        System.out.println("Current Account Created: Overdraft Limit Rs "
                + current.getOverdraftLimit());

        FixedDepositAccount fixedDeposit = new FixedDepositAccount(
                2003, "Kabir", 35, 50000.0);
        System.out.println("Fixed Deposit Created: Tenure "
                + fixedDeposit.getTenureMonths() + " months | Interest: "
                + fixedDeposit.getInterestRate() + "%");

        SalaryAccount salary = new SalaryAccount(
                2004, "Anika", 28, 25000.0, "Infosys");
        System.out.println("Salary Account Created: Employer "
                + salary.getEmployerName());

        System.out.println("All subclasses instantiated successfully!");
    }
}