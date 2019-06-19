package com.beijing.chelingling.lovecar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ListUtil
{
  public static String getFirstCharacter(String paramString)
  {
    return paramString.substring(0, 1);
  }
  
  public static void sortList(List<DataBean> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    Collections.sort(paramList, new PinyinComparator());
    Object localObject2 = new DataBean(getFirstCharacter(((DataBean)paramList.get(0)).getItem_en()), "", 0);
    Object localObject1 = getFirstCharacter(((DataBean)paramList.get(0)).getItem_en());
    localArrayList.add(localObject2);
    localArrayList.add(paramList.get(0));
    int i = 1;
    while (i < paramList.size())
    {
      localObject2 = localObject1;
      if (getFirstCharacter(((DataBean)paramList.get(i)).getItem_en()).compareTo((String)localObject1) != 0)
      {
        localObject2 = getFirstCharacter(((DataBean)paramList.get(i)).getItem_en());
        localArrayList.add(new DataBean((String)localObject2, "", 0));
      }
      localArrayList.add(paramList.get(i));
      i += 1;
      localObject1 = localObject2;
    }
    paramList.clear();
    localObject1 = localArrayList.iterator();
    while (((Iterator)localObject1).hasNext()) {
      paramList.add((DataBean)((Iterator)localObject1).next());
    }
  }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\lovecar\ListUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */