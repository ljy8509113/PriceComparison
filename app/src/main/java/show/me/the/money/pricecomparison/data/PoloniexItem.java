package show.me.the.money.pricecomparison.data;

/**
 * Created by KOITT on 2018-01-18.
 */

public class PoloniexItem {
        public int id;
        public double last;
        public double lowestAsk;
        public double highestBid;
        public double percentChange;
        public double baseVolume;
        public double quoteVolume;
        public int isFrozen;
        public double high24hr;
        public double low24hr;
        public boolean isKRW = false;

//        public void setValue(String key, String value){
//                switch (key){
//                        case "id" :
//                                id = Integer.parseInt(value);
//                                break;
//                        case "last" :
//                                last = Double.parseDouble(value);
//                                break;
//                        case "lowestAsk":
//                                lowestAsk = Double.parseDouble(value);
//                                break;
//                        case "highestBid" :
//                                highestBid = Double.parseDouble(value);
//                                break;
//                        case "percentChange" :
//                                percentChange = Double.parseDouble(value);
//                                break;
//                        case "baseVolume":
//                                baseVolume = Double.parseDouble(value);
//                                break;
//                        case "quoteVolume":
//                                quoteVolume = Double.parseDouble(value);
//                                break;
//                        case "isFrozen" :
//                                isFrozen = Integer.parseInt(value);
//                                break;
//                        case "high24hr":
//                                high24hr = Double.parseDouble(value);
//                                break;
//                        case "low24hr":
//                                low24hr = Double.parseDouble(value);
//                                break;
//                }
//        }
}
