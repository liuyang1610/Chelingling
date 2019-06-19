package com.beijing.chelingling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.chelingling.R;
import com.beijing.chelingling.info.Myzhuantihuodong;
import com.beijing.chelingling.unit.ChangWeb;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;

public class MyZhuanTiShowAdapter
        extends RecyclerView.Adapter<MyZhuanTiShowAdapter.ViewHolder>
        implements View.OnClickListener {
    private Context context;
    private OnItemClickListener mItemClickListener;
    private ArrayList<Myzhuantihuodong> remen;

    public MyZhuanTiShowAdapter(Context paramContext, ArrayList<Myzhuantihuodong> paramLinkedList) {
        this.context = paramContext;
        this.remen = paramLinkedList;
        Log.i("mvbnbvnvb", paramLinkedList.size() + "  4");
    }

    public int getItemCount() {
        return this.remen.size();
    }

    public void onBindViewHolder(ViewHolder paramViewHolder, int paramInt) {
        Picasso.get().load(ChangWeb.YUMING + ((Myzhuantihuodong) this.remen.get(paramInt)).getPic()).fit().into(paramViewHolder.zhuantiim);
        paramViewHolder.zhuantite.setText(((Myzhuantihuodong) this.remen.get(paramInt)).getTitle());
        paramViewHolder.itemView.setTag(Integer.valueOf(paramInt));
    }

    public void onClick(View paramView) {
        if (this.mItemClickListener != null) {
            this.mItemClickListener.onItemClick(((Integer) paramView.getTag()).intValue());
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.zhuantihuofive, paramViewGroup, false);
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
        private ImageView zhuantiim;
        private TextView zhuantite;

        public ViewHolder(View paramView) {
            super(paramView);
            this.zhuantiim = ((ImageView) paramView.findViewById(R.id.zhuantiim));
            this.zhuantite = ((TextView) paramView.findViewById(R.id.zhuantite));
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\adapter\MyZhuanTiShowAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */