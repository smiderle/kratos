package br.com.doubletouch.vendasup.data.net;

/**
 * Created by LADAIR on 27/01/2015.
 */
public interface Endpoints {

    public static final String VERSION = "/v1";
    public static final String ENDPOINT_PRIVATE = "/private";
    public static final String ENDPOINT_PUBLIC = "/public";
    public static final String CONTEXT = "http://192.168.0.107/vendas-api";

    public static final String ENDPOINT_PRODUTO = CONTEXT.concat(ENDPOINT_PRIVATE).concat(VERSION).concat("/product");

    public static final String ENDPOINT_OAUTH = CONTEXT.concat("/oauth/token");
}
