package com.beijing.chelingling.info;

import java.io.Serializable;

public class MyLoveCarLInfo
        implements Serializable {
    private String brand;
    private String car_date;
    private String ctime;
    private String engine;
    private String frame;
    private String id;
    private String msg_id;
    private String pic;
    private String plate_number;
    private String document;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getCar_date() {
        return this.car_date;
    }

    public String getCtime() {
        return this.ctime;
    }

    public String getEngine() {
        return this.engine;
    }

    public String getFrame() {
        return this.frame;
    }

    public String getId() {
        return this.id;
    }

    public String getMsg_id() {
        return this.msg_id;
    }

    public String getPic() {
        return this.pic;
    }

    public String getPlate_number() {
        return this.plate_number;
    }

    public void setBrand(String paramString) {
        this.brand = paramString;
    }

    public void setCar_date(String paramString) {
        this.car_date = paramString;
    }

    public void setCtime(String paramString) {
        this.ctime = paramString;
    }

    public void setEngine(String paramString) {
        this.engine = paramString;
    }

    public void setFrame(String paramString) {
        this.frame = paramString;
    }

    public void setId(String paramString) {
        this.id = paramString;
    }

    public void setMsg_id(String paramString) {
        this.msg_id = paramString;
    }

    public void setPic(String paramString) {
        this.pic = paramString;
    }

    public void setPlate_number(String paramString) {
        this.plate_number = paramString;
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\info\MyLoveCarLInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */