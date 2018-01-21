package show.me.the.money.pricecomparison.data;

/**
 * Created by ljy on 2018-01-21.
 */

public class UpbitItem {
//    [{
//        "code": "CRIX.UPBIT.KRW-BTC",
//                "candleDateTime": "2018-01-21T14:30:00+00:00",
//                "candleDateTimeKst": "2018-01-21T23:30:00+09:00",
//                "openingPrice": 14428000.00000000,
//                "highPrice": 14457000.00000000,
//                "lowPrice": 14100000.00000000,
//                "tradePrice": 14171000.0,
//                "candleAccTradeVolume": 295.49212124,
//                "candleAccTradePrice": 4205989262.713060000,
//                "timestamp": 1516545349465,
//                "unit": 10
//    }]

    public String code;
    public String candleDateTime;
    public String candleDateTimeKst;
    public double openingPrice;
    public double highPrice;
    public double lowPrice;
    public double tradePrice;
    public double candleAccTradeVolume;
    public double candleAccTradePrice;
    public long timestamp;
    public int unit;

}
