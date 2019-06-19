package com.beijing.chelingling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beijing.chelingling.R;
import com.beijing.chelingling.info.MyCarTypeInfo;

import java.util.ArrayList;
import java.util.LinkedList;

public class MyLoveCarTwoAdapter
        extends RecyclerView.Adapter<MyLoveCarTwoAdapter.ViewHolder>
        implements View.OnClickListener {
    private Context context;
    private ArrayList<MyCarTypeInfo> laerji;
    private OnItemClickListener mItemClickListener;

    public MyLoveCarTwoAdapter(Context paramContext, ArrayList<MyCarTypeInfo> paramLinkedList) {
        this.context = paramContext;
        this.laerji = paramLinkedList;
    }

    public int getItemCount() {
        return this.laerji.size();
    }

    public void onBindViewHolder(ViewHolder paramViewHolder, int paramInt) {
        paramViewHolder.pinpainame.setText(((MyCarTypeInfo) this.laerji.get(paramInt)).getType());
        paramViewHolder.itemView.setTag(Integer.valueOf(paramInt));
    }

    public void onClick(View paramView) {
        if (this.mItemClickListener != null) {
            this.mItemClickListener.onItemClick(((Integer) paramView.getTag()).intValue());
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_lovecartwo, null);
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
        TextView pinpainame;

        public ViewHolder(View paramView) {
            super(paramView);
            this.pinpainame = ((TextView) paramView.findViewById(R.id.pinpainame));
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\adapter\MyLoveCarTwoAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */