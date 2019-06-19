package com.beijing.chelingling.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.beijing.chelingling.R;

import java.util.ArrayList;
import java.util.List;

public class RvGridAdapter
        extends RecyclerView.Adapter<RvGridAdapter.ItemHolder>
        implements View.OnClickListener {
    private String city;
    private List<Boolean> isClicks;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener = null;
    private String[] strings;

    public RvGridAdapter(Context paramContext, String[] paramArrayOfString, String paramString) {
        this.mContext = paramContext;
        this.city = paramString;
        this.mLayoutInflater = LayoutInflater.from(this.mContext);
        this.strings = paramArrayOfString;
        this.isClicks = new ArrayList();
        int i = 0;
        while (i < paramArrayOfString.length) {
            if (i == 0) {
                this.isClicks.add(Boolean.valueOf(true));
            }
            this.isClicks.add(Boolean.valueOf(false));
            i += 1;
        }
    }

    public int getItemCount() {
        return this.strings.length;
    }

    public void onBindViewHolder(ItemHolder paramItemHolder, final int paramInt) {
        Log.i("sadsadsad", this.strings[paramInt] + "  " + this.city);
        if (this.strings[paramInt].equals(this.city)) {
            paramItemHolder.itemView.setBackgroundResource(R.drawable.bg_round_rectangle_red);
            paramItemHolder.itemNameTv.setTextColor(Color.parseColor("#ffffff"));
        } else {
            paramItemHolder.itemView.setBackgroundResource(R.drawable.bg_round_rectangle_wight);
            paramItemHolder.itemNameTv.setTextColor(Color.parseColor("#000000"));
        }

        paramItemHolder.itemNameTv.setText(this.strings[paramInt]);
        paramItemHolder.itemView.setTag(Integer.valueOf(paramInt));
        if (this.mOnItemClickListener != null) {
            paramItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View paramAnonymousView) {
                    int i = 0;
                    while (i < RvGridAdapter.this.isClicks.size()) {
                        RvGridAdapter.this.isClicks.set(i, Boolean.valueOf(false));
                        i += 1;
                    }
                    RvGridAdapter.this.isClicks.set(paramInt, Boolean.valueOf(true));
                    RvGridAdapter.this.notifyDataSetChanged();
                    RvGridAdapter.this.mOnItemClickListener.onItemClick(paramAnonymousView, ((Integer) paramAnonymousView.getTag()).intValue());
                }
            });
        }
    }

    public void onClick(View paramView) {
        if (this.mOnItemClickListener != null) {
            Log.i("sdsadsadfsa", "mnbvcxkjhgfoiuytre");
        }
    }

    public ItemHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        View view = this.mLayoutInflater.inflate(R.layout.rv_item, paramViewGroup, false);
        ItemHolder localItemHolder = new ItemHolder(view);
        view.setOnClickListener(this);
        return localItemHolder;
    }

    public void setOnItemClickListener(OnItemClickListener paramOnItemClickListener) {
        this.mOnItemClickListener = paramOnItemClickListener;
    }

    class ItemHolder
            extends RecyclerView.ViewHolder {
        TextView itemNameTv;

        ItemHolder(View paramView) {
            super(paramView);
            this.itemNameTv = ((TextView) paramView.findViewById(R.id.item_name_tv));
        }
    }

    public static abstract interface OnItemClickListener {
        public abstract void onItemClick(View paramView, int paramInt);
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\adapter\RvGridAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */