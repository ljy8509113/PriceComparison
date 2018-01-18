package show.me.the.money.pricecomparison.network.response;

import java.util.Map;

import show.me.the.money.pricecomparison.data.BithumbItem;

/**
 * Created by KOITT on 2018-01-11.
 */

public class ResponseBithumbPrice extends ResponseBase{
    public String status;
    public String date;
    public Map<String, BithumbItem> data;

    @Override
    public boolean isSuccess(){
        try {
            if(Integer.parseInt(status) == 0){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }
}
