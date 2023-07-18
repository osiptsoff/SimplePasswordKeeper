package ru.simplepasswordkeeper.api.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p> Model of account.</p>
 * <p>Keeps name and properties of account, properties are organized as pairs (e. g. "password" : "123")</p>
 * @author Nikita Osiptsov
 */
public class Account implements Serializable {
    private String name;
    private Map<String, String> properties;

    /**
     * <p>Creates new account with specified {@code name}.</p>
     * @param name name of account.
     * @throws NullPointerException if given name is {@code null} or empty.
     */
    public Account(String name) throws NullPointerException {
        this.setName(name);
        properties = new LinkedHashMap<String, String>();
    }

    /**
     * @return name of account.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Sets name of account.</p>
     * @param name name of account.
     * @throws NullPointerException if given name is {@code null} or empty.
     */
    public void setName(String name) throws NullPointerException {
        if(name == null || name.isBlank())
            throw new NullPointerException();
        this.name = name;
    }

    /**
     * <p>Puts property of account. If property with given name exists, overwrites it.</p>
     * @param propName name of property,
     * @param propValue value of property.
     * @throws NullPointerException if one of given parameters is {@code null} or empty string.
     */
    public void putProperty(String propName, String propValue) throws NullPointerException {
        if(propName == null || propName.isBlank() || propValue == null || propValue.isBlank())
            throw new NullPointerException();
        properties.put(propName, propValue);
    }

    /**
     * <p>Calls {@link #putProperty(String, String)} for each of {@link Map} entries.</p>
     * @param props {@link Map} of properties.
     * @throws NullPointerException if Map is or contents {@code null}.
     */
    public void putProperty(Map<String, String> props) throws NullPointerException {
        for(var entry : props.entrySet())
            properties.put(entry.getKey(), entry.getKey());
    }

    /**
     * @return unmodifiable {@link Map} of properties.
     */
    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, properties);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(this == obj)
            return true;

        if(obj instanceof Account)
            return name.equals(((Account) obj).name) && properties.equals(((Account) obj).properties);

        return false;
    }
}
