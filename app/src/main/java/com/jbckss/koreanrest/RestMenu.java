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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RestMenu extends Fragment {

    MenuDetailAdapter menuDetailAdapter;
    SQLiteDatabase database;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rest_menu, container, false);

        listView = (ListView) view.findViewById(R.id.listView3);
        menuDetailAdapter = new MenuDetailAdapter();

        // 해당하는 DB 테이블 데이터를 가져와 Adapter에 넣기!
        if(getArguments() != null){
            int rest_idx = getArguments().getInt("item_idx", 0);
            String menuName = getArguments().getString("tableName", "0");
            Toast.makeText(view.getContext(), "넘어온 값 : "+ rest_idx, Toast.LENGTH_LONG).show();
            selectData(menuName, rest_idx);
        }

        return view;
    }

    class MenuDetailAdapter extends BaseAdapter {
        ArrayList<MenuDetailItem> items = new ArrayList<MenuDetailItem>();

        @Override
        public int getCount() {return items.size();}

        public void addItem(MenuDetailItem item){items.add(item);}

        @Override
        public Object getItem(int position) {return items.get(position);}

        @Override
        public long getItemId(int position) {return position;}

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MenuDetailItemView view = null;
            if(convertView == null){
                view = new MenuDetailItemView(getActivity());
            }else {
                view = (MenuDetailItemView) convertView;
            }

            MenuDetailItem item = items.get(position);

            view.setMenu_name(item.getMenu_name());
            view.setMenu_price(item.getMenu_price());

            return view;
        }
    }

    // 데이터 가져오기
    public void selectData(String tableName, int item_idx){
        database = getActivity().openOrCreateDatabase("vietnam.db", android.content.Context.MODE_PRIVATE, null);
        if(database!=null){
            String sql = "select rest_idx, menu_name, menu_price from "+tableName+"_menu where rest_idx = "+item_idx;
            Cursor cursor = database.rawQuery(sql, null);

            for(int i=0; i<cursor.getCount();i++){
                cursor.moveToNext();
                int rest_idx = cursor.getInt(0);
                String menu_name = cursor.getString(1);
                String menu_price = cursor.getString(2);

                menuDetailAdapter.addItem(new MenuDetailItem(rest_idx, menu_name, menu_price));
            }
            listView.setAdapter(menuDetailAdapter);
            cursor.close();
        }
    }
}
