package ru.simplepasswordkeeper.api.model;

import java.io.Serializable;
import java.util.*;

/**
 * <p>Model of user, owner of {@link Account}s.</p>
 * @author Nikita Osiptsov
 */
public class User implements Serializable {
    private String name;
    private List<Account> accounts;

    /**
     * <p>Creates new user with specified {@code name}.</p>
     * @param name name of user.
     * @throws NullPointerException if given name is {@code null} or empty.
     */
    public User(String name) throws NullPointerException {
        this.setName(name);
        accounts = new LinkedList<Account>();
    }

    /**
     * @return name of user.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Sets name of user.</p>
     * @param name name of user.
     * @throws NullPointerException if given name is {@code null} or empty.
     */
    public void setName(String name) throws NullPointerException {
        if(name == null || name.isBlank())
            throw new NullPointerException();
        this.name = name;
    }

    /**
     * <p>Adds given account to this user.</p>
     * @param account account to be associated with this user.
     * @throws NullPointerException if given account is {@code null}.
     */
    public void putAccount(Account account) throws NullPointerException  {
        if(account == null)
            throw new NullPointerException();

        accounts.add(account);
    }

    /**
     * <p>Calls {@link #putAccount(Account)} for each account in given {@link Collection}.</p>
     * @param accounts {@link Collection} of accounts.
     * @throws NullPointerException if one of given accounts is {@code null}.
     */
    public void putAccount(Collection<Account> accounts) throws NullPointerException {
        for(Account account : accounts)
            putAccount(account);
    }

    /**
     * @return {@link Set} of properties.
     */
    public List<Account> getAccounts() { return accounts; }

    @Override
    public int hashCode() {
        return Objects.hash(name, accounts);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(this == obj)
            return true;

        if(obj instanceof User)
            return name.equals(((User) obj).name) && accounts.equals(((User) obj).accounts);

        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
