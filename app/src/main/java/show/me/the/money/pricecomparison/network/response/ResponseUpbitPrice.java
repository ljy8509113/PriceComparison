package show.me.the.money.pricecomparison.network.response;

import java.util.ArrayList;

import show.me.the.money.pricecomparison.data.UpbitItem;

/**
 * Created by KOITT on 2018-01-11.
 */

public class ResponseUpbitPrice extends ResponseBase{
    Object[] items;

    @Override
    public boolean isSuccess(){
        try {
            if(items.length > 0){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

}
