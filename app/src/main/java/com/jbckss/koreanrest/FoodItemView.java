package com.jbckss.koreanrest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FoodItemView extends LinearLayout {
    ImageView imageView;
    TextView foodName;

    public FoodItemView(Context context) {
        super(context);
        init(context);
    }

    public FoodItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.food_item, this, true);

        imageView = (ImageView) findViewById(R.id.imageView);
        foodName = (TextView) findViewById(R.id.foodName);

    }
        public void setFoodName (String name){ foodName.setText(name); }
        public void setImage(int resId) {imageView.setImageResource(resId);}
}
