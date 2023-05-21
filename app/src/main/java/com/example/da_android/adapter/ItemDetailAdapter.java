package com.example.da_android.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.example.da_android.DB;
import com.example.da_android.activity.EditInputActivity;
import com.example.da_android.R;
import com.example.da_android.model.CategoryItem;
import com.example.da_android.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Transaction> transactionList;
    DB db = new DB();

    public ItemDetailAdapter(Context context, int layout, List<Transaction> transactionList) {
        this.context = context;
        this.layout = layout;
        this.transactionList = transactionList;
    }

    @Override
    public int getCount() {
        return transactionList.size();
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
        TextView txtNote;
        TextView txtMoney;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewholder holder;
        if(view == null)
        {
            holder = new viewholder();
            LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.imgIcon = view.findViewById(R.id.detail_icon);
            holder.txtTenDM = view.findViewById(R.id.txt_detail_tenDM);
            holder.txtNote = view.findViewById(R.id.txt_detail_note);
            holder.txtMoney = view.findViewById(R.id.txt_detail_money);
            view.setTag(holder);
        }
        else
        {
            holder = (ItemDetailAdapter.viewholder) view.getTag();
        }
        Transaction item = transactionList.get(i);
        holder.imgIcon.setImageResource(R.drawable.ic_android_black_24dp);
        holder.txtTenDM.setText("None");

        if(item.getType().equals("Chi"))
        {
            db.readDataCategoryChi(new DB.OnCategoryDataLoadedListener() {
                @Override
                public void onCategoryDataLoaded(ArrayList<CategoryItem> list) {
                    for (CategoryItem itemCtg : list)
                    {
                        if(itemCtg.getIdCtg().equals(item.getIdCtg()))
                        {
                            holder.imgIcon.setImageResource(itemCtg.getIcon());
                            int color = ContextCompat.getColor(context, itemCtg.getColor());
                            ColorStateList tint = ColorStateList.valueOf(color);
                            ImageViewCompat.setImageTintList(holder.imgIcon, tint);
                            holder.txtTenDM.setText(itemCtg.getName());
                            return;
                        }
                    }

                }

                @Override
                public void onCategoryDataError(String errorMessage) {
                }
            });
        }
        else
        {
            db.readDataCategoryThu(new DB.OnCategoryDataLoadedListener() {
                @Override
                public void onCategoryDataLoaded(ArrayList<CategoryItem> list) {
                    for (CategoryItem itemCtg : list)
                    {
                        if(itemCtg.getIdCtg().equals(item.getIdCtg()))
                        {
                            holder.imgIcon.setImageResource(itemCtg.getIcon());
                            int color = ContextCompat.getColor(context, itemCtg.getColor());
                            ColorStateList tint = ColorStateList.valueOf(color);
                            ImageViewCompat.setImageTintList(holder.imgIcon, tint);
                            holder.txtTenDM.setText(itemCtg.getName());
                            return;
                        }
                    }

                }

                @Override
                public void onCategoryDataError(String errorMessage) {
                }
            });
            holder.txtMoney.setTextColor(Color.parseColor("#002BFF"));
        }

        if(item.getNote() == null || item.getNote().equals(""))
        {
            holder.txtNote.setText("");
        }
        else
        {
            holder.txtNote.setText("("+item.getNote()+")");
        }
        holder.txtMoney.setText(String.valueOf(item.getMoney()) + "đ");
        SharedPreferences sharedPref = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String viewCategory = sharedPref.getString("viewCategory", null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewCategory != null)
                {
//                    báo cáo biểu đồ cột
                    Intent intent = new Intent(context, EditInputActivity.class);
                    intent.putExtra("itemTransaction", item);
                    context.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(context, EditInputActivity.class);
                    intent.putExtra("itemTransaction", item);
                    context.startActivity(intent);
                }
            }
        });
        if(viewCategory != null)
        {
            view.setBackgroundResource(R.drawable.border_normal);
        }
        return view;
    }
}
