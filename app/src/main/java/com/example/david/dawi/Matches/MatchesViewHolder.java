package com.example.david.dawi.Matches;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david.dawi.Chat.ChattingActivity;
import com.example.david.dawi.R;

public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView tvMatchName, tvMatchAge, tvMatchPhone, tvMatchGender, tvMatchId;
    public ImageView ivMatchImage;
    public MatchesViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ivMatchImage = (ImageView) itemView.findViewById(R.id.ivMatchImage);
        tvMatchId = (TextView) itemView.findViewById(R.id.tvMatchId);
        tvMatchName = (TextView)itemView.findViewById(R.id.tvMatchName);
        tvMatchAge = (TextView)itemView.findViewById(R.id.tvMatchAge);
        tvMatchPhone = (TextView)itemView.findViewById(R.id.tvMatchPhone);
        tvMatchGender = (TextView)itemView.findViewById(R.id.tvMatchGender);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ChattingActivity.class);
        Bundle b = new Bundle();
        b.putString("matchId",tvMatchId.getText().toString());
        intent.putExtras(b);
        v.getContext().startActivity(intent);
    }
}
