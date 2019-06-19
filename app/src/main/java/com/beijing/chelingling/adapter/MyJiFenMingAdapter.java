package com.beijing.chelingling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beijing.chelingling.R;
import com.beijing.chelingling.info.MyJoFenMingXiInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class MyJiFenMingAdapter
        extends RecyclerView.Adapter<MyJiFenMingAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MyJoFenMingXiInfo> la;

    public MyJiFenMingAdapter(Context paramContext, ArrayList<MyJoFenMingXiInfo> paramLinkedList) {
        this.context = paramContext;
        this.la = paramLinkedList;
    }

    public int getItemCount() {
        return this.la.size();
    }

    public void onBindViewHolder(ViewHolder paramViewHolder, int paramInt) {
        Object localObject1 = new Date(Long.parseLong(la.get(paramInt).getTime() + "000"));
        Object localObject2 = new SimpleDateFormat("yy-MM-dd");
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        localObject2 = ((SimpleDateFormat) localObject2).format((Date) localObject1);
        localObject1 = localSimpleDateFormat.format((Date) localObject1);
        paramViewHolder.riqi.setText((CharSequence) localObject2);
        paramViewHolder.time.setText((CharSequence) localObject1);
        paramViewHolder.fenshu.setTextColor(-65536);
        if ((la.get(paramInt)).getLx().equals("0")) {
            paramViewHolder.fenshu.setTextColor(this.context.getResources().getColor(R.color.blue));
            paramViewHolder.fenshu.setText("+" + la.get(paramInt).getIntegra());
            return;
        }
        paramViewHolder.fenshu.setTextColor(-65536);
        paramViewHolder.fenshu.setText("-" + ((MyJoFenMingXiInfo) this.la.get(paramInt)).getIntegra());
    }

    public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_mingxi, null));
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder {
        TextView fenshu;
        TextView riqi;
        TextView time;

        public ViewHolder(View paramView) {
            super(paramView);
            this.riqi = ((TextView) paramView.findViewById(R.id.riqi));
            this.time = ((TextView) paramView.findViewById(R.id.time));
            this.fenshu = ((TextView) paramView.findViewById(R.id.fenshu));
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\adapter\MyJiFenMingAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */