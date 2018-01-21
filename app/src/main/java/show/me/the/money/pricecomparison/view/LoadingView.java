package show.me.the.money.pricecomparison.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatDialog;

import show.me.the.money.pricecomparison.R;

/**
 * Created by jeounglee on 2018. 1. 21..
 */

public class LoadingView {
    static LoadingView _instance = null;
    AppCompatDialog _progressDialog = null;

    public static LoadingView Instance(){
        if(_instance == null) {
            _instance = new LoadingView();
        }
        return _instance;
    }

    public void show(Context context){
        if (_progressDialog != null && _progressDialog.isShowing()) {
            _progressDialog.show();
        } else {
            _progressDialog = new AppCompatDialog(context);
            _progressDialog.setContentView(R.layout.loading_view);
            _progressDialog.setCancelable(false);
            _progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            _progressDialog.show();
        }
    }

    public void hide(){
        if(_progressDialog != null && _progressDialog.isShowing() ){
            _progressDialog.hide();
            _progressDialog.dismiss();
        }
    }
}
