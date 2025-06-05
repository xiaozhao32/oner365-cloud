package com.oner365.vault.config.properties;

import java.io.Serializable;

/**
 * Vault Properties
 *
 * @author zhaoyong
 */
public class VaultProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String password;

    private String url;

    public VaultProperties() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return String.format("name:%s password:%s url:%s", this.name, this.password, this.url);
    }

}
