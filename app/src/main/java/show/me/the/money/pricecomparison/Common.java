package show.me.the.money.pricecomparison;

/**
 * Created by KOITT on 2018-01-11.
 */

public class Common {
    public static final String BITHUMB_URL = "https://api.bithumb.com/";
    public static final String BITHUMB_PUBLIC_URL = BITHUMB_URL + "public/";
    public static final String COINBUTTON_TAG = "COIN_BUTTON";

    public enum HTTP_TYPE {
        GET,
        POST
    }

    public enum EXCHANGE {
        BITHUMB,
        UPBIT
    }

}
