package com.jxqixin.trafic.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 企业与法律法规文件关联表
 */
@Entity
@Table(name = "org_law")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class OrgLaw implements Serializable {
    @Id
    @GeneratedValue(generator = "id_gen")
    private String id;
    @ManyToOne
    @JoinColumn(name="org_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    private Org org;
    @ManyToOne
    @JoinColumn(name="law_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Law law;
    /**是否已企业发文通知*/
    private boolean sended;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public Law getLaw() {
        return law;
    }

    public void setLaw(Law law) {
        this.law = law;
    }

    public boolean isSended() {
        return sended;
    }

    public void setSended(boolean sended) {
        this.sended = sended;
    }
}
