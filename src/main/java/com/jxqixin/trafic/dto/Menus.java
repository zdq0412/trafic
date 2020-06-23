package com.jxqixin.trafic.dto;

import java.util.List;

/**
 * 页面可 显示的菜单结构
 */
public class Menus {
    /**图标*/
    private String icon;
    /**唯一标识*/
    private String index;
    /**标题*/
    private String title;
    /**子菜单*/
    private List<Menus> subs;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Menus> getSubs() {
        return subs;
    }

    public void setSubs(List<Menus> subs) {
        this.subs = subs;
    }
}
