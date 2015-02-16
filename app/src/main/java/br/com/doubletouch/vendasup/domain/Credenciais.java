package br.com.doubletouch.vendasup.domain;

/**
 * Created by LADAIR on 29/01/2015.
 */
public class Credenciais {

    public Credenciais(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String user;

    public String password;

    public final String clienteID =  "vendas-api";

    public final String clienteSecret =  "1234";

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClienteID() {
        return clienteID;
    }


    public String getClienteSecret() {
        return clienteSecret;
    }
}
