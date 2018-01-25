package show.me.the.money.pricecomparison.data;

import android.util.Log;

import show.me.the.money.pricecomparison.Common;

/**
 * Created by jeounglee on 2018. 1. 20..
 */

public class ExchangeCoinPrice {
    public Common.EXCHANGE exchangeName;
    public String coinName;
    public double price;
    public double average_price;
    public double premium;

    public ExchangeCoinPrice(Common.EXCHANGE exchangeName, String coinName, double price, double average_price){
        this.exchangeName = exchangeName;
        this.coinName = coinName;
        this.price = price;
        this.average_price = average_price;
    }

    public void setPremium(double price){
        if(price == -1) {
            premium = -1;
        }else {
            if(price > 0)
                premium = (this.price / price - 1) * 100;
            else
                premium = -1;
        }
        Log.d(Common.TAG, premium+ "%");
    }

//    public void setKRW(double krw){
//        price = (int)(price * krw);
//    }

}
