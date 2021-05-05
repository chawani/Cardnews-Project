package com.example.NewSeconds;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.NewSeconds.dto.CardViewItemDTO;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<CardViewItemDTO> cardViewItemDTOS=new ArrayList<>();
    public MyRecyclerViewAdapter(ArrayList<CardViewItemDTO> card){
        cardViewItemDTOS=card;
//        cardViewItemDTOS.add(new CardViewItemDTO(R.drawable.image_1,"제목1"));
//        cardViewItemDTOS.add(new CardViewItemDTO(R.drawable.image_2,"제목2"));
//        cardViewItemDTOS.add(new CardViewItemDTO(R.drawable.image_3,"제목3"));
//        cardViewItemDTOS.add(new CardViewItemDTO(R.drawable.image_1,"제목1"));
//        cardViewItemDTOS.add(new CardViewItemDTO(R.drawable.image_2,"제목2"));
//        cardViewItemDTOS.add(new CardViewItemDTO(R.drawable.image_3,"제목3"));
//        cardViewItemDTOS.add(new CardViewItemDTO(R.drawable.image_1,"제목1"));
//        cardViewItemDTOS.add(new CardViewItemDTO(R.drawable.image_2,"제목2"));
//        cardViewItemDTOS.add(new CardViewItemDTO(R.drawable.image_3,"제목3"));
//        cardViewItemDTOS.add(new CardViewItemDTO(R.drawable.image_1,"제목1"));
//        cardViewItemDTOS.add(new CardViewItemDTO(R.drawable.image_2,"제목2"));
//        cardViewItemDTOS.add(new CardViewItemDTO(R.drawable.image_3,"제목3"));
//        cardViewItemDTOS.add(new CardViewItemDTO(R.drawable.image_1,"제목1"));
//        cardViewItemDTOS.add(new CardViewItemDTO(R.drawable.image_2,"제목2"));
//        cardViewItemDTOS.add(new CardViewItemDTO(R.drawable.image_3,"제목3"));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item,parent,false);
        StdViewHolder vh = new StdViewHolder(view);
        return new RowCell(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(cardViewItemDTOS.get(position).imageview).into(((RowCell)holder).imageView);
//        ((RowCell)holder).imageView.setImageResource(cardViewItemDTOS.get(position).imageview);
        ((RowCell)holder).title.setText(cardViewItemDTOS.get(position).title);
    }

    @Override
    public int getItemCount() {
        return cardViewItemDTOS.size();
    }

    private class RowCell extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView title;

        public RowCell(View view){
            super(view);
            imageView=(ImageView)view.findViewById(R.id.cardview_imageview);
            title=(TextView)view.findViewById(R.id.cardview_title);
        }
    }

    public class StdViewHolder extends RecyclerView.ViewHolder {

        public StdViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView t=(TextView)v.findViewById(R.id.cardview_title);
                    String selection = t.getText().toString();
                    String select_sql="select * from article where title='"+selection+"'";
                    Intent intent=new Intent(v.getContext(), FragmentActivity.class);
                    intent.putExtra("select_sql",select_sql);
                    intent.putExtra("selection",selection);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
