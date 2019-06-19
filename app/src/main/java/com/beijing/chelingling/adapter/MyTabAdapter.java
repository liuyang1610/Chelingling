package com.beijing.chelingling.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyTabAdapter
        extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> titleList;

    public MyTabAdapter(FragmentManager paramFragmentManager, ArrayList<String> paramArrayList, ArrayList<Fragment> paramArrayList1) {
        super(paramFragmentManager);
        this.titleList = paramArrayList;
        this.fragmentList = paramArrayList1;
    }

    public int getCount() {
        return this.fragmentList.size();
    }

    public Fragment getItem(int paramInt) {
        return (Fragment) this.fragmentList.get(paramInt);
    }

    public CharSequence getPageTitle(int paramInt) {
        return (CharSequence) this.titleList.get(paramInt);
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\adapter\MyTabAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */