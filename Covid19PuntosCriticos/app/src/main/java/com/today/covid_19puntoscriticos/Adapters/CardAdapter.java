package com.today.covid_19puntoscriticos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.today.covid_19puntoscriticos.Model.Pages;
import com.today.covid_19puntoscriticos.R;

import java.util.List;

public class CardAdapter extends BaseAdapter {
    private List<Pages> list;
    private Context c;

    public CardAdapter(List<Pages> list, Context c) {
        this.list = list;
        this.c = c;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){

            LayoutInflater layoutInflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.item_home_list, null);

        }

        TextView description = (TextView)convertView.findViewById(R.id.text_info);
        ImageView img = (ImageView) convertView.findViewById(R.id.img_carview);




        String EDteamImage = list.get(position).getImg_url();
        Glide.with(c).load(EDteamImage).into(img);
        description.setText(list.get(position).getDescription());


        return convertView;

    }
}
