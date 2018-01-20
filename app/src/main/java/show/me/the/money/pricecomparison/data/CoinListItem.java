package show.me.the.money.pricecomparison.data;

/**
 * Created by KOITT on 2018-01-18.
 */

public class CoinListItem {
    public String title;
    public long price;
    public double premium;
    public float dayChanged;
    long standardPrice;

    public CoinListItem(String title, long price, long standardPrice, float dayChanged){
        this.title = title;
        this.price = price;
        this.dayChanged = dayChanged;

        setPremium(standardPrice);
    }

    void setPremium(long standardPrice){
        this.standardPrice = standardPrice;

        double result;
        if(this.standardPrice > this.price){
            result = (double)(this.standardPrice / this.price) - 1;
            this.premium = (result * 100) * -1;
        }else{
            result = (double)(this.price/this.standardPrice) - 1;
            this.premium = (result * 100);
        }
    }
}
