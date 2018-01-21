package show.me.the.money.pricecomparison;

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

    public enum HTTP_TYPE {
        GET,
        POST
    }

    public enum EXCHANGE {
        BITHUMB,
        UPBIT,
        COINONE;

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

//    static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).registerTypeAdapterFactory(new ArrayAdapterFactory()).create();
//    public static <T>T getConnectionResult(String res, Class<T> obj){
//        return gson.fromJson(res, obj);
//    }
}
