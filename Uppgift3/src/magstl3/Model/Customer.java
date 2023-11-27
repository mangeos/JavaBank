package magstl3.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Klassen Customer hanterar följande:
 * kundens för- och efternamn samt personnummer.
 * Kunden kan också hantera sina konton.
 * 
 * @author Magnus Östling, magstl-3
 */
public class Customer {
    private String firstName;
    private String lastName;
    private final String personNumber;
    private List<Account> accounts;

    /**
     * Konstruktorn för Customer.
     * 
     * @param firstName    Kundens förnamn.
     * @param lastName     Kundens efternamn.
     * @param personNumber Kundens personnummer.
     */
    public Customer(String firstName, String lastName, String personNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personNumber = personNumber;
        this.accounts = new ArrayList<>();
    }

    /**
     * Hämtar kundens förnamn.
     * 
     * @return Kundens förnamn.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sätter kundens förnamn.
     * 
     * @param firstName Det nya förnamnet att sätta.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Hämtar kundens efternamn.
     * 
     * @return Kundens efternamn.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sätter kundens efternamn.
     * 
     * @param lastName Det nya efternamnet att sätta.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Hämtar kundens personnummer.
     * 
     * @return Kundens personnummer.
     */
    public String getPersonNumber() {
        return personNumber;
    }

    /**
     * Lägger till ett konto till kundens lista över konton.
     * 
     * @param account Kontot att lägga till.
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * Hämtar en lista med kundens konton.
     * 
     * @return En lista med kundens konton.
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Tar bort ett konto från kundens lista över konton.
     * 
     * @param deletedAccount Kontot att ta bort.
     * @return Det borttagna kontot.
     */
    public Account removeAccount(Account deletedAccount) {
        accounts.remove(deletedAccount);
        return deletedAccount;
    }

    /**
     * Hämtar en sträng med kundens information (personnummer, förnamn och
     * efternamn).
     * 
     * @return En sträng med kundens information.
     */
    public String getCustomerInfo() {
        return personNumber + " " + firstName + " " + lastName;
    }
}
