package com.jbckss.koreanrest;

public class FoodItem {
    int idx;
    String name;

    public FoodItem(String name, int idx) {
        this.idx = idx;
        this.name = name;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "idx=" + idx +
                ", name='" + name + '\'' +
                '}';
    }
}
