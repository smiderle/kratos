package br.com.doubletouch.vendasup.util;

import android.content.Context;
import android.telephony.TelephonyManager;

import br.com.doubletouch.vendasup.VendasUp;

/**
 * Created by LADAIR on 19/05/2015.
 */
public class DeviceUtil {


    public static String getDeviceId() {

        final String deviceId = ((TelephonyManager) VendasUp.getAppContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (deviceId != null) {
            return deviceId;
        } else {
            return android.os.Build.SERIAL;
        }
    }
}
