package br.com.doubletouch.vendasup;

import android.app.Application;
import android.content.Context;

import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.Usuario;
import br.com.doubletouch.vendasup.domain.OauthAccess;

/**
 * Created by LADAIR on 27/01/2015.
 */
public class VendasUp extends Application {

    public static Context context;
    public static final String APP_TAG = "KRATOS";


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    /**
     * Retorna o contexto da aplicação.
     * @return context
     */
    public final static Context getAppContext(){
        return context;
    }

    private static OauthAccess oauthAccess;

    private static User user;

    private static BranchOffice branchOffice;



    //private static Usuario usuarioLogado;

    public static OauthAccess getOauthAccess() {
        return oauthAccess;
    }

    public static void setOauthAccess(OauthAccess oauthAccess) {
        VendasUp.oauthAccess = oauthAccess;
    }
/*
    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Usuario usuarioLogado) {
        VendasUp.usuarioLogado = usuarioLogado;
    }
*/
    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        VendasUp.user = user;
    }

    public static BranchOffice getBranchOffice() {
        return branchOffice;
    }

    public static void setBranchOffice(BranchOffice branchOffice) {
        VendasUp.branchOffice = branchOffice;
    }
}
