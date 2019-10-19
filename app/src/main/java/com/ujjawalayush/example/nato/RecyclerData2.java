package com.ujjawalayush.example.nato;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class RecyclerData2 {
    String display,value,k,display1,uid,request;
    Bitmap bitmap;
    Drawable drawable;
    public String getUsername() {
        return display;
    }

    public String getRequest() {
        return request;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getDisplay1() {
        return display1;
    }

    public void setDisplay1(String display1) {
        this.display1 = display1;
    }


    public String getInfo() {
        return value;
    }


    public void setUsrname(String display) {
        this.display = display;
    }

    public void setInfo(String value) {
        this.value = value;
    }
}
