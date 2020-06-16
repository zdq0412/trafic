package com.jxqixin.trafic.model;

import javax.persistence.*;
@Entity
@Table(name="ROLE_POWER")
public class RolePower {
	private Long id;
	private Power power;
	private Role role;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="POWER_ID",foreignKey= @ForeignKey(name="none",value=ConstraintMode.NO_CONSTRAINT))
	public Power getPower() {
		return power;
	}
	public void setPower(Power power) {
		this.power = power;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ROLE_ID",foreignKey= @ForeignKey(name="none",value=ConstraintMode.NO_CONSTRAINT))
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
}
