package com.jxqixin.trafic.model;

import java.io.Serializable;
import java.util.List;

/**
 * 树状结构节点
 */
public class TreeNode implements Serializable {
    /**id*/
    private String id;
    /**标题*/
    private String title;
    /**父节点id*/
    private String parentId;
    /**子节点*/
    private List<TreeNode> children;

    /**是否勾选*/
    private String checkArr="0";

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getCheckArr() {
        return checkArr;
    }

    public void setCheckArr(String checkArr) {
        this.checkArr = checkArr;
    }
}
