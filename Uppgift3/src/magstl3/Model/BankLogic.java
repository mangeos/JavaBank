package magstl3.Model;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import magstl3.Model.BankLogic;
import magstl3.Model.Customer;

/**
 * Klassen BankLogic innehåller en samling (lista) med alla inlagda kunder och
 * ett antal publika metoder som hanterar kunder och konton.
 * 
 * Model hanterar data
 * 
 * @author Magnus Östling, magstl-3
 */
public class BankLogic {
    private List<Customer> customers;

    /**
     * konstruktorn som innehåller en lista.
     */
    public BankLogic() {
        this.customers = new ArrayList<>();
    }

    /**
     * Skapar en ny kund och lägger till den i listan.
     * 
     * @param name    Namnet på kunden.
     * @param surname Efternamnet på kunden.
     * @param pNo     Personnumret för kunden.
     * @return true om kunden skapades och lades till, annars false om en kund med
     *         samma personnummer redan finns.
     */
    public boolean createCustomer(String name, String surname, String pNo) {
        for (Customer customer : customers) {
            if (customer.getPersonNumber().equals(pNo)) {
                return false;
            }
        }
        customers.add(new Customer(name, surname, pNo));
        return true;
    }

    /**
     * Hämtar en lista med information om alla kunder.
     * 
     * @return En lista med strängar som innehåller information om varje kund.
     */
    public List<String> getAllCustomers() {
        List<String> customerList = new ArrayList<>();
        for (Customer customer : customers) {
            customerList.add(customer.getPersonNumber() + " " + customer.getFirstName() + " " + customer.getLastName());
        }
        return customerList;
    }

    /**
     * Hämtar en lista med informationen om kunden inklusive dennes konton.
     * 
     * @param pNo Personnumret för kunden.
     * @return Returnerar en List<String> som innehåller informationen om kunden
     *         inklusive dennes konton.
     * @return Returnerar null om kunden inte fanns.
     */
    public List<String> getCustomer(String pNo) {
        for (Customer customer : customers) {
            if (customer.getPersonNumber().equals(pNo)) {
                List<String> customerInfo = new ArrayList<>();
                customerInfo
                        .add(customer.getPersonNumber() + " " + customer.getFirstName() + " " + customer.getLastName());

                for (Account account : customer.getAccounts()) {
                    String balanceStr = NumberFormat.getCurrencyInstance(new Locale("sv", "SE"))
                            .format(account.getBalance());
                    NumberFormat percentFormat = NumberFormat.getPercentInstance(new Locale("sv", "SE"));
                    percentFormat.setMaximumFractionDigits(1);
                    String percentStr = percentFormat.format(account.getInterestRate() / 100);

                    customerInfo.add(account.getAccountNumber() + " " + balanceStr + " " + account.getAccountType()
                            + " " + percentStr);
                }
                return customerInfo;
            }
        }
        return null;
    }

    /**
     * Byter namn på vald kund, argumentet pNo identifierar den kund som ska få nytt
     * namn.
     * Skickas en tom sträng som name ska gamla förnamnet behållas och om en tom
     * sträng skickas in som surname ska efternamnet behållas.
     *
     * @param name    Namnet på kunden.
     * @param surname Efternamnet på kunden.
     * @param pNo     Personnumret för kunden.
     * 
     * @return Returnerar true om namnet ändrades annars returnerar false (alltså om
     *         kunden inte fanns eller om båda namn attributen är tomma).
     */
    public boolean changeCustomerName(String name, String surname, String pNo) {
        List<Customer> newCustomerList = new ArrayList<>();
        boolean existed = false;
        String theName = name.trim();
        String theSurname = surname.trim();
        if (theName.equals("") && theSurname.equals("")) {
            return false;
        }
        for (Customer customer : customers) {
            if (customer.getPersonNumber().equals(pNo)) {
                existed = true;
                newCustomerList.add(new Customer(theName.equals("") ? customer.getFirstName() : theName,
                        theSurname.equals("") ? customer.getLastName() : theSurname, pNo));
                this.customers = new ArrayList<>();
                this.customers = newCustomerList;
            } else {
                newCustomerList.add(customer);
                this.customers = new ArrayList<>();
                this.customers = newCustomerList;
            }
        }
        if (existed) {
            return true;
        }
        return false;
    }

    /**
     * Skapar ett sparkonto till kund med personnummer pNo.
     * Kontonummer ska vara unika för hela banken, inte bara för en enskild kund
     * 
     * @param pNo Personnumret för kunden.
     * @return Returnerar kontonumret(Int) som det skapade kontot fick Alternativt
     *         returneras –1 om inget konto skapades (kunden fanns inte).
     */
    public int createSavingsAccount(String pNo) {
        for (Customer customer : customers) {
            if (customer.getPersonNumber().equals(pNo)) {
                Account newAccount = new SavingsAccount(0.0, 1.2);
                customer.addAccount(newAccount);
                return newAccount.getAccountNumber();
            }
        }
        return -1;
    }

    /**
     * Returnerar en String som innehåller presentation av kontot med kontonummer
     * accountId som tillhör kunden med personnummer pNo
     * 
     * @param pNo       Personnumret för kunden.
     * @param accountId är kundens kontonummer.
     * 
     * @return Det som returneras ska vara: kontonummer saldo kr kontotyp räntesats
     *         %, Returnerar null om konto inte finns eller om kontot inte tillhör
     *         kunden
     */
    public String getAccount(String pNo, int accountId) {
        for (Customer customer : customers) {
            if (customer.getPersonNumber().equals(pNo)) {
                for (Account account : customer.getAccounts()) {
                    if (accountId == account.getAccountNumber()) {
                        return account.getAccountInfo();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gör en insättning på konto med kontonummer accountId som tillhör kunden med
     * personnummer pNo.
     *
     * @param pNo       Personnumret för kunden.
     * @param accountId är kundens kontonummer.
     * @param amount    är summan som kunden vill sätta in till kontot
     *
     * @return Returnerar true om det gick bra annars false
     */
    public boolean deposit(String pNo, int accountId, int amount) {
        for (Customer customer : customers) {
            if (customer.getPersonNumber().equals(pNo)) {
                for (Account account : customer.getAccounts()) {
                    if (accountId == account.getAccountNumber()) {
                        return account.deposit(amount);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Gör ett uttag på konto med kontonummer accountId som tillhör kunden med
     * personnummer pNo.
     * 
     * @param pNo       Personnumret för kunden.
     * @param accountId är kundens kontonummer.
     * @param amount    är summan som kunden vill ta ut från kontot
     * 
     * @return Returnerar true om det gick bra annars false
     */
    public boolean withdraw(String pNo, int accountId, int amount) {
        for (Customer customer : customers) {
            if (customer.getPersonNumber().equals(pNo)) {
                for (Account account : customer.getAccounts()) {
                    if (accountId == account.getAccountNumber()) {
                        return account.withdraw(amount);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Avslutar ett konto med kontonummer accountId som tillhör kunden med
     * personnummer pNo.
     * När man avslutar ett konto skall räntan beräknas som saldo*räntesats/100.
     * 
     * @param pNo       Personnumret för kunden.
     * @param accountId är kundens kontonummer.
     * 
     * @return Det som returneras ska se ut som följer: 1001 1 000,00 kr Sparkonto
     *         12,00 kr. Returnerar null om inget konto togs bort
     * 
     */
    public String closeAccount(String pNo, int accountId) {
        Account accountToRemove = null;
        for (Customer customer : customers) {
            for (Account account : customer.getAccounts()) {
                if (account.getAccountNumber() == accountId && customer.getPersonNumber().equals(pNo)) {
                    accountToRemove = account;
                    customer.removeAccount(accountToRemove);
                    return accountToRemove.getAccountInfo2();
                }
            }
        }
        // Om kontot inte hittades, returnera null
        return null;
    }

    /**
     * Tar bort en kund med personnummer pNo ur banken, alla kundens eventuella
     * konton tas också bort och resultatet returneras.
     * 
     * @param pNo Personnumret för kunden.
     * 
     * @return Listan som returneras ska innehålla information om kund på första
     *         platsen (personnummer förnamn efternamn) sedan följer de konton som
     *         togs bort (kontonummer saldo kr kontotyp ränta kr).
     * @return Returnerar null om ingen kund togs bort.
     */
    public List<String> deleteCustomer(String pNo) {
        List<String> deletedCustomerList = new ArrayList<>();
        Customer deletedCustomerList2 = null;

        boolean deleteCustomer = false;
        for (Customer customer : customers) {
            if (customer.getPersonNumber().equals(pNo)) {
                deletedCustomerList2 = customer;

                deleteCustomer = true;
                deletedCustomerList.add(customer.getCustomerInfo());
                for (Account account : customer.getAccounts()) {
                    deletedCustomerList.add(account.getAccountInfo2());
                }
            }
        }
        if (deleteCustomer) {
            customers.remove(deletedCustomerList2);
            return deletedCustomerList;
        } else {
            return null;
        }
    }

    /**
     * Skapar ett kreditkonto till kund med personnummer pNo.
     * Kontonummer ska vara unika för hela banken, inte bara för en enskild kund
     * 
     * @param pNo Personnumret för kunden.
     * @return Returnerar kontonumret(Int) som det skapade kontot fick Alternativt
     *         returneras –1 om inget konto skapades (kunden fanns inte).
     */
    public int createCreditAccount(String pNo) {
        for (Customer customer : customers) {
            if (customer.getPersonNumber().equals(pNo)) {
                Account newAccount = new CreditAccount(0.0, 0.5);
                customer.addAccount(newAccount);
                return newAccount.getAccountNumber();
            }
        }
        return -1;
    }

    /**
     * Hämtar en lista som innehåller presentation av alla transaktioner som gjorts
     * på kontot (datum, tid, belopp, saldo efter transaktionen)
     * 
     * @param pNo       Personnumret för kunden.
     * @param accountId konto id
     * @return Returnerar information om genomförda konto
     *         transaktioner(List<String>).
     */
    public List<String> getTransactions(String pNo, int accountId) {

        for (Customer customer : customers) {
            if (customer.getPersonNumber().equals(pNo)) {
                for (Account account : customer.getAccounts()) {
                    if (accountId == account.getAccountNumber()) {
                        return account.getInfoAboutTransactions();
                    }
                }
            }
        }
        return null;
    }

}
