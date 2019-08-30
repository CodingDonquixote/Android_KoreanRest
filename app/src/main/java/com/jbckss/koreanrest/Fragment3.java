package com.jbckss.koreanrest;

import android.content.Context;
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

public class Fragment3 extends Fragment {

    ListView listView;

    SQLiteDatabase database;

    MenuAdapter adapter;

    SelectRestaurant selectRestaurant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);

        listView = (ListView) view.findViewById(R.id.listView);
        adapter = new MenuAdapter();

        int [][] selectLikeResult = selectLike();
        for(int i=0;i<100;i++){
            if(selectLikeResult[i][0]==-1){
                break;
            }
            selectData(selectLikeResult[i][0], selectLikeResult[i][1]);
        }


        selectRestaurant = new SelectRestaurant();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuItem item = (MenuItem) adapter.getItem(position);

                Bundle bundle = new Bundle(2);
                bundle.putInt("item_idx",item.getRest_idx());

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
                bundle.putString("tableName",tableName);
                selectRestaurant.setArguments(bundle);

                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, selectRestaurant).addToBackStack(null).commit();
            }
        });

        return view;
    }

    private int[][] selectLike(){
        int[][] selectLikeResult = new int [50][2];
        database = getActivity().openOrCreateDatabase("like.db", Context.MODE_PRIVATE, null);
        if(database!=null){
            String sql = "select rest_idx, rest_category from like_status where rest_like = 2";
            Cursor cursor = database.rawQuery(sql, null);

            int i;
            for(i=0;i<cursor.getCount();i++){
                cursor.moveToNext();
                int rest_idx = cursor.getInt(0);
                int rest_category = cursor.getInt(1);

                selectLikeResult[i][0] = rest_idx;
                selectLikeResult[i][1] = rest_category;
            }
            selectLikeResult[i][0] = -1;
            selectLikeResult[i][1] = -1;
            return selectLikeResult;
        }
        return selectLikeResult;
    }

    class MenuAdapter extends BaseAdapter{
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
//            view.setRest_add(item.getRest_add());
            view.setRest_idx(item.getItem_imgIdx());

            return view;
        }
    }

    // 데이터 가져오기
    public void selectData(int rest_idx, int rest_category){
        database = getActivity().openOrCreateDatabase("vietnam.db", Context.MODE_PRIVATE, null);

        if(database!=null){

            String tableName = "";
            if(rest_category==1)
                tableName = "bunsik";
            else if(rest_category==2)
                tableName = "jungsik";
            else if(rest_category==3)
                tableName = "chicken";
            else if(rest_category==4)
                tableName = "westerFood";
            else if(rest_category==5)
                tableName = "japaneseFood";
            else if(rest_category==6)
                tableName = "koreanFood";

            String sql = "select rest_name, rest_call, rest_add, rest_like from "+tableName+" where rest_category ="+rest_category+" and rest_idx = "+rest_idx;

            Cursor cursor = database.rawQuery(sql, null);

            cursor.moveToNext();
            //int rest_idx = cursor.getInt(0);
            String rest_name = cursor.getString(0);
            String rest_call = cursor.getString(1);
            String rest_add = cursor.getString(2);
            int rest_like = cursor.getInt(3);
            //int rest_category = cursor.getInt(5);
            if(tableName=="westernFood")
                tableName="westernfood";
            else if(tableName=="japaneseFood")
                tableName="japanesefood";
            else if(tableName=="koreanFood")
                tableName="koreanfood";

            String idx_str = tableName+rest_idx;
            int img_idx = getResources().getIdentifier(idx_str, "drawable", getActivity().getPackageName());

            adapter.addItem(new MenuItem(rest_idx, rest_name, rest_call, rest_add, rest_like, rest_category, img_idx));

            listView.setAdapter(adapter);
            cursor.close();
        }


    }

}
