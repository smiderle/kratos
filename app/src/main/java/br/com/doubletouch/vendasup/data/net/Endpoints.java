package br.com.doubletouch.vendasup.data.net;

/**
 * Created by LADAIR on 27/01/2015.
 */
public interface Endpoints {

    public static final String VERSION = "/v1";
    public static final String ENDPOINT_PRIVATE = "/private";
    public static final String ENDPOINT_PUBLIC = "/public";
    public static final String CONTEXT = "http://192.168.1.19/vendas-api";
    //public static final String CONTEXT = "http://54.94.216.207/vendas-api";

    public static final String ENDPOINT_PRODUTO = CONTEXT.concat(ENDPOINT_PRIVATE).concat(VERSION).concat("/product");
    public static final String ENDPOINT_CUSTOMER = CONTEXT.concat(ENDPOINT_PRIVATE).concat(VERSION).concat("/customer");
    public static final String ENDPOINT_ORDER = CONTEXT.concat(ENDPOINT_PRIVATE).concat(VERSION).concat("/order");
    public static final String ENDPOINT_PRICE_TABLE = CONTEXT.concat(ENDPOINT_PRIVATE).concat(VERSION).concat("/priceTable");
    public static final String ENDPOINT_INSTALLMENT = CONTEXT.concat(ENDPOINT_PRIVATE).concat(VERSION).concat("/installment");
    public static final String ENDPOINT_USER = CONTEXT.concat(ENDPOINT_PRIVATE).concat(VERSION).concat("/user");
    public static final String ENDPOINT_ORGANIZATION = CONTEXT.concat(ENDPOINT_PRIVATE).concat(VERSION).concat("/organization");
    public static final String ENDPOINT_BRANCH = CONTEXT.concat(ENDPOINT_PRIVATE).concat(VERSION).concat("/branchOffice");
    public static final String ENDPOINT_PRODUCT_PROMOTION = CONTEXT.concat(ENDPOINT_PRIVATE).concat(VERSION).concat("/productPromotion");
    public static final String ENDPOINT_GOAL = CONTEXT.concat(ENDPOINT_PRIVATE).concat(VERSION).concat("/goal");
    public static final String ENDPOINT_SIGNIN = CONTEXT.concat(ENDPOINT_PUBLIC).concat(VERSION).concat("/signin");
    public static final String ENDPOINT_LICENSE = CONTEXT.concat(ENDPOINT_PRIVATE).concat(VERSION).concat("/license");



    public static final String ENDPOINT_OAUTH = CONTEXT.concat("/oauth/token");
}
