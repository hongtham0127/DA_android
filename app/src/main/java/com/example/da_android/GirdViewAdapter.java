package com.example.da_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class GirdViewAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<HinhAnh> hinhAnhList;

    public GirdViewAdapter(Context context, int layout, List<HinhAnh> hinhAnhList) {
        this.context = context;
        this.layout = layout;
        this.hinhAnhList = hinhAnhList;
    }


    @Override
    public int getCount() {

        return hinhAnhList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class viewholder{
        ImageView imgHinh;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewholder holder;
        if(view == null)
        {
            holder = new viewholder();
            LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.imgHinh = (ImageView) view.findViewById(R.id.hinh);
            view.setTag(holder);

        }
        else
        {
            holder = (viewholder) view.getTag();

        }
        HinhAnh hinhAnh = hinhAnhList.get(i);
        holder.imgHinh.setImageResource(hinhAnh.getHinh());
        return view;
    }
}
