package com.bobomee.blogdemos.view.index;

/**
 * @author：BoBoMEe Created at 2016/1/5.
 */
public class UserModel {
    public int icon;
    public String name;
    public String des;

    public UserModel() {
    }

    public UserModel(String des, int icon, String name) {
        this.des = des;
        this.icon = icon;
        this.name = name;
    }

}
