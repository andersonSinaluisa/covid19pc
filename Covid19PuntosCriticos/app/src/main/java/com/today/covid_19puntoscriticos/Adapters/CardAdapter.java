package com.today.covid_19puntoscriticos.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.today.covid_19puntoscriticos.Model.Pages;

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
        return null;
    }
}
