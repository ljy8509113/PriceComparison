package show.me.the.money.pricecomparison.network;

import javax.net.ssl.HttpsURLConnection;

import show.me.the.money.pricecomparison.Common;
import show.me.the.money.pricecomparison.listener.ConnectionListener;

/**
 * Created by KOITT on 2018-01-16.
 */

public class ConnectionManager {
    static ConnectionManager instance = null;

    public static ConnectionManager Instance(){
        if(instance == null)
            instance = new ConnectionManager();
        return instance;
    }

    public void request(String url, String params, ConnectionListener listener, Common.HTTP_TYPE type, String identifier){
        HttpConnection con = new HttpConnection();
        con.request(url,params, type, listener, identifier);
    }

}
