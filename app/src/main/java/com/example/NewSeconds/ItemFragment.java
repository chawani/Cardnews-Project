package com.example.NewSeconds;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ItemFragment extends Fragment {
    public static ItemFragment newInstance(String position, String position2){
        Bundle args=new Bundle();
        args.putString("position",position);
        args.putString("position2",position2);
        ItemFragment fragment=new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.item,container,false);
        ImageView imageView=(ImageView)view.findViewById(R.id.item_imageview);
//        imageView.setImageResource(getArguments().getInt("position"));
//        imageView.setImageBitmap(getImageFromURL(getArguments().getString("position")));
        Glide.with(this).load(getArguments().getString("position")).into(imageView);
        imageView.setAlpha(50);

        TextView textView=(TextView)view.findViewById(R.id.item_textview);
        textView.setText(getArguments().getString("position2"));

        return view;
    }

    public static Bitmap getImageFromURL(String imageURL){
        Bitmap imgBitmap = null;
        HttpURLConnection conn = null;
        BufferedInputStream bis = null;
        try
        {
            URL url = new URL(imageURL);
            conn = (HttpURLConnection)url.openConnection();
            conn.connect();

            int nSize = conn.getContentLength();
            bis = new BufferedInputStream(conn.getInputStream(), nSize);
            imgBitmap = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e){
            e.printStackTrace();
        } finally{
            if(bis != null) {
                try {bis.close();} catch (IOException e) {}
            }
            if(conn != null ) {
                conn.disconnect();
            }
        }

        return imgBitmap;
    }
}
