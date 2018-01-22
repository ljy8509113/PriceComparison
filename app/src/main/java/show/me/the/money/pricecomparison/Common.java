package show.me.the.money.pricecomparison;

import java.text.DecimalFormat;

/**
 * Created by KOITT on 2018-01-11.
 */

public class Common {
    //빗썸
    public static final String BITHUMB_URL = "https://api.bithumb.com/";
    public static final String BITHUMB_PUBLIC_URL = BITHUMB_URL + "public/";
    public static final String COINBUTTON_TAG = "COIN_BUTTON";

    //코인원
    public static final String COINONE_URL = "https://api.coinone.co.kr/";

    //업비트
    public static final String UPBIT_URL = "https://crix-api-endpoint.upbit.com/v1/crix/candles/days?code=CRIX.UPBIT.KRW-";

    //폴로닉스
    public static final String POLONIEX_URL = "https://poloniex.com/public?command=returnTicker";

    //환율
    //http://www.happycgi.com/16208#1 참고 url
    public static final String CHECK_KRW_URL = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%3D%22USDKRW%22&format=xml&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    public enum HTTP_TYPE {
        GET,
        POST
    }

    public enum EXCHANGE {
        BITHUMB,
        UPBIT,
        COINONE,
        POLONIEX;

        public static EXCHANGE getExchange(String name){
            if(name.equals(COINONE.name())){
                return COINONE;
            }else if(name.equals(UPBIT.name())){
                return UPBIT;
            }else{
                return BITHUMB;
            }
        }
    }

    public static final String EXCHANGE_KEY = "exchange";

    public static String toNumFormat(double num) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(num);
    }

//    static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).registerTypeAdapterFactory(new ArrayAdapterFactory()).create();
//    public static <T>T getConnectionResult(String res, Class<T> obj){
//        return gson.fromJson(res, obj);
//    }
}
