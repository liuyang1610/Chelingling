package com.beijing.chelingling.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.chelingling.MyAddESSr;
import com.beijing.chelingling.R;
import com.beijing.chelingling.info.MyJiFenShangCheng;
import com.beijing.chelingling.unit.ChangWeb;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class MyDuiHuanAdapter
        extends RecyclerView.Adapter<MyDuiHuanAdapter.ViewHolder> {
    private Context context;
    private boolean istrue;
    private ArrayList<MyJiFenShangCheng> la;
    private String[] strs;

    public MyDuiHuanAdapter(Context paramContext, boolean paramBoolean, ArrayList<MyJiFenShangCheng> paramLinkedList, String[] paramArrayOfString) {
        this.context = paramContext;
        this.istrue = paramBoolean;
        this.la = paramLinkedList;
        this.strs = paramArrayOfString;
    }

    public int getItemCount() {
        return this.la.size();
    }

    public void onBindViewHolder(ViewHolder paramViewHolder, final int paramInt) {
        if (this.istrue == true) {
            paramViewHolder.duihuan.setVisibility(View.GONE);
            paramViewHolder.duihuanyes.setVisibility(View.VISIBLE);
        } else {
            paramViewHolder.duihuan.setVisibility(View.VISIBLE);
            paramViewHolder.duihuanyes.setVisibility(View.GONE);
        }

        paramViewHolder.duihuanjage.setText(((MyJiFenShangCheng) this.la.get(paramInt)).getIntegra());
        paramViewHolder.shangpinname.setText(((MyJiFenShangCheng) this.la.get(paramInt)).getTitle());
        paramViewHolder.yiduinum.setText(((MyJiFenShangCheng) this.la.get(paramInt)).getNums());
        paramViewHolder.kuncunnum.setText(((MyJiFenShangCheng) this.la.get(paramInt)).getNum());
        Picasso.get().load(ChangWeb.YUMING + ((MyJiFenShangCheng) this.la.get(paramInt)).getPic()).into(paramViewHolder.shangpinima);
        paramViewHolder.duihuan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                MyDuiHuanAdapter.this.context.startActivity(new Intent(MyDuiHuanAdapter.this.context, MyAddESSr.class).putExtra("id", (Serializable) MyDuiHuanAdapter.this.la.get(paramInt)));
            }
        });
        return;


    }

    public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.layout_duihuan, null));
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder {
        private Button duihuan;
        private TextView duihuanjage;
        private TextView duihuanyes;
        private TextView kuncunnum;
        private ImageView shangpinima;
        private TextView shangpinname;
        private TextView yiduinum;

        public ViewHolder(View paramView) {
            super(paramView);
            this.shangpinname = ((TextView) paramView.findViewById(R.id.shangpinname));
            this.duihuanjage = ((TextView) paramView.findViewById(R.id.duihuanjage));
            this.yiduinum = ((TextView) paramView.findViewById(R.id.yiduinum));
            this.kuncunnum = ((TextView) paramView.findViewById(R.id.kuncunnum));
            this.duihuanyes = ((TextView) paramView.findViewById(R.id.duihuanyes));
            this.shangpinima = ((ImageView) paramView.findViewById(R.id.shangpinima));
            this.duihuan = ((Button) paramView.findViewById(R.id.duihuan));
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\adapter\MyDuiHuanAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */