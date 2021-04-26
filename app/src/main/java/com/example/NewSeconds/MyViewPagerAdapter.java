package com.example.NewSeconds;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.NewSeconds.dto.Article;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    String images[]={"1","2","3","4","5","6","7"};
    String text[]={"1","2","3","4","5","6","7"};
    public MyViewPagerAdapter(@NonNull FragmentManager fm,Article article) {
        super(fm);
        setCardList(article);
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

    public void setCardList(Article article){
        text[0]=article.getContent1();
        text[1]=article.getContent2();
        text[2]=article.getContent3();
        text[3]=article.getContent4();
        text[4]=article.getContent5();
        text[5]=article.getContent6();
        text[6]=article.getContent7();
        images[0]=article.getImage();
        images[1]=article.getImage();
        images[2]=article.getImage();
        images[3]=article.getImage();
        images[4]=article.getImage();
        images[5]=article.getImage();
        images[6]=article.getImage();
    }
}
