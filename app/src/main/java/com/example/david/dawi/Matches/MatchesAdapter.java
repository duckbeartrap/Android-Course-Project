package com.example.david.dawi.Matches;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.david.dawi.R;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewHolder> {
    private List<MatchesObject> matchesObjectList;
    private Context context;

    public MatchesAdapter(List<MatchesObject> matchesObjects, Context context){
        this.matchesObjectList = matchesObjects;
        this.context = context;
    }

    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchesViewHolder rcv = new MatchesViewHolder((layoutView));

        return rcv;
    }

    @Override
    public void onBindViewHolder( MatchesViewHolder holder, int position) {
        holder.tvMatchId.setText(matchesObjectList.get(position).getUserId());
        holder.tvMatchName.setText(matchesObjectList.get(position).getName());
        holder.tvMatchAge.setText(matchesObjectList.get(position).getAge());
        holder.tvMatchPhone.setText(matchesObjectList.get(position).getPhone());
        holder.tvMatchGender.setText(matchesObjectList.get(position).getGender());
        if (!matchesObjectList.get(position).getImage().equals("default")){
            Glide.with(context).load(matchesObjectList.get(position).getImage()).into(holder.ivMatchImage);
        }

    }

    @Override
    public int getItemCount() {
        return matchesObjectList.size();
    }
}
