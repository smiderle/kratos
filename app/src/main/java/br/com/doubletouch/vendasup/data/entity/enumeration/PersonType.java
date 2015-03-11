package br.com.doubletouch.vendasup.data.entity.enumeration;

/**
 * Created by LADAIR on 24/02/2015.
 */
public enum PersonType {
    /**
     * 0 - Inexistente
     */
    NAO_INFORMADO,

    /**
     * 1 - Pessoa Fisica
     */
    FISICA,

    /**
     * 2 - Pessoa Juridica
     */
    JURIDICA;


    public static PersonType get(Integer ordinal){
        switch (ordinal){
            case 1:
                return FISICA;
            case 2:
                return JURIDICA;
            default:
                return NAO_INFORMADO;
        }
    }

}
