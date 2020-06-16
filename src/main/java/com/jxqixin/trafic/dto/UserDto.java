package com.jxqixin.trafic.dto;
public class UserDto extends PageDto {
    /**用户名*/
    private String username;
    /**真实姓名*/
    private String realName;
    /**电话号码*/
    private String tel;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
