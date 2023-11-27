package magstl3.Model;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * klassen Account hanterar följande information:
 * saldo, räntesats, kontonummer och kontotyp (Sparkonto).
 * 
 * @author Magnus Östling, magstl-3
 */
public abstract class Account {
    private double balance;
    private double interestRate;
    private int accountNumber;
    private static int lastNumber = 1000;
    private String accountType;
    private List<String> transactionInfo = new ArrayList<>();

    /**
     * Konstruktorn för Account.
     * 
     * @param accountType    Typen av konto (t.ex., Sparkonto).
     * @param initialBalance Det ursprungliga saldot på kontot.
     * @param interestRate   Räntesatsen för kontot.
     */
    protected Account(String accountType, double initialBalance, double interestRate) {
        lastNumber++;
        this.accountNumber = lastNumber;
        this.accountType = accountType;
        this.balance = initialBalance;
        this.interestRate = interestRate;
    }

    /**
     * Hämtar kontonumret för kontot.
     * 
     * @return Kontonumret.
     */
    public int getAccountNumber() {
        return accountNumber;
    }

    /**
     * Hämtar kontotypen för kontot.
     * 
     * @return Kontotypen.
     */
    public String getAccountType() {
        return this.accountType;
    }

    /**
     * Sätter saldo på kontot.
     *
     */
    public void setBalance(double b) {
        this.balance = b;
    }

    /**
     * Hämtar saldo på kontot.
     * 
     * @return Saldo på kontot.
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * Hämtar räntesatsen för kontot.
     * 
     * @return Räntesatsen för kontot.
     */
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double i) {
        interestRate = i;
    }

    public void setInfoAboutTransactions(double amount) {
        /*
         * spara datum (datum, tid, belopp, saldo efter transaktionen), ex:
         * [2021-08-27 09:42:10 −500,00 kr Saldo: −500,00 kr, 2021-08-27 09:42:11 −4
         * 000,00 kr Saldo: −4 500,00 kr]
         */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateTime = new Date();
        String formattedDate = dateFormat.format(dateTime);
        String amountStr = NumberFormat.getCurrencyInstance(new Locale("sv", "SE")).format(amount);
        String balanceStr = NumberFormat.getCurrencyInstance(new Locale("sv", "SE")).format(getBalance());

        transactionInfo.add(String.format("%s %s Saldo: %s", formattedDate, amountStr,
                balanceStr));

    }

    public List<String> getInfoAboutTransactions() {
        return transactionInfo;
    }

    /**
     * Hämtar information om kontot som en sträng.
     * 
     * @return En sträng som innehåller kontonumret, saldo, kontotyp och räntesats i
     *         rätt format.
     */
    public String getAccountInfo() {
        String balanceStr = NumberFormat.getCurrencyInstance(new Locale("sv", "SE")).format(getBalance());

        NumberFormat percentFormat = NumberFormat.getPercentInstance(new Locale("sv", "SE"));
        percentFormat.setMaximumFractionDigits(1);
        String percentStr = percentFormat.format(getInterestRate() / 100);
        return getAccountNumber() + " " + balanceStr + " " + getAccountType() + " " + percentStr;
    }

    /**
     * Hämtar information om kontot och lägger till ränteberäkningen i strängen.
     * 
     * @return En sträng som innehåller kontonumret, saldo, kontotyp, räntesats och
     *         ränta i rätt format.
     */

    public String getAccountInfo2() {
        String balanceStr = NumberFormat.getCurrencyInstance(new Locale("sv", "SE")).format(getBalance());
        double balance1 = getBalance();
        NumberFormat percentFormat = NumberFormat.getPercentInstance(new Locale("sv", "SE"));
        percentFormat.setMaximumFractionDigits(1);
        // String percentStr = percentFormat.format(getInterestRate() / 100);
        calculateInterest();
        String balanceStr2 = NumberFormat.getCurrencyInstance(new Locale("sv", "SE"))
                .format((getBalance() - balance1));
        return getAccountNumber() + " " + balanceStr + " " + getAccountType() + " " + balanceStr2;
    }

    // Abstrakta metoder som man måste skapa och definera i underklasserna
    public abstract boolean deposit(double amount);

    public abstract boolean withdraw(double amount);

    public abstract void calculateInterest();
}