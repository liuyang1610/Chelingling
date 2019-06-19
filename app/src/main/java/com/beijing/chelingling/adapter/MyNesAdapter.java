package com.beijing.chelingling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beijing.chelingling.R;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.MyNewInfo;
import com.squareup.picasso.Picasso;
import com.thinkcool.circletextimageview.CircleTextImageView;

import java.util.ArrayList;
import java.util.LinkedList;

public class MyNesAdapter
        extends RecyclerView.Adapter<MyNesAdapter.ViewHolder>
        implements View.OnClickListener {
    private Context context;
    private OnItemClickListener mItemClickListener;
    private ArrayList<MyNewInfo> remen;

    public MyNesAdapter(Context paramContext, ArrayList<MyNewInfo> paramLinkedList) {
        this.context = paramContext;
        this.remen = paramLinkedList;
    }

    public int getItemCount() {
        return this.remen.size();
    }

    public void onBindViewHolder(ViewHolder paramViewHolder, int paramInt) {
        Log.i("vxczxz", ((MyNewInfo) this.remen.get(this.remen.size() - 1 - paramInt)).getPic());
        Picasso.get().load(ChangWeb.YUMING + ((MyNewInfo) this.remen.get(this.remen.size() - 1 - paramInt)).getPic()).into(paramViewHolder.newsicon);
        paramViewHolder.nesname.setText(((MyNewInfo) this.remen.get(this.remen.size() - 1 - paramInt)).getTitle());
        paramViewHolder.nescontent.setText(((MyNewInfo) this.remen.get(this.remen.size() - 1 - paramInt)).getDescription());
        paramViewHolder.nestime.setText(((MyNewInfo) this.remen.get(this.remen.size() - 1 - paramInt)).getCtime());
        paramViewHolder.itemView.setTag(Integer.valueOf(this.remen.size() - 1 - paramInt));
    }

    public void onClick(View paramView) {
        if (this.mItemClickListener != null) {
            this.mItemClickListener.onItemClick(((Integer) paramView.getTag()).intValue());
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_news, null);
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
        private TextView nescontent;
        private TextView nesname;
        private TextView nestime;
        private CircleTextImageView newsicon;

        public ViewHolder(View paramView) {
            super(paramView);
            this.newsicon = ((CircleTextImageView) paramView.findViewById(R.id.newsicon));
            this.nesname = ((TextView) paramView.findViewById(R.id.nesname));
            this.nescontent = ((TextView) paramView.findViewById(R.id.nescontent));
            this.nestime = ((TextView) paramView.findViewById(R.id.nestime));
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\adapter\MyNesAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */