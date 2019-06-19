package com.beijing.chelingling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beijing.chelingling.R;
import com.beijing.chelingling.info.MyLoveCarInfo;

import java.util.ArrayList;
import java.util.List;

public class MyLoveCarAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private Context context;
    private List<MyLoveCarInfo> laType;
    private ArrayList<String> mDatas = new ArrayList();
    private View mHeaderView;
    private OnItemClickListener mItemClickListener;

    public MyLoveCarAdapter(Context paramContext, List<MyLoveCarInfo> paramList) {
        this.context = paramContext;
        this.laType = paramList;
    }

    public void addDatas(ArrayList<String> paramArrayList) {
        this.mDatas.addAll(paramArrayList);
        notifyDataSetChanged();
    }

    public View getHeaderView() {
        return this.mHeaderView;
    }

    public int getItemCount() {
        if (this.mHeaderView == null) {
            return 20;
        }
        return 21;
    }

    public int getItemViewType(int paramInt) {
        if (this.mHeaderView == null) {
        }
        while (paramInt != 0) {
            return 1;
        }
        return 0;
    }

    public int getPositionForSection(int paramInt) {
        int i = 0;
        while (i < getItemCount()) {
            if (((MyLoveCarInfo) this.laType.get(i)).getLetters().toUpperCase().charAt(0) == paramInt) {
                return i;
            }
            i += 1;
        }
        return -1;
    }

    public int getRealPosition(RecyclerView.ViewHolder paramViewHolder) {
        int i = paramViewHolder.getLayoutPosition();
        if (this.mHeaderView == null) {
            return i;
        }
        return i - 1;
    }

    public int getSectionForPosition(int paramInt) {
        return laType.get(paramInt).getLetters().charAt(0);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt) {
        if (getItemViewType(paramInt) == 0) {
        }

        int i = getRealPosition(paramViewHolder);
        paramViewHolder.itemView.setTag(Integer.valueOf(i));

        ((Holder) paramViewHolder).qichename.setText((laType.get(paramInt - 1)).getName());
        if (paramInt - 1 == getPositionForSection(getSectionForPosition(paramInt - 1))) {
            ((Holder) paramViewHolder).biaoti.setVisibility(View.GONE);
            ((Holder) paramViewHolder).biaoti.setText((laType.get(paramInt - 1)).getLetters());
            return;
        }
        ((Holder) paramViewHolder).biaoti.setVisibility(View.VISIBLE);
    }

    public void onClick(View paramView) {
        if (this.mItemClickListener != null) {
            this.mItemClickListener.onItemClick(((Integer) paramView.getTag()).intValue());
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        if ((this.mHeaderView != null) && (paramInt == 0)) {
            return new Holder(this.mHeaderView);
        }
        View view = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.item_addlove, paramViewGroup, false);
        view.setOnClickListener(this);
        return new Holder(view);
    }

    public void setHeaderView(View paramView) {
        this.mHeaderView = paramView;
        notifyItemInserted(0);
    }

    public void setItemClickListener(OnItemClickListener paramOnItemClickListener) {
        this.mItemClickListener = paramOnItemClickListener;
    }

    class Holder
            extends RecyclerView.ViewHolder {
        private TextView biaoti;
        private TextView qichename;

        public Holder(View paramView) {
            super(paramView);
            if (paramView == MyLoveCarAdapter.this.mHeaderView) {
                return;
            }
            this.biaoti = ((TextView) paramView.findViewById(R.id.biaoti));
            this.qichename = ((TextView) paramView.findViewById(R.id.qichename));
        }
    }

    public static abstract interface OnItemClickListener {
        public abstract void onItemClick(int paramInt);
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\adapter\MyLoveCarAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */