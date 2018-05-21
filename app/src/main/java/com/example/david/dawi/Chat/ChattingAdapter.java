package com.example.david.dawi.Chat;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.david.dawi.R;

import java.util.List;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingViewHolder> {
    private List<ChattingObject> chatObjectList;
    private Context context;

    public ChattingAdapter(List<ChattingObject> matchesObjects, Context context){
        this.chatObjectList = matchesObjects;
        this.context = context;
    }

    @NonNull
    @Override
    public ChattingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatting,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ChattingViewHolder rcv = new ChattingViewHolder((layoutView));

        return rcv;
    }

    @Override
    public void onBindViewHolder(ChattingViewHolder holder, int position) {
        holder.myMessage.setText(chatObjectList.get(position).getMessage());
        if (chatObjectList.get(position).getCurrentUser()){
            holder.myMessage.setGravity(Gravity.END);
            holder.myMessage.setTextColor(Color.parseColor("#834639"));
            holder.myMessagecontainter.setBackgroundColor(Color.parseColor("#62F13C"));
        }else {
            holder.myMessage.setGravity(Gravity.START);
            holder.myMessage.setTextColor(Color.parseColor("#834639"));
            holder.myMessagecontainter.setBackgroundColor(Color.parseColor("#3CA4F1"));
        }

    }

    @Override
    public int getItemCount() {
        return chatObjectList.size();
    }
}
