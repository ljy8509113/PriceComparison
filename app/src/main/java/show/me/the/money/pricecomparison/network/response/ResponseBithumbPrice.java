package show.me.the.money.pricecomparison.network.response;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by KOITT on 2018-01-11.
 */

public class ResponseBithumbPrice {

    String status;
    ArrayList<HashMap<String, Item>> data = new ArrayList<>();

    class Item {
        String opening_price;
        String closing_price;
        String min_price;
        String max_price;
        String average_price;
        String units_traded;
        String volume_1day;
        String volume_7day;
        String buy_price;
        String sell_price;
    }
}
