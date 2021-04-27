package com.example.NewSeconds;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.NewSeconds.dto.Article;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    String images[]={"1","2","3","4","5","6","7"};
    String text[]={"1","2","3","4","5","6","7"};
    public MyViewPagerAdapter(@NonNull FragmentManager fm,Cursor cursor) {
        super(fm);
        setCardList(cursor);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ItemFragment.newInstance(images[position],text[position]);
    }

    @Override
    public int getCount() {
        return 7;
    }

    public void setCardList(Cursor cursor){
        while(cursor.moveToNext()){
            text[0]=cursor.getString(2);
            text[1]=cursor.getString(3);
            text[2]=cursor.getString(4);
            text[3]=cursor.getString(5);
            text[4]=cursor.getString(6);
            text[5]=cursor.getString(7);
            text[6]=cursor.getString(8);
            images[0]=cursor.getString(12);
            images[1]=cursor.getString(12);
            images[2]=cursor.getString(12);
            images[3]=cursor.getString(12);
            images[4]=cursor.getString(12);
            images[5]=cursor.getString(12);
            images[6]=cursor.getString(12);
        }
//        text[0]=article.getContent1();
//        text[1]=article.getContent2();
//        text[2]=article.getContent3();
//        text[3]=article.getContent4();
//        text[4]=article.getContent5();
//        text[5]=article.getContent6();
//        text[6]=article.getContent7();
//        images[0]=article.getImage();
//        images[1]=article.getImage();
//        images[2]=article.getImage();
//        images[3]=article.getImage();
//        images[4]=article.getImage();
//        images[5]=article.getImage();
//        images[6]=article.getImage();
    }
}
