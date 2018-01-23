package show.me.the.money.pricecomparison.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import show.me.the.money.pricecomparison.Common;
import show.me.the.money.pricecomparison.R;
import show.me.the.money.pricecomparison.data.BithumbItem;
import show.me.the.money.pricecomparison.data.CoinNameData;
import show.me.the.money.pricecomparison.data.CoinoneItem;
import show.me.the.money.pricecomparison.data.ExchangeCoinPrice;
import show.me.the.money.pricecomparison.data.PoloniexItem;
import show.me.the.money.pricecomparison.data.UpbitItem;
import show.me.the.money.pricecomparison.listener.ConnectionListener;
import show.me.the.money.pricecomparison.network.ConnectionManager;
import show.me.the.money.pricecomparison.network.response.ResponseBithumbPrice;
import show.me.the.money.pricecomparison.network.response.ResponseBithumbPriceList;
import show.me.the.money.pricecomparison.network.response.ResponseUpbitPrice;
import show.me.the.money.pricecomparison.util.NaturalDeserializer;
import show.me.the.money.pricecomparison.view.LoadingView;

/**
 * Created by KOITT on 2018-01-15.
 */

public class PriceFragment extends Fragment implements ConnectionListener, AdapterView.OnItemClickListener {

    class ConnectionComplate{
        public boolean isBithumb = false;
        public boolean isCoinOne = false;
        public boolean isUpBit = false;
        public boolean isPoloniex = false;

        public boolean isComplate(){
            if(isBithumb && isCoinOne && isUpBit && isPoloniex)
                return true;
            else
                return false;
        }

        public void setDefault(){
            isUpBit = false;
            isBithumb = false;
            isCoinOne = false;
            isPoloniex = false;
        }
    }

    final String IDENTIFIER_BITHUMB_LIST = "bithumb_list";
    final String IDENTIFIER_COINONE_LIST = "coinone_list";
    final String IDENTIFIER_BITHUMB_COIN = "bithumb_coin";
    final String IDENTIFIER_COINONE_COIN = "coinone_coin";
    final String IDENTIFIER_UPBIT_COIN = "upbit_coin";
    final String IDENTIFIER_POLONIEX_LIST = "poloniex_list";
    public static final String IDENTIFIER_USD_TO_KRW = "krw";

    final String TAG_COIN_NAME="1";
    final String TAG_EXCHANGE="2";
    final int NOT_DATA = -1;

    GsonBuilder _gsonBuilder = new GsonBuilder();
    HashMap<Common.EXCHANGE, ExchangeCoinPrice> _mapPrice = new HashMap<>();
    ConnectionComplate isConnection = new ConnectionComplate();

    ArrayList<String > _arrayBithumbCoinName = new ArrayList<>();
    ArrayList<String > _arrayCoinoneCoinName = new ArrayList<>();
    HashMap<String, CoinNameData> _mapCoinNames = new HashMap<>();

    ArrayList<ExchangeCoinPrice> _arrayPrice = new ArrayList<>();
    PriceAdapter _adapterPrice;
    String _selectCoinName = "";
    ArrayList<CoinNameData> _arrayCoinNames = new ArrayList<>();

    ListView _listPrice;
    ListView _listCoins;
    TextView _txtSelectedCoin;

    CoinNamesAdapter _adapterCoins;
    Gson _gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //String url, String params, Common.EXCHANGE exchange, ConnectionListener listener, Common.HTTP_TYPE type){
        View v = inflater.inflate(R.layout.fragment_price, container, false);

        _adapterPrice = new PriceAdapter(_arrayPrice);
        _listPrice = (ListView)v.findViewById(R.id.list_coin_price);
        _listPrice.setAdapter(_adapterPrice);
        _txtSelectedCoin = v.findViewById(R.id.text_selected_coin);

        _listCoins = v.findViewById(R.id.list_coin_name);
        _adapterCoins = new CoinNamesAdapter(_arrayCoinNames);
        _listCoins.setAdapter(_adapterCoins);

        _listPrice.setOnItemClickListener(this);
        _listPrice.setTag(TAG_EXCHANGE);
        _listCoins.setOnItemClickListener(this);
        _listCoins.setTag(TAG_COIN_NAME);

        _selectCoinName = "XRP";
        requestALLCoinPrice();
        Log.d("lee", "view create");

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LoadingView.Instance().show(getContext());
        Log.d("lee", "view created");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getTag().toString()){
            case TAG_COIN_NAME:
                LoadingView.Instance().show(getContext());
                String coinName = ((TextView)view.findViewById(R.id.text_item_coin_name)).getText().toString();
                requestCoinPrice(coinName);
                break;
            case TAG_EXCHANGE:

                break;
        }
    }

    void requestALLCoinPrice(){
//        isConnection.isUpBit = true;
        ConnectionManager.Instance().request(Common.BITHUMB_PUBLIC_URL, "ticker/ALL", this, Common.HTTP_TYPE.GET,IDENTIFIER_BITHUMB_LIST );
        ConnectionManager.Instance().request(Common.COINONE_URL,"ticker?currency=all", this, Common.HTTP_TYPE.GET, IDENTIFIER_COINONE_LIST);
        ConnectionManager.Instance().request(Common.UPBIT_URL, _selectCoinName+"&count=1", this, Common.HTTP_TYPE.GET, IDENTIFIER_UPBIT_COIN);
        ConnectionManager.Instance().request(Common.POLONIEX_URL, "", this, Common.HTTP_TYPE.GET, IDENTIFIER_POLONIEX_LIST);
    }

    void requestCoinPrice(String coinName){
        _selectCoinName = coinName;
        boolean isExBithumb = false;
        boolean isExCoinone = false;
        for(String name : _arrayBithumbCoinName){
            if(name.toUpperCase().equals(coinName.toUpperCase())){
                ConnectionManager.Instance().request(Common.BITHUMB_PUBLIC_URL, "ticker/"+coinName, this, Common.HTTP_TYPE.GET,IDENTIFIER_BITHUMB_COIN );
                isExBithumb = true;
                break;
            }
        }

        for(String name : _arrayCoinoneCoinName){
            if(name.toUpperCase().equals(coinName.toUpperCase())){
                ConnectionManager.Instance().request(Common.COINONE_URL,"ticker?currency="+coinName.toLowerCase(), this, Common.HTTP_TYPE.GET, IDENTIFIER_COINONE_COIN);
                isExCoinone = true;
                break;
            }
        }

        ConnectionManager.Instance().request(Common.UPBIT_URL, coinName+"&count=1",  this, Common.HTTP_TYPE.GET, IDENTIFIER_UPBIT_COIN);
        ConnectionManager.Instance().request(Common.POLONIEX_URL, "", this, Common.HTTP_TYPE.GET, IDENTIFIER_POLONIEX_LIST);

        if(!isExBithumb) {
            isConnection.isBithumb = true;
            upDateListView(coinName, getBithumbItem(null, coinName));
        }

        if(!isExCoinone) {
            isConnection.isCoinOne = true;
            upDateListView(coinName, getCoinoneItem(null, coinName));
        }
    }

    @Override
    public void onSuccess(String res, String identifier) {
        switch (identifier){
            case IDENTIFIER_BITHUMB_LIST: {
//                if(identifier.equals(IDENTIFIER_BITHUMB_LIST)){
//                    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).registerTypeAdapterFactory(new ArrayAdapterFactory()).create();
//                    ResponseBithumbPrice result = gson.fromJson(res, ResponseBithumbPrice.class);

//                    ResponseBithumbPrice result = Common.getConnectionResult(res, ResponseBithumbPrice.class);
//                    Gson gson = new Gson();
//                    ResponseBithumbPrice result = gson.fromJson(res, ResponseBithumbPrice.class);

                _gsonBuilder.registerTypeAdapter(BithumbItem.class, new NaturalDeserializer());
                Gson gson = _gsonBuilder.create();
                ResponseBithumbPriceList result = gson.fromJson(res, ResponseBithumbPriceList.class);
                result.makeMap();
                BithumbItem item = null;

                if (result.isSuccess()) {
                    for (String key : result.modifyMap.keySet()) {
                        //BithumbItem obj = result.modifyMap.get(key);
                        //setBithumbItem(obj, key);
                        if (_selectCoinName.equals(key)) {
                            item = result.modifyMap.get(key);
                        }
                        _arrayBithumbCoinName.add(key);
                    }
                }
                isConnection.isBithumb = true;
                upDateListView(_selectCoinName, getBithumbItem(item, _selectCoinName));
            }
                    break;
            case IDENTIFIER_BITHUMB_COIN : {
//                }else if(identifier.equals(IDENTIFIER_BITHUMB_COIN)){
                Log.d("lee", res);
                ResponseBithumbPrice info = _gson.fromJson(res, ResponseBithumbPrice.class);
                isConnection.isBithumb = true;
                upDateListView(_selectCoinName, getBithumbItem(info.data, _selectCoinName));
//                }
            }
                break;
            case IDENTIFIER_COINONE_LIST: {
//                if(identifier.equals(IDENTIFIER_COINONE_LIST)){
                Type stringStringMap = new TypeToken<Map<String, Object>>() {
                }.getType();
                Map<String, Object> map = _gson.fromJson(res, stringStringMap);
                CoinoneItem item = null;
                if (Integer.parseInt(map.get("errorCode").toString()) == 0 && map.get("result").equals("success")) {
                    for (String key : map.keySet()) {
                        if (!key.equals("errorCode") && !key.equals("result") && !key.equals("timestamp")) {
                            if (_selectCoinName.equals(key.toUpperCase()))
                                item = (CoinoneItem) _gson.fromJson(map.get(key).toString(), CoinoneItem.class);
//                                setCoinoneIte(item, key);
                            _arrayCoinoneCoinName.add(key.toUpperCase());
                        }
                    }
                }
                isConnection.isCoinOne = true;
                upDateListView(_selectCoinName, getCoinoneItem(item, _selectCoinName));
            }
                    break;
//                }else if(identifier.equals(IDENTIFIER_COINONE_COIN)){
            case IDENTIFIER_COINONE_COIN : {
                Log.d("lee", res);
                CoinoneItem item = _gson.fromJson(res, CoinoneItem.class);
                isConnection.isCoinOne = true;
                upDateListView(_selectCoinName, getCoinoneItem(item, _selectCoinName));
//                }
            }
                break;
            case IDENTIFIER_UPBIT_COIN:
            {
//                if(identifier.equals(IDENTIFIER_UPBIT_COIN)){
                    Type stringStringMap = new TypeToken<ArrayList<UpbitItem>>(){}.getType();
                    ArrayList<UpbitItem> array = _gson.fromJson(res, stringStringMap);
                    isConnection.isUpBit = true;
                    if(array != null && array.size() > 0){
                        UpbitItem item = array.get(0);
                        upDateListView(_selectCoinName, getUpbitItem(item, _selectCoinName));
                    }else{
                        upDateListView(_selectCoinName, getUpbitItem(null, _selectCoinName));
                    }
                    Log.d("lee", array.toString());
                }
                break;

            case IDENTIFIER_POLONIEX_LIST: {
                Type stringStringMap = new TypeToken<Map<String, PoloniexItem>>() {
                }.getType();
                Map<String, PoloniexItem> map = _gson.fromJson(res, stringStringMap);

                PoloniexItem selectedItem = null;
                if (map != null && map.size() > 0) {
                    for (String key : map.keySet()) {
                        if (key.equals("USDT_" + _selectCoinName)) {
                            selectedItem = map.get(key);
                            break;
                        }
                    }
                }
                isConnection.isPoloniex = true;
                upDateListView(_selectCoinName, getPoloniexItem(selectedItem, _selectCoinName));
            }
                break;

            case IDENTIFIER_USD_TO_KRW:
            {
                //Log.d("lee","krw : " + res);
                try{
                    FileWriter fw = new FileWriter(Common.getPath());
                    fw.write(res);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onFail(String code, String msg, String identifier) {

    }

    ExchangeCoinPrice getBithumbItem(BithumbItem item, String key) {
        if (item != null)
            return getCoinPriceItem(Common.EXCHANGE.BITHUMB, key, item.closing_price, item.average_price);
        else
            return getCoinPriceItem(Common.EXCHANGE.BITHUMB, key, NOT_DATA, NOT_DATA);
    }

    ExchangeCoinPrice getCoinoneItem(CoinoneItem item, String key) {
        if (item != null)
            return getCoinPriceItem(Common.EXCHANGE.COINONE, key, item.last, (double) ((item.high + item.low) / 2.0));
        else
            return getCoinPriceItem(Common.EXCHANGE.COINONE, key, NOT_DATA, NOT_DATA);
    }

    ExchangeCoinPrice getUpbitItem(UpbitItem item, String key) {
        if (item != null)
            return getCoinPriceItem(Common.EXCHANGE.UPBIT, key, (long)item.tradePrice, (double) ((item.highPrice + item.lowPrice) / 2.0));
        else
            return getCoinPriceItem(Common.EXCHANGE.UPBIT, key, NOT_DATA, NOT_DATA);
    }

    ExchangeCoinPrice getPoloniexItem(PoloniexItem item, String key) {
        if (item != null)
            return getCoinPriceItem(Common.EXCHANGE.POLONIEX, key, item.last, 0);
        else
            return getCoinPriceItem(Common.EXCHANGE.POLONIEX, key, NOT_DATA, NOT_DATA);
    }

    ExchangeCoinPrice getCoinPriceItem(Common.EXCHANGE exchange, String coinName, double price, double premium){
        ExchangeCoinPrice item = new ExchangeCoinPrice(exchange, coinName.toUpperCase(), price, premium);
        return item;
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

    void upDateListView(final String coinName, ExchangeCoinPrice item){
        _mapPrice.put(item.exchangeName, item);
        if(isConnection.isComplate()){
            isConnection.setDefault();
            _arrayPrice.clear();

            for(Common.EXCHANGE key: _mapPrice.keySet())
              _arrayPrice.add(_mapPrice.get(key));

            ConnectionManager.Instance().request(Common.CHECK_KRW_URL, "", this, Common.HTTP_TYPE.GET, IDENTIFIER_USD_TO_KRW);

//            _adapterPrice.updateData(_arrayPrice);
//
//            getActivity().runOnUiThread(
//                new Runnable() {
//                    public void run() {
//                        _adapterPrice.notifyDataSetChanged();
//                        _txtSelectedCoin.setText(">"+coinName);
//
//                    }
//                });
//
//            makeCoinNameList();
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
            holder.price.setText(item.price == NOT_DATA ? "-":Common.toNumFormat(item.price));
            holder.premium.setText(item.premium == NOT_DATA?"-":Common.toNumFormat(item.premium));
            holder.averge.setText(item.average_price == NOT_DATA ? "-":Common.toNumFormat(item.average_price));

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
//                holder.isUpbit = view.findViewById(R.id.text_item_is_upbit);
                view.setTag(holder);

            }else{
                holder = (ViewHolder) view.getTag();
            }

            CoinNameData item = array.get(i);
            holder.name.setText(item.name);
            holder.isBithumb.setText(item.isBithumb ? "O":"X");
            holder.isCoinone.setText(item.isCoinone?"O":"X");
//            holder.isUpbit.setText(item.isUpbit?"O":"X");

            return view;
        }

        public void update(ArrayList<CoinNameData>array){
            this.array = array;
        }

        class ViewHolder{
            TextView name;
            TextView isBithumb;
            TextView isCoinone;
//            TextView isUpbit;
        }
    }
}
