package com.jbckss.koreanrest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuDetailItemView extends LinearLayout {
    TextView menu_name;
    TextView menu_price;

    public MenuDetailItemView(Context context) {
        super(context);
        init(context);
    }

    public MenuDetailItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.menu_detail, this, true);

        menu_name = (TextView) findViewById(R.id.textView);
        menu_price = (TextView) findViewById(R.id.textView2);
    }

    public void setMenu_name(String name) {
        menu_name.setText(name);
    }

    public void setMenu_price(String call) {
        menu_price.setText(call);
    }
}
