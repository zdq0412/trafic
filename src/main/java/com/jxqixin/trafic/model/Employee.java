package com.jxqixin.trafic.model;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "m003_employee")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class Employee implements Serializable {
    @Id
    @GeneratedValue(generator = "id_gen")
    private String id;
    /**名称*/
    private String name;
    /**以句点作为分隔的档案码,句点前数值表示的含义,0:简历数,1:劳动合同数,2:资质文件数,3:从业经历数,4:入职培训数,5:安全责任书数,6:培训考核情况数,7:其他文件数*/
    private String archiveCode;
    /**性别*/
    private String sex;
    /**年龄*/
    private int age;
    /**身份证号*/
    private String idnum;
    /**头像图片地址*/
    private String photo;
    /**实际存储路径*/
    private String realPath;
    /**手机号*/
    private String tel;
    /**备注*/
    private String note;
    /**所属企业*/
    @ManyToOne
    @JoinColumn(name = "org_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Org org;
    /**关联用户*/
    @ManyToOne
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;
    /**所在部门*/
    @ManyToOne
    @JoinColumn(name = "department_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotFound(action = NotFoundAction.IGNORE)
    private Department department;
    /**所在职位*/
    @ManyToOne
    @JoinColumn(name = "position_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotFound(action = NotFoundAction.IGNORE)
    private Position position;

    public User getUser() {
        return user;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getArchiveCode() {
        return archiveCode;
    }

    public void setArchiveCode(String archiveCode) {
        this.archiveCode = archiveCode;
    }
}
