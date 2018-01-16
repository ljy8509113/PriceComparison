package show.me.the.money.pricecomparison.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import show.me.the.money.pricecomparison.R;

/**
 * Created by KOITT on 2018-01-15.
 */

public class CustomScrollView extends HorizontalScrollView {
    LinearLayout _layoutContent;

    public CustomScrollView(Context context) {
        super(context);
        initView();
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.custom_scrollview, this, false);
        addView(v);

        _layoutContent = v.findViewById(R.id.layout_contents);
    }

    public void setButton(){

    }

}
