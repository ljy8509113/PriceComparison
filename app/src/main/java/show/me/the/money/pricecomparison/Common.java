package show.me.the.money.pricecomparison;

import android.os.Environment;

import java.io.File;
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
//    public static final String CHECK_KRW_URL = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%3D%22USDKRW%22&format=xml&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
//    public static final String CHECK_KRW_URL = "https://finance.yahoo.com/quote/KRW=X";
    public static final String CHECK_KRW_URL = "https://freecurrencyrates.com/en/widget-vertical?iso=USDEURGBPJPYCNYXUL&df=2&p=FsCw1k6p3&v=fits&source=fcr&width=245&width_title=0&firstrowvalue=1&thm=A6C9E2,FCFDFD,4297D7,5C9CCC,FFFFFF,C5DBEC,FCFDFD,2E6E9E,000000&title=Currency%20Converter&tzo=-540";

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

    public static String getPath(){
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        String path = "";
        if(root == null || root.equals("")){
            path = "/textFolder";
        }else{
            path = root+"/textFolder";
        }

        File f = new File(path);
        if(f.isDirectory() == false)
            f.mkdir();

        return path+"/text.txt";
    }

//    static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).registerTypeAdapterFactory(new ArrayAdapterFactory()).create();
//    public static <T>T getConnectionResult(String res, Class<T> obj){
//        return gson.fromJson(res, obj);
//    }
}
