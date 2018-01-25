package show.me.the.money.pricecomparison;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.RemoteViews;

import show.me.the.money.pricecomparison.listener.ConnectionListener;
import show.me.the.money.pricecomparison.network.ConnectionManager;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider implements ConnectionListener {
//    static final String ACTION_CLICK_1 = "CLICK_1";
//    static final String ACTION_CLICK_2 = "CLICK_2";
//    static final String ACTION_CLICK_3 = "CLICK_3";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

//        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);

        Intent intent = new Intent(context, AppWidget.class);
//        intent.setAction(ACTION_CLICK_1);
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        PendingIntent pending1 = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
//        views.setOnClickPendingIntent(R.id.button_1, pending1);
//
//        intent.setAction(ACTION_CLICK_2);
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        PendingIntent pending2 = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
//        views.setOnClickPendingIntent(R.id.button_2, pending2);
//
//        intent.setAction(ACTION_CLICK_3);
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        PendingIntent pending3 = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
//        views.setOnClickPendingIntent(R.id.button_3, pending3);


        /**
         * 레이아웃을 클릭하면 홈페이지 이동
         */
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("show.me.the.money.pricecomparison"));
        Intent intentMove = context.getPackageManager().getLaunchIntentForPackage("show.me.the.money.pricecomparison");
        intentMove.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentMove, 0);
        views.setOnClickPendingIntent(R.id.layout_widget, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();

        if(action != null && action.equals(Common.COINBUTTON_TAG)){
            int tag = intent.getIntExtra(Common.COINBUTTON_TAG, 0);
        }

        // RENEW
//        if (action != null){
//            switch (action){
//                case ACTION_CLICK_1 :
//                    break;
//                case ACTION_CLICK_2:
//                    break;
//                case ACTION_CLICK_3 :
//                    break;
//            }
//
//            int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
//            updateAppWidget(context, AppWidgetManager.getInstance(context), id);   // 버튼이 클릭되면 새로고침 수행
//
//            Log.d("ExampleWidget", "onReceive: CLICK Button");
//            return;
//        }
    }

    @Override
    public void onSuccess(String res, String identifier) {

    }

    @Override
    public void onFail(String code, String msg, String identifier) {

    }
}

