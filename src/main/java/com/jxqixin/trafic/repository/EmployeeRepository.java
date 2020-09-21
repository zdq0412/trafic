package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;

public interface EmployeeRepository<ID extends Serializable> extends CommonRepository<Employee,ID> {
    /***
     * 根据手机号和企业id查找人员信息
     * @return
     */
    @Query("select e from Employee e where e.tel=?1 and e.org.id=?2")
    Employee findByTelAndOrgId(String tel,String orgId);
    /**
     * 根据身份证号查找人员信息
     * @param idnum
     * @return
     */
    @Query(nativeQuery = true,value = "select e.* from m003_employee e where idnum=?1 and org_id=?2")
    Employee findByIdnum(String idnum,String orgId);
    /**
     * 将企业信息设置为空
     * @param id
     */
    @Modifying
    @Query(nativeQuery = true,value = "update m003_employee set org_id=null where id=?1")
    void updateOrg2Null(String id);
    /**
     * 根据部门ID查找员工数量
     * @param deptId
     * @return
     */
    @Query(nativeQuery = true,value = "select count(e.id) from m003_employee e " +
            " inner join m002_department d on d.id=e.department_id where d.id=?1")
    Long findCountByDepartmentId(String deptId);
    /**
     * 根据职位ID查找员工数量
     * @param positionId
     * @return
     */
    @Query(nativeQuery = true,value = "select count(e.id) from m003_employee e " +
            " inner join m002_position d on d.id=e.position_id where d.id=?1")
    Long findCountByPositionId(String positionId);
    @Modifying
    @Query(nativeQuery = true,value = "update m003_employee set user_id=null where user_id=?1")
    void updateUser2NullByUserId(String id);
    @Query(nativeQuery = true,value = "select e.* from m003_employee e inner join t_user u on u.id=e.user_id where u.username=?1")
    Employee findByUsername(String username);
    @Modifying
    @Query(nativeQuery = true,value = "delete from m003_employee where idnum=?1 and org_id is null")
    void deleteByIdnumAndOrgIdIsNull(String idnum);
    @Query("select e from Employee e where e.tel=?1")
    Employee findByTel(String tel);
}
