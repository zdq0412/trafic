package com.jxqixin.trafic.dto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
public class EmployeeDto implements Serializable {
    private String id;
    /**名称*/
    private String name;
    /**身份证号*/
    private String idnum;
    /**头像图片地址*/
    private String photo;
    /***真实路径*/
    private String realPath;
    /**手机号*/
    private String tel;
    /**备注*/
    private String note;
    /**角色ID*/
    private String roleId;

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
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

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
