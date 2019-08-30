package com.jbckss.koreanrest;

public class MenuItem {
    int rest_idx;
    String rest_name;
    String rest_call;
    String rest_add;
    int rest_like;
    int rest_category;
    int item_imgIdx;

    public MenuItem(int rest_idx, String rest_name, String rest_call, String rest_add, int rest_like, int rest_category , int item_imgIdx) {
        this.rest_idx = rest_idx;
        this.rest_name = rest_name;
        this.rest_call = rest_call;
        this.rest_add = rest_add;
        this.rest_like = rest_like;
        this.rest_category = rest_category;
        this.item_imgIdx = item_imgIdx;
    }

    public int getRest_idx() {
        return rest_idx;
    }

    public void setRest_idx(int rest_idx) {
        this.rest_idx = rest_idx;
    }

    public String getRest_name() {
        return rest_name;
    }

    public void setRest_name(String rest_name) {
        this.rest_name = rest_name;
    }

    public String getRest_call() {
        return rest_call;
    }

    public void setRest_call(String rest_call) {
        this.rest_call = rest_call;
    }

    public String getRest_add() {
        return rest_add;
    }

    public void setRest_add(String rest_add) {
        this.rest_add = rest_add;
    }

    public int getRest_like() {
        return rest_like;
    }

    public void setRest_like(int rest_like) {
        this.rest_like = rest_like;
    }

    public int getRest_category() {
        return rest_category;
    }

    public void setRest_category(int rest_category) {
        this.rest_category = rest_category;
    }

    public int getItem_imgIdx() {
        return item_imgIdx;
    }

    public void setItem_imgIdx(int item_imgIdx) {
        this.item_imgIdx = item_imgIdx;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "rest_idx=" + rest_idx +
                ", rest_name='" + rest_name + '\'' +
                ", rest_call='" + rest_call + '\'' +
                ", rest_add='" + rest_add + '\'' +
                ", rest_like=" + rest_like +
                ", rest_category=" + rest_category +
                ", item_imgIdx=" + item_imgIdx +
                '}';
    }
}
