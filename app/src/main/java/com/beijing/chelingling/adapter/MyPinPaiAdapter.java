package com.beijing.chelingling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.chelingling.R;
import com.beijing.chelingling.info.MyLoveCarInfo;

import java.util.List;

public class MyPinPaiAdapter
        extends RecyclerView.Adapter<MyPinPaiAdapter.ViewHolder>
        implements OnClickListener {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private Context context;
    private View mHeaderView;
    private OnItemClickListener mItemClickListener;
    private List<MyLoveCarInfo> mSortList;

    public MyPinPaiAdapter(Context paramContext) {
        this.context = paramContext;
    }

    public View getHeaderView() {
        return this.mHeaderView;
    }

    public int getItemCount() {
        return this.mSortList.size() + 1;
    }

    public int getItemViewType(int paramInt) {
        if (this.mHeaderView == null) {
            return 1;
        }
        if (paramInt == 0) {
            return 0;
        }
        return 1;
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount() - 1; i++) {
            String sortStr = mSortList.get(i).getLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    public int getSectionForPosition(int paramInt) {
        return mSortList.get(paramInt).getLetters().charAt(0);
    }

    public void onBindViewHolder(ViewHolder paramViewHolder, int paramInt) {
        if (getItemViewType(paramInt) == 0) {
            return;
        }
        if ((paramViewHolder instanceof ViewHolder)) {
            paramViewHolder.qichename.setText(mSortList.get(paramInt - 1).getName());

            Log.i("asdfas", mSortList.size() + "  ");
            int section = getSectionForPosition(paramInt - 1);
            if (paramInt - 1 == getPositionForSection(section)) {
                paramViewHolder.biaoti.setVisibility(View.VISIBLE);
                paramViewHolder.biaoti.setText(mSortList.get(paramInt - 1).getLetters());
            } else {
                paramViewHolder.biaoti.setVisibility(View.GONE);
            }
            paramViewHolder.itemView.setTag(paramInt - 1);
        }
    }

    public void onClick(View paramView) {
        if (this.mItemClickListener != null) {
            this.mItemClickListener.onItemClick(((Integer) paramView.getTag()).intValue());
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        if ((this.mHeaderView != null) && (paramInt == 0)) {
            return new ViewHolder(this.mHeaderView);
        }
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_addlove, paramViewGroup, false);
        ViewHolder localViewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return localViewHolder;
    }

    public void setData(List<MyLoveCarInfo> paramList) {
        mSortList = paramList;
        Log.i("asdfas", mSortList.size() + "  ");
    }

    public void setHeaderView(View paramView) {
        this.mHeaderView = paramView;
        notifyItemInserted(0);
    }

    public void setItemClickListener(OnItemClickListener paramOnItemClickListener) {
        this.mItemClickListener = paramOnItemClickListener;
    }

    public static abstract interface OnItemClickListener {
        public abstract void onItemClick(int paramInt);
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder {
        TextView biaoti;
        TextView qichename;

        public ViewHolder(View paramView) {
            super(paramView);
            this.biaoti = ((TextView) paramView.findViewById(R.id.biaoti));
            this.qichename = ((TextView) paramView.findViewById(R.id.qichename));
        }
    }
}


/* Location:              G:\apk\dex2jar-2.0\classes-dex2jar.jar!\com\daigou\zhongchufu\zhongchufu\pinpai\MyPinPaiAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */