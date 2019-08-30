package com.jbckss.koreanrest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import com.jbckss.koreanrest.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class Fragment2 extends Fragment {
    ImageView imageView;
    TextView textView1;
    TextView textView2;

    SQLiteDatabase database;

    LinearLayout randomItem;

    SelectRestaurant selectRestaurant;

    int selectedItem;
    String tableName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment2, container, false);

        imageView = (ImageView) view.findViewById(R.id.rd_image);
        textView1 = (TextView) view.findViewById(R.id.rd_text1);
        textView2 = (TextView) view.findViewById(R.id.rd_text2);

        randomItem = view.findViewById(R.id.random_item);
        randomItem.setVisibility(View.INVISIBLE);

        Button button = (Button) view.findViewById(R.id.rd_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int rand = random.nextInt(64);
                int tableInt = -1;
                if(rand<=7)
                    tableInt=0;
                else if(rand>7&&rand<=16)
                    tableInt=1;
                else if(rand>16&&rand<=27)
                    tableInt=2;
                else if(rand>27&&rand<=29)
                    tableInt=3;
                else if(rand>29&&rand<=35)
                    tableInt=4;
                else if(rand>35&&rand<=64)
                    tableInt=5;
                tableName = "";
                int size = 0;
                if(tableInt == 0){
                    tableName = "bunsik";
                    size = 7;
                }else if(tableInt == 1){
                    tableName = "jungsik";
                    size = 9;
                }else if (tableInt == 2){
                    tableName = "chicken";
                    size = 11;
                }else if (tableInt == 3){
                    tableName = "westernFood";
                    size = 2;
                }else if (tableInt == 4){
                    tableName = "japaneseFood";
                    size = 6;
                }else if (tableInt == 5){
                    tableName = "koreanFood";
                    size = 29;
                }else{

                }
                tableInt = random.nextInt(size);
                MenuItem menuItem = selectData(tableName, tableInt);
                // String result [] = selectData(tableName, rand);
                selectedItem = menuItem.getRest_idx();
                if(menuItem != null){
                    randomItem.setVisibility(View.VISIBLE);
                    textView1.setText(menuItem.getRest_name());
                    textView2.setText(menuItem.getRest_call());

                    imageView.setImageResource(menuItem.getItem_imgIdx());
                }
            }
        });

        randomItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRestaurant = new SelectRestaurant();

                Bundle bundle = new Bundle(2);
                bundle.putInt("item_idx",selectedItem);
                bundle.putString("tableName",tableName);

                selectRestaurant.setArguments(bundle);

                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, selectRestaurant).addToBackStack(null).commit();
            }
        });

        return view;
    }

    public MenuItem selectData(String tableName, int randomNum){
        database = getActivity().openOrCreateDatabase("vietnam.db", Context.MODE_PRIVATE, null);
        if(database != null){
            String sql= "select rest_idx, rest_name, rest_call, rest_add, rest_like, rest_category from "+tableName;
            Cursor cursor = database.rawQuery(sql, null);
            cursor.moveToPosition(randomNum);

            int rest_idx = cursor.getInt(0);
            String rest_name = cursor.getString(1);
            String rest_call = cursor.getString(2);
            String rest_add = cursor.getString(3);
            int rest_like = cursor.getInt(4);
            int rest_category = cursor.getInt(5);

            if(tableName=="westernFood")
                tableName="westernfood";
            else if(tableName=="japaneseFood")
                tableName="japanesefood";
            else if(tableName=="koreanFood")
                tableName="koreanfood";

            String idx_str = tableName+rest_idx;
            int img_idx = getResources().getIdentifier(idx_str, "drawable", getActivity().getPackageName());

            MenuItem menuItem = new MenuItem(rest_idx, rest_name, rest_call, rest_add, rest_like, rest_category, img_idx);

            cursor.close();

            return menuItem;
        }
        else{
            return null;
        }
    }
}
