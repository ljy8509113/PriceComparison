package show.me.the.money.pricecomparison.network;

/**
 * Created by KOITT on 2018-01-16.
 */

public class ConnectionManager {
    static ConnectionManager instance = null;

    public ConnectionManager Instance(){
        if(instance == null)
            instance = new ConnectionManager();

        return instance;
    }

    public void request(){
        HttpConnection con = new HttpConnection();

    }

    public void response(){

    }

}
