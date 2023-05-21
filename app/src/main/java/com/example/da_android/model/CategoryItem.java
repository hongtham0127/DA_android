package com.example.da_android.model;

public class CategoryItem {
    private String idCtg;
    private String name;
    private int icon;
    private String type;
    private int color;

    public CategoryItem(String idCtg, String name, int icon, String type, int color) {
        this.idCtg = idCtg;
        this.name = name;
        this.icon = icon;
        this.type = type;
        this.color = color;
    }

    public CategoryItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getIdCtg() {
        return idCtg;
    }

    public void setIdCtg(String idCtg) {
        this.idCtg = idCtg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
