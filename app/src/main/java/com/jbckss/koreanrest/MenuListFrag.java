package com.jbckss.koreanrest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MenuListFrag extends Fragment {

    MenuAdapter adapter;

    SQLiteDatabase database;

    ListView listView;

    SelectRestaurant selectRestaurant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_list, container, false);

        listView = (ListView) view.findViewById(R.id.listView);
        adapter = new MenuAdapter();

        // 해당하는 DB 테이블 데이터를 가져와 Adapter에 넣기!
        if(getArguments() != null){
            String menuName = getArguments().getString("menu", "0");
            String tableName = "";
            if(menuName=="분식"){
                tableName = "bunsik";
                selectData(tableName);
            }else if(menuName=="중식"){
                tableName = "jungsik";
                selectData(tableName);
            }else if(menuName=="치킨"){
                tableName = "chicken";
                selectData(tableName);
            }else if(menuName=="양식"){
                tableName = "westernFood";
                selectData(tableName);
            }else if(menuName=="일식"){
                tableName = "japaneseFood";
                selectData(tableName);
            }else if(menuName=="한식"){
                tableName = "koreanFood";
                selectData(tableName);
            }
        }

        selectRestaurant = new SelectRestaurant();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuItem item = (MenuItem) adapter.getItem(position);

                // selectRetaurant에 정보 입력!!@@@@@@@@@@@@@@@@@@@@@@@
                Bundle bundle = new Bundle(2);
                bundle.putInt("item_idx",item.getRest_idx());

                // 테이블 이름 전달
                String tableName ="";
                switch (item.getRest_category()){
                    case 1:
                        tableName = "bunsik";
                        break;
                    case 2:
                        tableName = "jungsik";
                        break;
                    case 3:
                        tableName = "chicken";
                        break;
                    case 4:
                        tableName = "westernFood";
                        break;
                    case 5:
                        tableName = "japaneseFood";
                        break;
                    case 6:
                        tableName = "koreanFood";
                        break;
                    default:
                        break;
                }
                bundle.putString("tableName", tableName);
                selectRestaurant.setArguments(bundle);

                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, selectRestaurant).addToBackStack(null).commit();
            }
        });

        return view;
    }

    class MenuAdapter extends BaseAdapter {
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(MenuItem item){
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
            MenuItemView view = null;
            if(convertView == null){
                view = new MenuItemView(getActivity());
            }else {
                view = (MenuItemView) convertView;
            }

            MenuItem item = items.get(position);

            view.setRest_name(item.getRest_name());
            view.setRest_call(item.getRest_call());
            view.setRest_idx(item.getItem_imgIdx());

            return view;
        }
    }

    // 데이터 가져오기
    public void selectData(String tableName){
        database = getActivity().openOrCreateDatabase("vietnam.db", android.content.Context.MODE_PRIVATE, null);
        if(database!=null){
            String sql = "select rest_idx, rest_name, rest_call, rest_add, rest_like, rest_category from "+tableName;
            Cursor cursor = database.rawQuery(sql, null);

            for(int i=0; i<cursor.getCount();i++){
                cursor.moveToNext();
                int rest_idx = cursor.getInt(0);
                String rest_name = cursor.getString(1);
                String rest_call = cursor.getString(2);
                String rest_add = cursor.getString(3);
                int rest_like = cursor.getInt(4);
                int rest_category = cursor.getInt(5);

                String idx_str = tableName+rest_idx;
                if(tableName=="westernFood"){
                    idx_str = "westernfood"+rest_idx;
                }else if(tableName=="koreanFood"){
                    idx_str = "koreanfood"+rest_idx;
                }else if(tableName=="japaneseFood"){
                    idx_str = "japanesefood"+rest_idx;
                }
                int img_idx = getResources().getIdentifier(idx_str, "drawable", getActivity().getPackageName());

                adapter.addItem(new MenuItem(rest_idx, rest_name, rest_call, rest_add, rest_like, rest_category, img_idx));
            }
            listView.setAdapter(adapter);
            cursor.close();
        }
    }
}
