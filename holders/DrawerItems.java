package com.smartstudio.sajmovi.eu.holders;

/**
 * Created by Boris on 12.6.2015..
 */
public class DrawerItems {
        String ItemName;
        int imgResID;

        public DrawerItems(String itemName, int imgResID) {
            super();
            ItemName = itemName;
            this.imgResID = imgResID;
        }

        public String getItemName() {
            return ItemName;
        }
        public void setItemName(String itemName) {
            ItemName = itemName;
        }
        public int getImgResID() {
            return imgResID;
        }
        public void setImgResID(int imgResID) {
            this.imgResID = imgResID;
        }
}
