package com.example.david.dawi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        image.setImageResource(R.mipmap.ic_launcher);

        return convertView;

    }
}
