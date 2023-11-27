package magstl3.Model;

/**
 * klassen SavingsAccount hanterar följande:
 * deposit, withdraw och calculateInterest.
 * 
 * @author Magnus Östling, magstl-3
 */
public class SavingsAccount extends Account {
    private int freeWithdrawals; // Antal gratis uttag per år

    public SavingsAccount(double initialBalance, double interestRate) {
        super("Sparkonto", initialBalance, interestRate);
        this.freeWithdrawals = 1;

    }

    /**
     * Gör en insättning på kontot.
     * 
     * @param amount Beloppet som ska sättas in.
     * @return true om insättningen lyckades, annars false.
     */
    @Override
    public boolean deposit(double amount) {
        if (amount > 0) {
            super.setBalance(super.getBalance() + amount);
            setInfoAboutTransactions(amount);
            return true;
        }
        return false;
    }

    /**
     * Gör ett uttag från kontot.
     * 
     * @param amount Beloppet som ska tas ut.
     * @return true om uttaget lyckades, annars false.
     */
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && super.getBalance() >= amount) {
            if (freeWithdrawals > 0) {
                super.setBalance(super.getBalance() - amount);
                freeWithdrawals--;
                setInfoAboutTransactions(-amount);
                return true;
            } else {
                double withdrawalFee = amount * 0.02; // 2% uttagsränta
                if (super.getBalance() - (amount + withdrawalFee) >= 0) {
                    super.setBalance(super.getBalance() - (amount + withdrawalFee));
                    setInfoAboutTransactions(-(amount + withdrawalFee));
                    return true;
                }

            }

        }
        return false;
    }

    /**
     * Beräknar och lägger till ränta till kontot baserat på räntesatsen.
     */
    @Override
    public void calculateInterest() {
        double interest = super.getBalance() * (super.getInterestRate() / 100);
        super.setBalance(super.getBalance() + interest);
    }

}
