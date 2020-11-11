package com.jxqixin.trafic.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门或职位dto,用于部门职位树
 */
public class DeptPositionDto {
    private String id;
    private String name;
    /**类别:dept or position*/
    private String type;

    private List<DeptPositionDto> children = new ArrayList<>();

    public List<DeptPositionDto> getChildren() {
        return children;
    }

    public void setChildren(List<DeptPositionDto> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
