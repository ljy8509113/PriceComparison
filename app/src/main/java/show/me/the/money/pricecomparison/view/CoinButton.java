package show.me.the.money.pricecomparison.view;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import show.me.the.money.pricecomparison.Common;

/**
 * Created by KOITT on 2018-01-15.
 */

public class CoinButton extends AppCompatButton {
    public int _tag = -1;
    public CoinButton(Context context) {
        super(context);
    }

    public CoinButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoinButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PendingIntent getPendingIntent(Intent intent, int appWidgetId, Context context){
        intent.setAction(Common.COINBUTTON_TAG);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putExtra(Common.COINBUTTON_TAG, _tag);
        PendingIntent pending = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);

        return pending;
    }

}
