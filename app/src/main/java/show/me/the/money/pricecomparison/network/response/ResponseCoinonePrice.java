package show.me.the.money.pricecomparison.network.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import show.me.the.money.pricecomparison.data.BithumbItem;
import show.me.the.money.pricecomparison.data.CoinoneItem;

/**
 * Created by KOITT on 2018-01-11.
 */

public class ResponseCoinonePrice extends ResponseBase{
    public String errorCode;
    public Map<String, CoinoneItem> modifyMap;

    @Override
    public boolean isSuccess(){
//        if(data != null){
//            try{
//                if(Integer.parseInt(data.get("errorCode").toString()) == 0){
//                    return true;
//                }
//            }catch (Exception e){
//                return false;
//            }
//        }
//        if(array != null && array.size() > 0){
//            return true;
//        }
        return false;
    }

    public void makeMap(){
//        for(String key : data.keySet()){
//            if(!key.equals("errorCode")){
//                String value = data.get(key).toString();
//                value = value.replace("{","");
//                value = value.replace("}","");
//                String [] array = value.split(",");
//                CoinoneItem c = new CoinoneItem();
//
//                for(int i=0; i<array.length; i++) {
//                    String[] result = array[i].split("=");
//                    c.setValue(result[0].replaceAll(" ",""), result[1].replaceAll(" ",""));
//                }
//                modifyMap.put(key, c);
//            }
//        }
    }

//    public String status;
//    public String date;
//    public Map<String, Object> data;
//    public Map<String, BithumbItem> modifyMap = new HashMap<>();
//
//    @Override
//    public boolean isSuccess(){
//        try {
//            if(Integer.parseInt(status) == 0){
//                return true;
//            }else{
//                return false;
//            }
//        }catch (Exception e){
//            return false;
//        }
//    }
//
//    public void makeMap(){
//        for(String key : data.keySet()){
//            if(key.equals("date")){
//                date = data.get(key).toString();
//            }else{
//                String value = data.get(key).toString();
//                value = value.replace("{","");
//                value = value.replace("}","");
//                String [] array = value.split(",");
//                BithumbItem b = new BithumbItem();
//
//                for(int i=0; i<array.length; i++) {
//                    String[] result = array[i].split("=");
//                    b.setValue(result[0].replaceAll(" ",""), result[1].replaceAll(" ",""));
//                }
//                b.coinName = key;
//                modifyMap.put(key, b);
//            }
//        }
//    }
}
