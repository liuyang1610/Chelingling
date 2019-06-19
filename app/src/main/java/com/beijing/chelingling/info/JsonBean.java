package com.beijing.chelingling.info;


import java.util.List;

public class JsonBean {
    private List<CityBean> city;
    private String name;

    public List<CityBean> getCityList() {
        return this.city;
    }

    public String getName() {
        return this.name;
    }

    public String getPickerViewText() {
        return this.name;
    }

    public void setCityList(List<CityBean> paramList) {
        this.city = paramList;
    }

    public void setName(String paramString) {
        this.name = paramString;
    }

    public static class CityBean {
        private List<String> area;
        private String name;

        public List<String> getArea() {
            return this.area;
        }

        public String getName() {
            return this.name;
        }

        public void setArea(List<String> paramList) {
            this.area = paramList;
        }

        public void setName(String paramString) {
            this.name = paramString;
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\info\JsonBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */