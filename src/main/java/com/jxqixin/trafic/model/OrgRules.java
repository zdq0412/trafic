package com.jxqixin.trafic.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 企业与安全规章制度关联表
 */
@Entity
@Table(name = "org_rules")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class OrgRules implements Serializable {
    @Id
    @GeneratedValue(generator = "id_gen")
    private String id;
    @ManyToOne
    @JoinColumn(name="org_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    private Org org;
    @ManyToOne
    @JoinColumn(name="rules_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Rules rules;
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

    public boolean isSended() {
        return sended;
    }

    public void setSended(boolean sended) {
        this.sended = sended;
    }

    public Rules getRules() {
        return rules;
    }

    public void setRules(Rules rules) {
        this.rules = rules;
    }
}
