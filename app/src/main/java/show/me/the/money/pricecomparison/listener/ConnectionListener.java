package show.me.the.money.pricecomparison.listener;

import show.me.the.money.pricecomparison.Common;

/**
 * Created by KOITT on 2018-01-16.
 */

public interface ConnectionListener {
    void onSuccess(String res, Common.EXCHANGE exchange, String identifier);
    void onFail(String code, String msg, String identifier);
}
