package show.me.the.money.pricecomparison.data;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by KOITT on 2018-01-18.
 */

public class BithumbItem {
        public long opening_price;
        public long closing_price;
        public long min_price;
        public long max_price;
        public double average_price;
        public double units_traded;
        public double volume_1day;
        public double volume_7day;
        public long buy_price;
        public long sell_price;

        public Object[] objs = {opening_price, closing_price, min_price, max_price, average_price, units_traded, volume_1day, volume_7day, buy_price, sell_price};

        public void setValue(String key, String value){
                switch (key){
                        case "opening_price" :
                                opening_price = Long.parseLong(value);
                                break;
                        case "closing_price" :
                                closing_price = Long.parseLong(value);
                                break;
                        case "min_price":
                                min_price = Long.parseLong(value);
                                break;
                        case "max_price" :
                                max_price = Long.parseLong(value);
                                break;
                        case "average_price" :
                                average_price = Double.parseDouble(value);
                                break;
                        case "units_traded":
                                units_traded = Double.parseDouble(value);
                                break;
                        case "volume_1day":
                                volume_1day = Double.parseDouble(value);
                                break;
                        case "volume_7day" :
                                volume_7day = Double.parseDouble(value);
                                break;
                        case "buy_price":
                                buy_price = Long.parseLong(value);
                                break;
                        case "sell_price":
                                sell_price = Long.parseLong(value);
                                break;
                }
        }
}
