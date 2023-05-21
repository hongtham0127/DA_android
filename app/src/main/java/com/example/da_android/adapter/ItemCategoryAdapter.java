package com.example.da_android.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.example.da_android.R;
import com.example.da_android.model.CategoryItem;
import com.example.da_android.model.Transaction;

import java.util.List;

public class ItemCategoryAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<CategoryItem> categoryItemList;
    private String check;
    private Transaction transaction;

    public ItemCategoryAdapter(Context context, int layout, List<CategoryItem> categoryItemList, String check, Transaction transaction) {
        this.context = context;
        this.layout = layout;
        this.categoryItemList = categoryItemList;
        this.check = check;
        this.transaction = transaction;
    }

    @Override
    public int getCount() {
        return categoryItemList.size();
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
        ImageView imgIcon;
        TextView txtTenDM;
        TextView checkEdit;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewholder holder;
        if(view == null)
        {
            holder = new viewholder();
            LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.imgIcon = view.findViewById(R.id.iconCtg);
            holder.txtTenDM = view.findViewById(R.id.txt_tenDM);
            holder.checkEdit = view.findViewById(R.id.check_edit);
            view.setTag(holder);
        }
        else
        {
            holder = (viewholder) view.getTag();
        }
        CategoryItem categoryItem = categoryItemList.get(i);
        holder.imgIcon.setImageResource(categoryItem.getIcon());
        int color = ContextCompat.getColor(context, categoryItem.getColor());
        ColorStateList tint = ColorStateList.valueOf(color);
        ImageViewCompat.setImageTintList(holder.imgIcon, tint);
        holder.txtTenDM.setText(categoryItem.getName());
        if(holder.checkEdit.getText().toString().equals(check))
        {
            if(categoryItem.getIdCtg().equals(transaction.getIdCtg()))
            {
                view.setBackgroundResource(R.drawable.selected_item);
            }
        }
        else
        {
            if (i == 0) {
                view.setBackgroundResource(R.drawable.selected_item);
            }
        }
        return view;
    }
}
