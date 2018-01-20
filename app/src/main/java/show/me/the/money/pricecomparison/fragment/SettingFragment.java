package show.me.the.money.pricecomparison.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import show.me.the.money.pricecomparison.Common;
import show.me.the.money.pricecomparison.R;

/**
 * Created by KOITT on 2018-01-15.
 */

public class SettingFragment extends Fragment {
    RadioGroup _radioGroup;
    ListView _listViewCoin;
    TextView _selecedCoin;
    CoinListAdapter _adapter;
    ArrayList<CoinItem> _arrayCoinItem = new ArrayList<>();
    LinearLayout _layoutBottom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);

        _radioGroup = v.findViewById(R.id.radio_group);
        _listViewCoin = v.findViewById(R.id.list_select_auto);
        _selecedCoin = v.findViewById(R.id.text_select_coin);
        _layoutBottom = v.findViewById(R.id.layout_bottom);

        _adapter = new CoinListAdapter(_arrayCoinItem);
        _listViewCoin.setAdapter(_adapter);

        SharedPreferences pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        Common.EXCHANGE selectedExchange = Common.EXCHANGE.getExchange(pref.getString(Common.EXCHANGE_KEY, Common.EXCHANGE.BITHUMB.name()));

        for(int i=0; i<Common.EXCHANGE.values().length;i ++){
            if(selectedExchange == Common.EXCHANGE.values()[i]){
                ((RadioButton)_radioGroup.getChildAt(i)).setChecked(true);
            }
        }

        _layoutBottom.setVisibility(View.GONE);

        return v;
    }

    View.OnClickListener listItemClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int index = Integer.parseInt(view.getTag().toString());
                for(int x=0; x<_arrayCoinItem.size(); x++) {
                    CheckBox btn = _listViewCoin.findViewWithTag(x + 1);
                    if (x == index - 1) {
                        _arrayCoinItem.get(x).isSelected = true;
                        btn.setChecked(true);
                    } else {
                        _arrayCoinItem.get(x).isSelected = false;
                        btn.setChecked(false);
                    }
                }
                _adapter.update(_arrayCoinItem);
                _adapter.notifyDataSetChanged();
            }
        };

    class CoinListAdapter extends BaseAdapter{

        ArrayList<CoinItem> _array = new ArrayList<>();
        public CoinListAdapter(ArrayList<CoinItem> array){
            _array = array;
        }

        @Override
        public int getCount() {
            return _array.size();
        }

        @Override
        public Object getItem(int i) {
            return _array.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, final ViewGroup viewGroup) {
            ViewHolder holder = null;
            if(view == null){
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.auto_coin_item, viewGroup,false);

                holder = new ViewHolder();
                holder.textTitle = (TextView)view.findViewById(R.id.text_item_coin_name);
                holder.checkButton = (CheckBox) view.findViewById(R.id.check_item);
                holder.checkButton.setTag(i+1);
                holder.checkButton.setOnClickListener(listItemClickListener);

                view.setTag(holder);
            }else{
                holder = (ViewHolder)view.getTag();
            }

            holder.textTitle.setText(_array.get(i).title);
            holder.checkButton.setChecked(_array.get(i).isSelected);

            return view;
        }

        public void update(ArrayList<CoinItem> array){
            _array = array;
        }

        class ViewHolder{
            TextView textTitle;
            CheckBox checkButton;
        }
    }

    class CoinItem{
        String title;
        boolean isSelected = false;

        public CoinItem(String title, boolean isSelected){
            this.title =title;
            this.isSelected = isSelected;
        }
    }

}
