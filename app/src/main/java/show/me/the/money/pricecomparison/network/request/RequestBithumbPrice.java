package show.me.the.money.pricecomparison.network.request;

import com.google.gson.Gson;

/**
 * Created by KOITT on 2018-01-11.
 */

public class RequestBithumbPrice {
    String coinName;
    public RequestBithumbPrice(String conName){
        this.coinName = conName;
    }

    public String makeGetURL() {
        return "ticker/"+coinName;
    }

}
