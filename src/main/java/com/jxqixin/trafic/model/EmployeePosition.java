package com.jxqixin.trafic.model;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name="employee_position")
@GenericGenerator(name = "id_gen",strategy = "uuid")
public class EmployeePosition implements Serializable {
	private String id;
	private Employee employee;
	private Position position;
	@Id
	@GeneratedValue(generator = "id_gen")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="employee_id",foreignKey= @ForeignKey(name="none",value=ConstraintMode.NO_CONSTRAINT))
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	@ManyToOne
	@JoinColumn(name="position_id",foreignKey= @ForeignKey(name="none",value=ConstraintMode.NO_CONSTRAINT))
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
