package com.beijing.chelingling.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.chelingling.R;
import com.beijing.chelingling.info.MapInfo;

import java.util.ArrayList;
import java.util.List;

public class MyMapAdapter
        extends RecyclerView.Adapter<MyMapAdapter.ViewHolder>
        implements View.OnClickListener {
    private Context context;
    private List<MapInfo> data;
    private List<Boolean> isClicks;
    private OnItemClickListener mItemClickListener;

    public MyMapAdapter(List<MapInfo> paramList, Context paramContext) {
        this.context = paramContext;
        this.data = paramList;
        this.isClicks = new ArrayList();
        int i = 0;
        while (i < paramList.size()) {
            if (i == 0) {
                this.isClicks.add(Boolean.valueOf(true));
            }
            this.isClicks.add(Boolean.valueOf(false));
            i += 1;
        }
    }

    public int getItemCount() {
        return this.data.size();
    }

    public void onBindViewHolder(ViewHolder paramViewHolder, final int paramInt) {
        if (((Boolean) this.isClicks.get(paramInt)).booleanValue() == true) {
            paramViewHolder.name.setTextColor(Color.parseColor("#0077ee"));
            paramViewHolder.addre.setTextColor(Color.parseColor("#0077ee"));
            paramViewHolder.leftsa.setBackgroundResource(R.drawable.addsblue);
        } else {
            paramViewHolder.name.setTextColor(Color.parseColor("#1a1a1a"));
            paramViewHolder.addre.setTextColor(Color.parseColor("#1a1a1a"));
            paramViewHolder.leftsa.setBackgroundResource(R.drawable.icon_position);
        }

        paramViewHolder.name.setText(((MapInfo) this.data.get(paramInt)).getDujinname());
        paramViewHolder.addre.setText("(" + ((MapInfo) this.data.get(paramInt)).getDujindizhi() + ")");
        paramViewHolder.itemView.setTag(Integer.valueOf(paramInt));

        if (this.mItemClickListener != null) {
            paramViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View paramAnonymousView) {
                    int i = 0;
                    while (i < MyMapAdapter.this.isClicks.size()) {
                        MyMapAdapter.this.isClicks.set(i, Boolean.valueOf(false));
                        i += 1;
                    }
                    MyMapAdapter.this.isClicks.set(paramInt, Boolean.valueOf(true));
                    MyMapAdapter.this.notifyDataSetChanged();
                    MyMapAdapter.this.mItemClickListener.onItemClick(((Integer) paramAnonymousView.getTag()).intValue());
                }
            });
        }
        return;


    }

    public void onClick(View paramView) {
        if (this.mItemClickListener != null) {
            this.mItemClickListener.onItemClick(((Integer) paramView.getTag()).intValue());
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_map, null);
        ViewHolder localViewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return localViewHolder;
    }

    public void setItemClickListener(OnItemClickListener paramOnItemClickListener) {
        this.mItemClickListener = paramOnItemClickListener;
    }

    public static abstract interface OnItemClickListener {
        public abstract void onItemClick(int paramInt);
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder {
        private TextView addre;
        private ImageView leftsa;
        private TextView name;

        public ViewHolder(View paramView) {
            super(paramView);
            this.leftsa = ((ImageView) paramView.findViewById(R.id.leftsa));
            this.name = ((TextView) paramView.findViewById(R.id.name));
            this.addre = ((TextView) paramView.findViewById(R.id.addre));
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\adapter\MyMapAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */