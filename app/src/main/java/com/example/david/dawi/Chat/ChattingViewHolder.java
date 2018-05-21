package com.example.david.dawi.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.david.dawi.R;

public class ChattingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView myMessage;
    public RelativeLayout myMessagecontainter;

    public ChattingViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        myMessage = itemView.findViewById(R.id.tvMessage);
        myMessagecontainter = itemView.findViewById(R.id.Messagecontainter);

    }

    @Override
    public void onClick(View v) {
    }
}
