package show.me.the.money.pricecomparison.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import show.me.the.money.pricecomparison.Common;
import show.me.the.money.pricecomparison.R;
import show.me.the.money.pricecomparison.data.BithumbItem;
import show.me.the.money.pricecomparison.data.ExchangeCoinPrice;
import show.me.the.money.pricecomparison.listener.ConnectionListener;
import show.me.the.money.pricecomparison.network.ConnectionManager;
import show.me.the.money.pricecomparison.network.response.ResponseBithumbPrice;
import show.me.the.money.pricecomparison.util.NaturalDeserializer;

/**
 * Created by KOITT on 2018-01-15.
 */

public class PriceFragment extends Fragment implements ConnectionListener {

    class ConnectionComplate{
        public boolean isBithumb = false;
        public boolean isCoinOne = false;
        public boolean isUpBit = false;

        public boolean isComplate(){
            if(isBithumb && isCoinOne && isUpBit)
                return true;
            else
                return false;
        }

    }

    final static String IDENTIFIER_BITHUMB_LIST = "bithumb_list";
    GsonBuilder _gsonBuilder = new GsonBuilder();
    HashMap<String, ExchangeCoinPrice> _mapPriceBithumb = new HashMap<>();
    ConnectionComplate isConnection = new ConnectionComplate();

    ArrayList<ExchangeCoinPrice> _arrayPrice = new ArrayList<>();
    PriceAdapter _adapterPrice;
    String selectCoinName = "";

    ListView _listPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //String url, String params, Common.EXCHANGE exchange, ConnectionListener listener, Common.HTTP_TYPE type){
        View v = inflater.inflate(R.layout.fragment_price, container, false);
        _adapterPrice = new PriceAdapter(_arrayPrice);
        _listPrice = (ListView)v.findViewById(R.id.list_coin_price);
        _listPrice.setAdapter(_adapterPrice);
        ConnectionManager.Instance().request(Common.BITHUMB_PUBLIC_URL, "ticker/ALL", Common.EXCHANGE.BITHUMB, this, Common.HTTP_TYPE.GET,IDENTIFIER_BITHUMB_LIST );

        return v;

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

                    _gsonBuilder.registerTypeAdapter(BithumbItem.class, new NaturalDeserializer());
                    Gson gson = _gsonBuilder.create();
                    ResponseBithumbPrice result = gson.fromJson(res, ResponseBithumbPrice.class);
                    result.makeMap();

                    if(result.isSuccess()){
                        for(String key : result.modifyMap.keySet()) {
                            BithumbItem obj = result.modifyMap.get(key);
                            ExchangeCoinPrice price = new ExchangeCoinPrice(Common.EXCHANGE.BITHUMB, key, obj.closing_price, obj.average_price);
                            _mapPriceBithumb.put(key, price);
                        }
                    }

                    isConnection.isBithumb = true;
                    selectCoinName = "BTC";
                    upDateListView(selectCoinName);

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

    void upDateListView(String coinName){
//        if(isConnection.isComplate()){
            _arrayPrice.clear();
            _arrayPrice.add(_mapPriceBithumb.get(coinName));
            _adapterPrice.updateData(_arrayPrice);

        getActivity().runOnUiThread(
                new Runnable() {
                    public void run() {
                        _adapterPrice.notifyDataSetChanged();
                    }
                });
//        }
    }

    class PriceAdapter extends BaseAdapter{

        ArrayList<ExchangeCoinPrice> array;
        public PriceAdapter(ArrayList<ExchangeCoinPrice> array){
            this.array = array;
        }

        @Override
        public int getCount() {
            return array.size();
        }

        @Override
        public Object getItem(int i) {
            return array.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if(view == null){
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_exchange_price, null);

                holder = new ViewHolder();
                holder.exchange = view.findViewById(R.id.text_item_exchange_name);
                holder.price = view.findViewById(R.id.text_item_price);
                holder.averge = view.findViewById(R.id.text_item_avg);
                holder.premium = view.findViewById(R.id.text_item_premium);
                view.setTag(holder);

            }else{
                holder = (ViewHolder) view.getTag();
            }

            ExchangeCoinPrice item = array.get(i);
            holder.exchange.setText(item.exchangeName.name());
            holder.price.setText(item.price+"원");
            holder.premium.setText(item.premium+"");
            holder.averge.setText(item.average_price+"");

            return view;
        }

        public void updateData(ArrayList<ExchangeCoinPrice> array){
            this.array = array;
        }

        class ViewHolder{
            public TextView exchange;
            public TextView price;
            public TextView averge;
            public TextView premium;
        }
    }
}
