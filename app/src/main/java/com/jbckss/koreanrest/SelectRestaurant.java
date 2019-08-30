package com.jbckss.koreanrest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectRestaurant extends Fragment {

    RestMenu restMenu;
    RestInfo restInfo;

    SQLiteDatabase database;

    TextView textView;

    ImageView likeImage;

    String tableName;
    int rest_idx;
    int rest_cate;
    String rest_add;
    String rest_cal;
    String restName;
    double geoV_go;
    double geoV1_go;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_restaurant, container, false);

        restMenu = new RestMenu();
        restInfo = new RestInfo();

        rest_idx = getArguments().getInt("item_idx");
        tableName = getArguments().getString("tableName");

        Bundle bundle = new Bundle(2);
        bundle.putInt("item_idx",rest_idx);
        bundle.putString("tableName", tableName);
        restMenu.setArguments(bundle);

        ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.selectContainer, restMenu).commit();
        TabLayout tabs = (TabLayout) view.findViewById(R.id.menuInfoTabs);
        tabs.addTab(tabs.newTab().setText("메뉴"));
        tabs.addTab(tabs.newTab().setText("정보"));

        textView = view.findViewById(R.id.textView3);

        String result[] = selectData(rest_idx, tableName);
        textView.setText(result[0]);
        rest_cate = Integer.parseInt(result[2]);
        rest_add = result[3];
        rest_cal = result[4];
        restName = result[0];
        geoV_go = Double.parseDouble(result[5]);
        geoV1_go = Double.parseDouble(result[6]);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position==0){
                    selected = restMenu;
                    Bundle bundle = new Bundle(2);
                    bundle.putInt("item_idx",rest_idx);
                    bundle.putString("tableName", tableName);
                    restMenu.setArguments(bundle);
                } else if(position == 1){
                    selected = restInfo;
                    Bundle bundle = new Bundle(5);
                    bundle.putString("no1", restName);
                    bundle.putString("no3", rest_cal);
                    bundle.putString("no4", rest_add);
                    bundle.putDouble("geo_v", geoV_go);
                    bundle.putDouble("geo_v1", geoV1_go);

                    restInfo.setArguments(bundle);
                }
                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.selectContainer, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        likeImage = view.findViewById(R.id.likeImage);
        final int rest_idx_selected = Integer.parseInt(result[1]);
        final int rest_category_selected = Integer.parseInt(result[2]);
        // 즐겨찾기 상태 나타내기
        int likeStatus = selectLike(rest_idx_selected, rest_category_selected);
        if(likeStatus == 1) {
            likeImage.setImageResource(R.drawable.like_bf);
        }else if(likeStatus == 2){
            likeImage.setImageResource(R.drawable.like_af);
        }
        likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int likeStatus = insertLike(rest_idx_selected, rest_category_selected);
                if(likeStatus==1){
                    likeImage.setImageResource(R.drawable.like_bf);
                }else if(likeStatus==2){
                    likeImage.setImageResource(R.drawable.like_af);
                }
            }
        });

        return view;
    }

    public String[] selectData(int position, String tableName){
        database = getActivity().openOrCreateDatabase("vietnam.db", Context.MODE_PRIVATE, null);
        if(database != null){
            String sql= "select rest_name, rest_idx, rest_category, rest_add, rest_call, geo_v, geo_v1 from "+tableName;
            Cursor cursor = database.rawQuery(sql, null);
            cursor.moveToPosition(position-1);
            String name = cursor.getString(0);
            int rest_idx = cursor.getInt(1);
            String rest_idxStr = String.valueOf(rest_idx);
            int rest_category = cursor.getInt(2);
            String rest_categoryStr = String.valueOf(rest_category);
            String rest_address = cursor.getString(3);
            String rest_call = cursor.getString(4);
            double geoV = cursor.getDouble(5);
            Log.d("SelectRestaurant클래스", "selectData 메소드, geoV : "+geoV);
            String geoV_string = String.valueOf(geoV);
            double geoV1 = cursor.getDouble(6);
            String geoV1_string = String.valueOf(geoV1);
            cursor.close();
            String result[] = new String[7];
            result[0] = name;
            result[1] = rest_idxStr;
            result[2] = rest_categoryStr;
            result[3] = rest_address;
            result[4] = rest_call;
            result[5] = geoV_string;
            result[6] = geoV1_string;
            return result;
        }else{
            return null;
        }
    }

    public int selectLike(int rest_idx_selected, int rest_category_selected){
        database = getActivity().openOrCreateDatabase("like.db", Context.MODE_PRIVATE, null);
        if(database != null){
            String sql = "select rest_like from like_status where rest_idx = "+rest_idx_selected+" and rest_category = "+rest_category_selected;
            Cursor cursor = database.rawQuery(sql, null);
            cursor.moveToNext();
            int result_selectLike = cursor.getInt(0);
            cursor.close();
            return result_selectLike;
        }else{
            return 0;
        }
    }

    public int insertLike(int rest_idx_selected, int rest_category_selected){
        database = getActivity().openOrCreateDatabase("like.db", Context.MODE_PRIVATE, null);
        if(database!=null){
            String sql= "select rest_like from like_status where rest_idx = "+rest_idx_selected+ " and rest_category = "+rest_category_selected;
            Cursor cursor = database.rawQuery(sql, null);
            cursor.moveToNext();
            int likeStatus = cursor.getInt(0);

            if(likeStatus == 1){
                sql = "update like_status set rest_like = 2 where rest_idx = "+rest_idx_selected+ " and rest_category = "+rest_category_selected;
                Toast.makeText(getActivity(), "좋아요에 등록되었습니다.", Toast.LENGTH_SHORT).show();
                database.execSQL(sql);
                cursor.close();
                return 2;
            }else if (likeStatus == 2){
                sql = "update like_status set rest_like = 1 where rest_idx = "+rest_idx_selected+ " and rest_category = "+rest_category_selected;
                Toast.makeText(getActivity(), "좋아요가 취소되었습니다.", Toast.LENGTH_SHORT).show();
                database.execSQL(sql);
                cursor.close();
                return 1;
            }
        }else{
            Toast.makeText(getActivity(), "데이터 베이스 오픈이 안됨", Toast.LENGTH_LONG).show();
            return 0;
        }
        return 0;
    }
}
