package com.jbckss.koreanrest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuItemView extends LinearLayout {
    TextView rest_name;
    TextView rest_call;
    // TextView rest_add;
    ImageView rest_idx;

    public MenuItemView(Context context) {
        super(context);
        init(context);
    }

    public MenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.menu_item, this, true);

        rest_name = (TextView) findViewById(R.id.textView);
        rest_call = (TextView) findViewById(R.id.textView2);
//        rest_add = (TextView) findViewById(R.id.textView3);
        rest_idx = (ImageView) findViewById(R.id.imageView);
    }

    public void setRest_name(String name) {
        rest_name.setText(name);
    }

    public void setRest_call(String call) {
        rest_call.setText(call);
    }

//    public void setRest_add(String add) {
//        rest_add.setText(add);
//    }

    public void setRest_idx(int idx) {
        rest_idx.setImageResource(idx);
    }
}
