package com.beijing.chelingling.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.beijing.chelingling.R;
import com.beijing.chelingling.bean.GroupBean;
import com.beijing.chelingling.bean.ItemBean;

import java.util.List;

public class MyExAdapter
        implements ExpandableListAdapter {
    private List<List> childrenList;
    private int childrenlayout;
    private Context context;
    private List<GroupBean> groupBeanList;
    private int grouplayout;

    public MyExAdapter(Context paramContext, List<GroupBean> paramList, List<List> paramList1, int paramInt1, int paramInt2) {
        this.groupBeanList = paramList;
        this.childrenList = paramList1;
        this.context = paramContext;
        this.grouplayout = paramInt1;
        this.childrenlayout = paramInt2;
    }

    public boolean areAllItemsEnabled() {
        return false;
    }

    public Object getChild(int paramInt1, int paramInt2) {
        return ((List) this.childrenList.get(paramInt1)).get(paramInt1);
    }

    public long getChildId(int paramInt1, int paramInt2) {
        return paramInt2;
    }

    public View getChildView(int paramInt1, int paramInt2, boolean paramBoolean, View paramView, ViewGroup paramViewGroup) {
        if (paramView == null) {
            paramView = LayoutInflater.from(this.context).inflate(this.childrenlayout, paramViewGroup, false);
        } else {
//            paramViewGroup = (ItemBean) getChild(paramInt1, paramInt2);
//            ((TextView) paramView.findViewById(R.id.view_content)).setText(paramViewGroup.getContent());

        }
        return paramView;
    }

    public int getChildrenCount(int paramInt) {
        return ((List) this.childrenList.get(paramInt)).size();
    }

    public long getCombinedChildId(long paramLong1, long paramLong2) {
        return 0L;
    }

    public long getCombinedGroupId(long paramLong) {
        return 0L;
    }

    public Object getGroup(int paramInt) {
        return this.groupBeanList.get(paramInt);
    }

    public int getGroupCount() {
        return this.groupBeanList.size();
    }

    public long getGroupId(int paramInt) {
        return paramInt;
    }

    public View getGroupView(int paramInt, boolean paramBoolean, View paramView, ViewGroup paramViewGroup) {
        GroupBean group = null;

        if (paramView == null) {
            paramView = LayoutInflater.from(this.context).inflate(this.grouplayout, paramViewGroup, false);
        } else {
            group = (GroupBean) getGroup(paramInt);
        }

        ((TextView) paramView.findViewById(R.id.view_title)).setText(group.getTitle());
        return paramView;

    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int paramInt1, int paramInt2) {
        return true;
    }

    public boolean isEmpty() {
        return false;
    }

    public void onGroupCollapsed(int paramInt) {
    }

    public void onGroupExpanded(int paramInt) {
    }

    public void registerDataSetObserver(DataSetObserver paramDataSetObserver) {
    }

    public void unregisterDataSetObserver(DataSetObserver paramDataSetObserver) {
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\adapter\MyExAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */