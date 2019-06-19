package com.beijing.chelingling.lovecar;

public class DataBean
{
  public static final int TYPE_CHARACTER = 0;
  public static final int TYPE_DATA = 1;
  private String item_en;
  private int item_type;
  private String name;
  private String phone;
  
  public DataBean(String paramString1, String paramString2, int paramInt)
  {
//    CharacterParser localCharacterParser = CharacterParser.getInstance();
//    this.name = paramString1;
//    this.phone = paramString2;
//    this.item_type = paramInt;
//    this.item_en = localCharacterParser.getSelling(paramString1).toUpperCase().trim();
//    if (!this.item_en.matches("[A-Z]+")) {
//      this.item_en = ("#" + this.item_en);
//    }
  }
  
  public String getItem_en()
  {
    return this.item_en;
  }
  
  public int getItem_type()
  {
    return this.item_type;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setItem_en(String paramString)
  {
    this.item_en = paramString;
  }
  
  public void setItem_type(int paramInt)
  {
    this.item_type = paramInt;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void setPhone(String paramString)
  {
    this.phone = paramString;
  }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\lovecar\DataBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */