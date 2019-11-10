package com.smartstudio.sajmovi.eu.holders;

/**
 * Created by Boris on 13.6.2015..
 */
public class DrawerItemsCalendar {
    String ItemName;
    String KeyName;
    int imgResID;

    public DrawerItemsCalendar(String itemName, int imgResID, String keynames) {
        super();
        ItemName = itemName;
        this.imgResID = imgResID;
        KeyName = keynames;
    }

    public String getItemName() {
        return ItemName;
    }
    public String getKeyName() {
        return KeyName;
    }
    public int getImgResID() {
        return imgResID;
    }
    public void setItemName(String itemName) {
        ItemName = itemName;
    }
    public void setKeyName(String keynames) {
        KeyName = keynames;
    }
    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }
}
