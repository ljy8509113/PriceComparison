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
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import show.me.the.money.pricecomparison.Common;
import show.me.the.money.pricecomparison.R;
import show.me.the.money.pricecomparison.data.BithumbItem;
import show.me.the.money.pricecomparison.data.CoinNameData;
import show.me.the.money.pricecomparison.data.CoinoneItem;
import show.me.the.money.pricecomparison.data.ExchangeCoinPrice;
import show.me.the.money.pricecomparison.listener.ConnectionListener;
import show.me.the.money.pricecomparison.network.ConnectionManager;
import show.me.the.money.pricecomparison.network.response.ResponseBithumbPrice;
import show.me.the.money.pricecomparison.network.response.ResponseCoinonePrice;
import show.me.the.money.pricecomparison.util.NaturalDeserializer;
import show.me.the.money.pricecomparison.view.LoadingView;

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
    final static String IDENTIFIER_COINONE_LIST = "coinone_list";
    final static String IDENTIFIER_BITHUMB_COIN = "bithumb_coin";
    final static String IDENTIFIER_COINONE_COIN = "coinone_coin";

    GsonBuilder _gsonBuilder = new GsonBuilder();
    HashMap<String, ExchangeCoinPrice> _mapPriceBithumb = new HashMap<>();
    HashMap<String, ExchangeCoinPrice> _mapPriceCoinone = new HashMap<>();
    ConnectionComplate isConnection = new ConnectionComplate();

    ArrayList<String > _arrayBithumbCoinName = new ArrayList<>();
    ArrayList<String > _arrayCoinoneCoinName = new ArrayList<>();
    HashMap<String, CoinNameData> _mapCoinNames = new HashMap<>();

    ArrayList<ExchangeCoinPrice> _arrayPrice = new ArrayList<>();
    PriceAdapter _adapterPrice;
    String selectCoinName = "";
    ArrayList<CoinNameData> _arrayCoinNames = new ArrayList<>();

    ListView _listPrice;
    ListView _listCoins;

    CoinNamesAdapter _adapterCoins;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //String url, String params, Common.EXCHANGE exchange, ConnectionListener listener, Common.HTTP_TYPE type){
        View v = inflater.inflate(R.layout.fragment_price, container, false);
        LoadingView.Instance().show(getContext());
        _adapterPrice = new PriceAdapter(_arrayPrice);
        _listPrice = (ListView)v.findViewById(R.id.list_coin_price);
        _listPrice.setAdapter(_adapterPrice);

        _listCoins = v.findViewById(R.id.list_coin_name);
        _adapterCoins = new CoinNamesAdapter(_arrayCoinNames);
        _listCoins.setAdapter(_adapterCoins);

        selectCoinName = "XRP";
        requestALLCoinPrice();

        return v;

    }

    void requestALLCoinPrice(){
        isConnection.isUpBit = true;
        ConnectionManager.Instance().request(Common.BITHUMB_PUBLIC_URL, "ticker/ALL", Common.EXCHANGE.BITHUMB, this, Common.HTTP_TYPE.GET,IDENTIFIER_BITHUMB_LIST );
        ConnectionManager.Instance().request(Common.COINONE_URL,"ticker?currency=all", Common.EXCHANGE.COINONE, this, Common.HTTP_TYPE.GET, IDENTIFIER_COINONE_LIST);
    }

    void requestCoinPrice(String coinName){
        ConnectionManager.Instance().request(Common.BITHUMB_PUBLIC_URL, "ticker/"+coinName, Common.EXCHANGE.BITHUMB, this, Common.HTTP_TYPE.GET,IDENTIFIER_BITHUMB_COIN );
        ConnectionManager.Instance().request(Common.COINONE_URL,"ticker?currency="+coinName.toLowerCase(), Common.EXCHANGE.COINONE, this, Common.HTTP_TYPE.GET, IDENTIFIER_COINONE_COIN);
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
                            _arrayBithumbCoinName.add(key);
                        }
                    }

                    isConnection.isBithumb = true;
                    upDateListView(selectCoinName);

                }
                break;
            case COINONE:
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, Object>>(){}.getType();
                Map<String,Object> map = gson.fromJson(res, stringStringMap);

                if(Integer.parseInt(map.get("errorCode").toString()) == 0 && map.get("result").equals("success")){
                    for(String key : map.keySet()){
                        if(!key.equals("errorCode") && !key.equals("result") && !key.equals("timestamp")){
                            CoinoneItem item = (CoinoneItem) gson.fromJson(map.get(key).toString(), CoinoneItem.class);
                            ExchangeCoinPrice price = new ExchangeCoinPrice(Common.EXCHANGE.COINONE, key.toUpperCase(), item.last, (item.high+item.low)/2);
                            _mapPriceCoinone.put(key.toUpperCase(), price);
                            _arrayCoinoneCoinName.add(key.toUpperCase());
                        }
                    }
                }

                isConnection.isCoinOne = true;
                upDateListView(selectCoinName);
                break;
            case UPBIT:
                break;
        }
    }

    @Override
    public void onFail(String code, String msg, String identifier) {

    }

    void makeCoinNameList(){
        _mapCoinNames.clear();

            for(String key: _arrayBithumbCoinName){
                CoinNameData data = new CoinNameData(key);
                data.isBithumb = true;
                _mapCoinNames.put(key, data);
            }

            for(String key : _arrayCoinoneCoinName){
                CoinNameData data = _mapCoinNames.get(key);
                if(data == null)
                    data = new CoinNameData(key);
                data.isCoinone = true;
                _mapCoinNames.put(key, data);
            }


            for(String key : _mapCoinNames.keySet()){
                _arrayCoinNames.add(_mapCoinNames.get(key));
            }

            _adapterCoins.update(_arrayCoinNames);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    _adapterCoins.notifyDataSetChanged();
                    LoadingView.Instance().hide();
                }
            });

    }

    void upDateListView(String coinName){
        if(isConnection.isComplate()){
            _arrayPrice.clear();
            _arrayPrice.add(_mapPriceBithumb.get(coinName));
            _arrayPrice.add(_mapPriceCoinone.get(coinName));
            _adapterPrice.updateData(_arrayPrice);

        getActivity().runOnUiThread(
                new Runnable() {
                    public void run() {
                        _adapterPrice.notifyDataSetChanged();
                    }
                });

            makeCoinNameList();
        }
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

    class CoinNamesAdapter extends BaseAdapter{
        ArrayList<CoinNameData> array;
        public CoinNamesAdapter(ArrayList<CoinNameData> array){
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
                view = inflater.inflate(R.layout.item_coin_names, null);

                holder = new ViewHolder();
                holder.name = view.findViewById(R.id.text_item_coin_name);
                holder.isBithumb = view.findViewById(R.id.text_item_is_bithumb);
                holder.isCoinone = view.findViewById(R.id.text_item_is_coinone);
                holder.isUpbit = view.findViewById(R.id.text_item_is_upbit);
                view.setTag(holder);

            }else{
                holder = (ViewHolder) view.getTag();
            }

            CoinNameData item = array.get(i);
            holder.name.setText(item.name);
            holder.isBithumb.setText(item.isBithumb ? "O":"X");
            holder.isCoinone.setText(item.isCoinone?"O":"X");
            holder.isUpbit.setText(item.isUpbit?"O":"X");

            return view;
        }

        public void update(ArrayList<CoinNameData>array){
            this.array = array;
        }

        class ViewHolder{
            TextView name;
            TextView isBithumb;
            TextView isCoinone;
            TextView isUpbit;
        }
    }
}
