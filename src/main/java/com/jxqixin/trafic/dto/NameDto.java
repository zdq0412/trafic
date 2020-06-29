package com.jxqixin.trafic.dto;
/**
 * 只有一个字符串作为查询条件的dto
 */
public class NameDto extends PageDto {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
