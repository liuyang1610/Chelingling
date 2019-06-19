package com.beijing.chelingling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beijing.chelingling.R;
import com.beijing.chelingling.info.MyjiaYoutaoCan;

import java.util.ArrayList;

public class MyTaoCanAdapter
        extends RecyclerView.Adapter<MyTaoCanAdapter.ViewHolder>
        implements View.OnClickListener {
    private Context context;
    private ArrayList<MyjiaYoutaoCan> la;
    private OnItemClickListener mItemClickListener;

    public MyTaoCanAdapter(Context paramContext, ArrayList<MyjiaYoutaoCan> paramLinkedList) {
        this.context = paramContext;
        this.la = paramLinkedList;
    }

    public int getItemCount() {
        return this.la.size();
    }

    public void onBindViewHolder(ViewHolder paramViewHolder, int paramInt) {
        paramViewHolder.dfgsd.setText(((MyjiaYoutaoCan) this.la.get(paramInt)).getTitle());
        paramViewHolder.zhekou.setText("[" + ((MyjiaYoutaoCan) this.la.get(paramInt)).getZhekou() + "]");
        if ((paramViewHolder.zhekou.getText().toString().trim().equals("[ ]")) || (paramViewHolder.zhekou.getText().toString().trim().equals("[null]"))) {
            paramViewHolder.zhekou.setText("");
        }
        double i = Double.parseDouble(la.get(paramInt).getPricea());
        double j = Double.parseDouble(la.get(paramInt).getPrice());
        paramViewHolder.zhekouinfo.setText(paramInt + 1 + ",套餐金额" + ((MyjiaYoutaoCan) this.la.get(paramInt)).getPricea() + "元 实际缴纳" + ((MyjiaYoutaoCan) this.la.get(paramInt)).getPrice() + "元");
        paramViewHolder.shijizhekou.setText("实际折扣金额" + (i - j) + "元");
        paramViewHolder.itemView.setTag(Integer.valueOf(paramInt));
    }

    public void onClick(View paramView) {
        if (this.mItemClickListener != null) {
            this.mItemClickListener.onItemClick(((Integer) paramView.getTag()).intValue());
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_taocan, null);
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
        private TextView dfgsd;
        private TextView shijizhekou;
        private TextView zhekou;
        private TextView zhekouinfo;

        public ViewHolder(View paramView) {
            super(paramView);
            this.dfgsd = ((TextView) paramView.findViewById(R.id.dfgsd));
            this.zhekou = ((TextView) paramView.findViewById(R.id.zhekou));
            this.zhekouinfo = ((TextView) paramView.findViewById(R.id.zhekouinfo));
            this.shijizhekou = ((TextView) paramView.findViewById(R.id.shijizhekou));
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\adapter\MyTaoCanAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */