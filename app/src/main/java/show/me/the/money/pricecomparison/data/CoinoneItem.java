package show.me.the.money.pricecomparison.data;

/**
 * Created by KOITT on 2018-01-18.
 */

public class CoinoneItem {

        public long last;
        public long yesterday_last;
        public long yesterday_low;
        public long high;
        public long low;
        public long yesterday_first;
        public double yesterday_volume;
        public double volume;
        public long yesterday_high;
        public long first;
        public String currency;
        public String result;
        public long timestamp;

        public void setValue(String key, String value){
                switch (key){
                        case "last" :
                                last = Long.parseLong(value);
                                break;
                        case "yesterday_last" :
                                yesterday_last = Long.parseLong(value);
                                break;
                        case "yesterday_low":
                                yesterday_low = Long.parseLong(value);
                                break;
                        case "high" :
                                high = Long.parseLong(value);
                                break;
                        case "low" :
                                low = Long.parseLong(value);
                                break;
                        case "yesterday_first":
                                yesterday_first = Long.parseLong(value);
                                break;
                        case "yesterday_volume":
                                yesterday_volume = Double.parseDouble(value);
                                break;
                        case "volume" :
                                volume = Double.parseDouble(value);
                                break;
                        case "yesterday_high":
                                yesterday_high = Long.parseLong(value);
                                break;
                        case "first":
                                first = Long.parseLong(value);
                        case "currency":
                                currency = currency;
                                break;
                }
        }
}
