package com.jbckss.koreanrest;

import android.content.res.AssetManager;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 데이터를 넣어주자
        try{
            boolean result = isCheckDB();
            Log.d("MiniApp", "DB Check="+result);
            if(!result){
                copyDB();
                //if(isCheckDB3()){
                //    deleteDatabase("vietnam2.db");
                //}
                Log.d("카피", "DB복사됨");
            }
            if(!isCheckDB2()){
                copyDB2();
                Log.d("카피", "likeDB가 카피됨");
            }
        }catch (Exception e){}

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).addToBackStack(null).commit();

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("식당"));
        tabs.addTab(tabs.newTab().setText("랜덤 뽑기"));
        tabs.addTab(tabs.newTab().setText("좋아요"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;
                if(position == 0){
                    selected = fragment1;
                } else if(position == 1){
                    selected = fragment2;
                } else if(position == 2){
                    selected = fragment3;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public boolean isCheckDB(){
        String filePath = "/data/data/"+ getPackageName() + "/databases/" + "vietnam.db";

        File file = new File(filePath);

        if (file.exists()) {
            return true;
        }
        return false;
    }

    public boolean isCheckDB2(){
        String filePath = "/data/data/"+ getPackageName() + "/databases/" + "like.db";

        File file = new File(filePath);

        if (file.exists()) {
            return true;
        }
        return false;
    }

    public boolean isCheckDB3(){
        String filePath = "/data/data/"+ getPackageName() + "/databases/" + "vietnam2.db";

        File file = new File(filePath);

        if (file.exists()) {
            return true;
        }
        return false;
    }

    public void copyDB(){
        Log.d("Working","copyDB");
        AssetManager manager = getAssets();
        String folderPath = "/data/data/"+ getPackageName() + "/databases";
        String filePath = "/data/data/"+ getPackageName() + "/databases/" + "vietnam.db";
        File folder = new File(folderPath);
        File file = new File(filePath);

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try{
            InputStream is = manager.open("db/" + "vietnam.db");
            BufferedInputStream bis = new BufferedInputStream(is);

            if(folder.exists()){
            }else{
                folder.mkdirs();
            }

            if(file.exists()){
                file.delete();
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            int read = -1;
            byte[] buffer = new byte[1024];
            while((read=bis.read(buffer, 0, 1024))!= -1){
                bos.write(buffer, 0, read);
            }

            bos.flush();
            bos.close();
            bis.close();
            is.close();
        }catch (IOException e){
            Log.e("errorMessage : ", e.getMessage());
        }
    }

    public void copyDB2(){
        Log.d("Working","likeDB복사");
        AssetManager manager = getAssets();
        String folderPath = "/data/data/"+ getPackageName() + "/databases";
        String filePath = "/data/data/"+ getPackageName() + "/databases/" + "like.db";
        File folder = new File(folderPath);
        File file = new File(filePath);

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try{
            InputStream is = manager.open("db/" + "like.db");
            BufferedInputStream bis = new BufferedInputStream(is);

            if(folder.exists()){
            }else{
                folder.mkdirs();
            }

            if(file.exists()){
                file.delete();
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            int read = -1;
            byte[] buffer = new byte[1024];
            while((read=bis.read(buffer, 0, 1024))!= -1){
                bos.write(buffer, 0, read);
            }

            bos.flush();
            bos.close();
            bis.close();
            is.close();
        }catch (IOException e){
            Log.e("errorMessage : ", e.getMessage());
        }
    }
}
