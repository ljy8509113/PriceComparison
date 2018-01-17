package show.me.the.money.pricecomparison.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import show.me.the.money.pricecomparison.Common;
import show.me.the.money.pricecomparison.R;
import show.me.the.money.pricecomparison.listener.ConnectionListener;
import show.me.the.money.pricecomparison.network.ConnectionManager;

/**
 * Created by KOITT on 2018-01-15.
 */

public class PriceFragment extends Fragment implements ConnectionListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //String url, String params, Common.EXCHANGE exchange, ConnectionListener listener, Common.HTTP_TYPE type){
        ConnectionManager.Instance().request(Common.BITHUMB_PUBLIC_URL, "ticker/ALL", Common.EXCHANGE.BITHUMB, this, Common.HTTP_TYPE.GET );
        return inflater.inflate(R.layout.fragment_price, container, false);

    }

    @Override
    public void onSuccess(String res, Common.EXCHANGE exchange) {

    }

    @Override
    public void onFail(String code, String msg) {

    }
}
