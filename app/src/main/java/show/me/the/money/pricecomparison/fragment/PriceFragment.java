package show.me.the.money.pricecomparison.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import show.me.the.money.pricecomparison.Common;
import show.me.the.money.pricecomparison.R;
import show.me.the.money.pricecomparison.data.BithumbItem;
import show.me.the.money.pricecomparison.listener.ConnectionListener;
import show.me.the.money.pricecomparison.network.ConnectionManager;
import show.me.the.money.pricecomparison.network.response.ResponseBithumbPrice;
import show.me.the.money.pricecomparison.util.NaturalDeserializer;

/**
 * Created by KOITT on 2018-01-15.
 */

public class PriceFragment extends Fragment implements ConnectionListener {
    final static String IDENTIFIER_BITHUMB_LIST = "bithumb_list";
    GsonBuilder gsonBuilder = new GsonBuilder();
    ArrayList<BithumbItem> arrayBithumb = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //String url, String params, Common.EXCHANGE exchange, ConnectionListener listener, Common.HTTP_TYPE type){
        ConnectionManager.Instance().request(Common.BITHUMB_PUBLIC_URL, "ticker/ALL", Common.EXCHANGE.BITHUMB, this, Common.HTTP_TYPE.GET,IDENTIFIER_BITHUMB_LIST );
        return inflater.inflate(R.layout.fragment_price, container, false);

    }

    @Override
    public void onSuccess(String res, Common.EXCHANGE exchange, String identifier) {
        switch (exchange){
            case BITHUMB:
                if(identifier.equals(IDENTIFIER_BITHUMB_LIST)){
//                    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).registerTypeAdapterFactory(new ArrayAdapterFactory()).create();
//                    ResponseBithumbPrice result = gson.fromJson(res, ResponseBithumbPrice.class);

//                    ResponseBithumbPrice result = Common.getConnectionResult(res, ResponseBithumbPrice.class);
//                    Gson gson = new Gson();
//                    ResponseBithumbPrice result = gson.fromJson(res, ResponseBithumbPrice.class);

                    gsonBuilder.registerTypeAdapter(BithumbItem.class, new NaturalDeserializer());
                    Gson gson = gsonBuilder.create();
                    ResponseBithumbPrice result = gson.fromJson(res, ResponseBithumbPrice.class);
                    result.makeMap();

                    if(result.isSuccess()){
                        for(String key : result.modifyMap.keySet()) {
//                            BithumbItem item = g.fromJson(result.data.get(key).toString(), BithumbItem.class);
                            BithumbItem obj = result.modifyMap.get(key);
                            for(int i=0; i<obj.objs.length; i++)
                                Log.d("lee - ", obj.objs[i] + "");
                        }
                    }


                }
                break;
            case COINONE:
                break;
            case UPBIT:
                break;
        }
    }

    @Override
    public void onFail(String code, String msg, String identifier) {

    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }
}
