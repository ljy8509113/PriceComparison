package show.me.the.money.pricecomparison.network.response;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import show.me.the.money.pricecomparison.data.BithumbItem;

/**
 * Created by KOITT on 2018-01-11.
 */

public class ResponseBithumbPriceList extends ResponseBase{
    public String status;
    public String date;
    public Map<String, Object> data;
    public Map<String, BithumbItem> modifyMap = new HashMap<>();

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

    public void makeMap(){
        for(String key : data.keySet()){
            if(key.equals("date")){
                date = data.get(key).toString();
            }else{
                String value = data.get(key).toString();
                value = value.replace("{","");
                value = value.replace("}","");
                String [] array = value.split(",");
                BithumbItem b = new BithumbItem();

                for(int i=0; i<array.length; i++) {
                    String[] result = array[i].split("=");
                    b.setValue(result[0].replaceAll(" ",""), result[1].replaceAll(" ",""));
                }
                b.coinName = key;
                modifyMap.put(key, b);
            }
        }
    }
}
