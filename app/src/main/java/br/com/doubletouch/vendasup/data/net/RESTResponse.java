package br.com.doubletouch.vendasup.data.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LADAIR on 27/01/2015.
 */
public class RESTResponse {

    public RESTResponse(Integer code, Exception exception) {
        this.code = code;
        this.exception = exception;

    }

    public RESTResponse(Integer code, InputStream content) throws IOException {
        this.code = code;
        this.content = content;
    }

    /**
     * Código com o status de retorno
     */
    private Integer code;

    /**
     * Objeto de retorno no formato json
     */
    private String json;

    /**
     *
     */
    private Exception exception;

    /**
     * Conteudo que foi retornado
     */
    private InputStream content;

    /**
     * Retorna o json que foi retornado do webservice.
     * @return
     * @throws IOException
     */
    public String getJson() throws IOException {
        this.json =  toString(content);
        content.close();
        return json;
    }

    public Integer getCode(){
        return this.code;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    private String toString(InputStream is) throws IOException {

        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int lidos;
        while ((lidos = is.read(bytes)) > 0) {
            baos.write(bytes, 0, lidos);
        }
        return new String(baos.toByteArray());
    }
}
