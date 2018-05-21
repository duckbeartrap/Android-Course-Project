package com.example.david.dawi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class arrayAdapter extends ArrayAdapter<cards>{
    Context context;

    public arrayAdapter(Context context, int resourceid, List<cards> items){
        super(context,resourceid,items);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        cards card_item = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.tvname);
        TextView age = (TextView)convertView.findViewById(R.id.tvage);
        ImageView image = (ImageView)convertView.findViewById(R.id.ivUserImage);

        name.setText(card_item.getName());
        age.setText(card_item.getAge());
        switch (card_item.getProfileImageUrl()){
            case "default":
                Glide.with(convertView.getContext()).load(R.mipmap.userimage).into(image);
                break;
            default:
                Glide.clear(image);
                Glide.with(convertView.getContext()).load(card_item.getProfileImageUrl()).into(image);
                break;
        }

        return convertView;

    }
}
