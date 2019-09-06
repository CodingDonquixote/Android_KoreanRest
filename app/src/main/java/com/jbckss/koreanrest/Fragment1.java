package com.jbckss.koreanrest;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class Fragment1 extends Fragment {
FoodAdapter adapter;
MenuListFrag menuListFrag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);
        View view = inflater.inflate(R.layout.fragment1, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.gridView);

        adapter = new FoodAdapter();
        adapter.addItem(new FoodItem("분식", R.drawable.food1));
        adapter.addItem(new FoodItem("중식", R.drawable.food2));
        adapter.addItem(new FoodItem("치킨", R.drawable.food3));
        adapter.addItem(new FoodItem("양식", R.drawable.food4));
        adapter.addItem(new FoodItem("일식", R.drawable.food5));
        adapter.addItem(new FoodItem("한식", R.drawable.food6));
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodItem item = (FoodItem) adapter.getItem(position);
                menuListFrag = new MenuListFrag();

                Bundle args = new Bundle();
                if(item.getName()=="분식"){
                    args.putString("menu", "분식");
                } else if(item.getName()=="중식"){
                    args.putString("menu", "중식");
                } else if(item.getName()=="치킨"){
                    args.putString("menu", "치킨");
                } else if(item.getName()=="양식"){
                    args.putString("menu", "양식");
                } else if(item.getName()=="일식"){
                    args.putString("menu", "일식");
                } else if(item.getName()=="한식"){
                    args.putString("menu", "한식");
                }
                menuListFrag.setArguments(args);
                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, menuListFrag).addToBackStack(null).commit();
            }
        });
        return view;
    }

    class FoodAdapter extends BaseAdapter{
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();

        @Override
        public int getCount() {
            return items.size();
        }
        public void addItem(FoodItem item){
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FoodItemView view = null;
            if(convertView == null){
                view = new FoodItemView(getActivity());
            }else {
                view = (FoodItemView) convertView;
            }

            FoodItem item = items.get(position);
            view.setFoodName(item.getName());
            view.setImage(item.getIdx());

            return view;
        }
    }
}
