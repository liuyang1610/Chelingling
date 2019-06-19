package com.beijing.chelingling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.chelingling.R;
import com.beijing.chelingling.info.MyLoveCarReMenInfo;
import com.beijing.chelingling.unit.ChangWeb;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyLoveHeadAdapter
        extends RecyclerView.Adapter<MyLoveHeadAdapter.Viewholder>
        implements View.OnClickListener {
    private Context context;
    private OnItemClickListener mItemClickListener;
    private List<MyLoveCarReMenInfo> remen;

    public MyLoveHeadAdapter(Context paramContext, List<MyLoveCarReMenInfo> remen) {
        this.context = paramContext;
        this.remen = remen;
    }

    public int getItemCount() {
        return remen.size();
    }

    public void onBindViewHolder(Viewholder paramViewholder, int paramInt) {
        Picasso.get().load(ChangWeb.YUMING + remen.get(paramInt).getLogo()).into(paramViewholder.carlogo);
        paramViewholder.carname.setText(remen.get(paramInt).getName());
        paramViewholder.itemView.setTag(paramInt);
    }

    public void onClick(View paramView) {
        if (this.mItemClickListener != null) {
            this.mItemClickListener.onItemClick((Integer) paramView.getTag());
        }
    }

    public Viewholder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_header_lovecar, null);
        Viewholder localViewholder = new Viewholder(view);
        view.setOnClickListener(this);
        return localViewholder;
    }

    public void setItemClickListener(OnItemClickListener paramOnItemClickListener) {
        this.mItemClickListener = paramOnItemClickListener;
    }

    public static abstract interface OnItemClickListener {
        public abstract void onItemClick(int paramInt);
    }

    public class Viewholder
            extends RecyclerView.ViewHolder {
        private ImageView carlogo;
        private TextView carname;

        public Viewholder(View paramView) {
            super(paramView);
            this.carlogo = ((ImageView) paramView.findViewById(R.id.carlogo));
            this.carname = ((TextView) paramView.findViewById(R.id.carname));
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\adapter\MyLoveHeadAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */