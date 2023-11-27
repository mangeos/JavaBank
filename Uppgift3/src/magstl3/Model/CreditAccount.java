package magstl3.Model;

/**
 * klassen CreditAccount hanterar följande:
 * deposit, withdraw och calculateInterest.
 * 
 * @author Magnus Östling, magstl-3
 */
public class CreditAccount extends Account {
    public CreditAccount(double initialBalance, double interestRate) {
        super("Kreditkonto", initialBalance, interestRate);
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
        if ((super.getBalance() - amount) >= 0) {
            super.setBalance(super.getBalance() - amount);
            setInfoAboutTransactions(-amount);
            return true;
        }
        if (amount > 0 && (super.getBalance() - amount) >= -5000) {
            setInterestRate(7.0);
            super.setBalance(super.getBalance() - amount);
            setInfoAboutTransactions(-amount);
            return true;
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
