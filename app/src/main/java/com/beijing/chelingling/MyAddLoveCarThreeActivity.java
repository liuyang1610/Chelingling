package com.beijing.chelingling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.chelingling.adapter.MyExAdapter;
import com.beijing.chelingling.bean.GroupBean;
import com.beijing.chelingling.bean.ItemBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAddLoveCarThreeActivity
        extends AppCompatActivity {
    private MyExAdapter adapter;
    private ArrayList<List> childList;
    @Bind({R.id.expandablelistview})
    ExpandableListView expandablelistview;
    private ArrayList<GroupBean> groupList;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;

    public void infoData() {
        this.groupList = new ArrayList();
        this.childList = new ArrayList();
        this.groupList.add(new GroupBean("2018款"));
        this.groupList.add(new GroupBean("2017款"));
        this.groupList.add(new GroupBean("2016款"));
        this.groupList.add(new GroupBean("2015款"));
        this.groupList.add(new GroupBean("2014款"));
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(new ItemBean("2018轻量级专享型"));
        localArrayList.add(new ItemBean("2018轻量级专享型"));
        localArrayList.add(new ItemBean("2018轻量级专享型"));
        localArrayList.add(new ItemBean("2018轻量级专享型"));
        localArrayList.add(new ItemBean("2018轻量级专享型"));
        localArrayList.add(new ItemBean("2018轻量级专享型"));
        this.childList.add(localArrayList);
        localArrayList = new ArrayList();
        localArrayList.add(new ItemBean("2017轻量级专享型"));
        localArrayList.add(new ItemBean("2017轻量级专享型"));
        localArrayList.add(new ItemBean("2017轻量级专享型"));
        localArrayList.add(new ItemBean("2017轻量级专享型"));
        localArrayList.add(new ItemBean("2017轻量级专享型"));
        localArrayList.add(new ItemBean("2017轻量级专享型"));
        this.childList.add(localArrayList);
        localArrayList = new ArrayList();
        localArrayList.add(new ItemBean("2016轻量级专享型"));
        localArrayList.add(new ItemBean("2016轻量级专享型"));
        localArrayList.add(new ItemBean("2016轻量级专享型"));
        localArrayList.add(new ItemBean("2016轻量级专享型"));
        localArrayList.add(new ItemBean("2016轻量级专享型"));
        localArrayList.add(new ItemBean("2016轻量级专享型"));
        this.childList.add(localArrayList);
        localArrayList = new ArrayList();
        localArrayList.add(new ItemBean("2015轻量级专享型"));
        localArrayList.add(new ItemBean("2015轻量级专享型"));
        localArrayList.add(new ItemBean("2015轻量级专享型"));
        localArrayList.add(new ItemBean("2015轻量级专享型"));
        localArrayList.add(new ItemBean("2015轻量级专享型"));
        localArrayList.add(new ItemBean("2015轻量级专享型"));
        this.childList.add(localArrayList);
        localArrayList = new ArrayList();
        localArrayList.add(new ItemBean("2014轻量级专享型"));
        localArrayList.add(new ItemBean("2014轻量级专享型"));
        localArrayList.add(new ItemBean("2014轻量级专享型"));
        localArrayList.add(new ItemBean("2014轻量级专享型"));
        localArrayList.add(new ItemBean("2014轻量级专享型"));
        localArrayList.add(new ItemBean("2014轻量级专享型"));
        this.childList.add(localArrayList);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_add_love_car_three);
        getWindow().clearFlags(1024);
        ButterKnife.bind(this);
        this.titles.setText("添加爱车");
        infoData();
        this.adapter = new MyExAdapter(this, this.groupList, this.childList, R.layout.item_group_exlist, R.layout.item_chilad);
        this.expandablelistview.setAdapter(this.adapter);
    }

    @OnClick({R.id.leftimfan})
    public void onViewClicked() {
        finish();
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyAddLoveCarThreeActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */