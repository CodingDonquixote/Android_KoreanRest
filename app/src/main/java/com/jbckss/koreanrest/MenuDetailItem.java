package com.jbckss.koreanrest;

public class MenuDetailItem {
    int rest_idx;
    String menu_name;
    String menu_price;

    public MenuDetailItem(int rest_idx, String menu_name, String menu_price) {
        this.rest_idx = rest_idx;
        this.menu_name = menu_name;
        this.menu_price = menu_price;
    }

    public int getRest_idx() {
        return rest_idx;
    }

    public void setRest_idx(int rest_idx) {
        this.rest_idx = rest_idx;
    }

    public String getMenu_name() {return menu_name;}

    public void setMenu_name(String menu_name) {this.menu_name = menu_name;}

    public String getMenu_price() {return menu_price;}

    public void setMenu_price(String menu_price) {this.menu_price = menu_price;}

    @Override
    public String toString() {
        return "MenuDetailItem{" +
                "rest_idx=" + rest_idx +
                ", menu_name='" + menu_name + '\'' +
                ", menu_price='" + menu_price + '\'' +
                '}';
    }
}
