package ru.simplepasswordkeeper.api.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Model of user, owner of {@link Account}s.</p>
 * @author Nikita Osiptsov
 */
public class User {
    private String name;
    private Set<Account> accounts;

    /**
     * <p>Creates new user with specified {@code name}.</p>
     * @param name name of user.
     * @throws NullPointerException if given name is {@code null} or empty.
     */
    public User(String name) throws NullPointerException {
        this.setName(name);
        accounts = new HashSet<Account>();
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
     * <p>Adds given account to this user. If account with same name exists, it will be overwritten.</p>
     * @param account account to be associated with this user.
     * @throws NullPointerException if given account is {@code null}.
     */
    public void putAccount(Account account) throws NullPointerException  {
        if(account == null)
            throw new NullPointerException();

        accounts.remove(account);
        accounts.add(account);
    }

    /**
     * <p>Calls {@link #putAccount(Account)} for each account in given {@link Collection}.</p>
     * @param accounts {@link Collection} of accounts.
     * @throws NullPointerException if one of given accounts is {@code null}.
     */
    public void putAccount(Collection<Account> accounts) throws NullPointerException {
        for(var account : accounts)
            putAccount(account);
    }

    /**
     * @return unmodifiable {@link Set} of properties.
     */
    public Set<Account> getAccounts() {
        return Collections.unmodifiableSet(accounts);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User)
            return name.equals(((User) obj).getName());
        return false;
    }
}
