package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.DangerGoodsCheckDetailRecord;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.Date;

public interface DangerGoodsCheckDetailRecordRepository<ID extends Serializable> extends CommonRepository<DangerGoodsCheckDetailRecord,ID> {
    /**
     * 分析所有记录
     * @return
     */
    @Query(nativeQuery = true,value ="select c.name name,count(record.id) total, ifnull(sum(case when record.rectification=1 then 1 else 0 end),0) rectificationCount," +
            "ifnull(sum(case when record.rectification=1 then 1 else 0 end)/count(record.id)*100,0) rate,ifnull(sum(record.rectificationFund),0) fund " +
            " from m024_danger_goods_check_detial_record record right join category c on c.id=record.severity_id" +
            " left join org o on o.id=record.org_id  where c.type='隐患排查严重程度'" +
            " group by c.name")
    Object[] analysisAll(String orgId);

    /**
     * 分析
     * @param checkDateFrom
     * @return
     */
    @Query(nativeQuery = true,value = "select c.name name,count(record.id) total, ifnull(sum(case when record.rectification=1 then 1 else 0 end),0) rectificationCount," +
            "ifnull(sum(case when record.rectification=1 then 1 else 0 end)/count(record.id)*100,0) rate,ifnull(sum(record.rectificationFund),0) fund " +
            "from m024_danger_goods_check_detial_record record right join category c on c.id=record.severity_id" +
            " left join org o on o.id=record.org_id " +
            " where record.checkDate>=?1  and c.type='隐患排查严重程度' group by c.name")
    Object[] analysisFrom(Date checkDateFrom,String orgId);
    /**
     * 分析
     * @param checkDateTo
     * @return
     */
    @Query(nativeQuery = true,value = "select c.name name,count(record.id) total, ifnull(sum(case when record.rectification=1 then 1 else 0 end),0) rectificationCount," +
            "ifnull(sum(case when record.rectification=1 then 1 else 0 end)/count(record.id)*100,0) rate,ifnull(sum(record.rectificationFund),0) fund " +
            "from m024_danger_goods_check_detial_record record right join category c on c.id=record.severity_id" +
            " left join org o on o.id=record.org_id " +
            " where record.checkDate<=?1  and c.type='隐患排查严重程度' group by c.name")
    Object[] analysisTo(Date checkDateTo,String orgId);
    /**
     * 分析
     * @param checkDateTo
     * @return
     */
    @Query(nativeQuery = true,value = "select c.name name,count(record.id) total, ifnull(sum(case when record.rectification=1 then 1 else 0 end),0) rectificationCount," +
            "ifnull(sum(case when record.rectification=1 then 1 else 0 end)/count(record.id)*100,0) rate,ifnull(sum(record.rectificationFund),0) fund " +
            "from m024_danger_goods_check_detial_record record right join category c on c.id=record.severity_id" +
            " left join org o on o.id=record.org_id " +
            " where record.checkDate<=?2 and record.checkDate>=?1 and c.type='隐患排查严重程度' group by c.name")
    Object[] analysisBetween(Date checkDateFrom, Date checkDateTo,String orgId);
    /**
     * 统计所有记录
     * @return
     */
    @Query(nativeQuery = true,value ="select c.name name,count(record.id) total, ifnull(sum(case when record.rectification=1 then 1 else 0 end),0) rectificationCount," +
            "ifnull(sum(case when record.rectification=1 then 1 else 0 end)/count(record.id)*100,0) rate,ifnull(sum(record.rectificationFund),0) fund " +
            " from m024_danger_goods_check_detial_record record right join category c on c.id=record.reason_category_id" +
            " left join org o on o.id=record.org_id  where c.type='隐患排查原因类别'" +
            " group by c.name")
    Object[] statisticsAll(String orgId);

    /**
     * 统计
     * @param checkDateFrom
     * @return
     */
    @Query(nativeQuery = true,value = "select c.name name,count(record.id) total, ifnull(sum(case when record.rectification=1 then 1 else 0 end),0) rectificationCount," +
            "ifnull(sum(case when record.rectification=1 then 1 else 0 end)/count(record.id)*100,0) rate,ifnull(sum(record.rectificationFund),0) fund " +
            "from m024_danger_goods_check_detial_record record right join category c on c.id=record.reason_category_id" +
            " left join org o on o.id=record.org_id " +
            " where record.checkDate>=?1 and c.type='隐患排查原因类别' group by c.name")
    Object[] statisticsFrom(Date checkDateFrom,String orgId);
    /**
     * 统计
     * @param checkDateTo
     * @return
     */
    @Query(nativeQuery = true,value = "select c.name name,count(record.id) total, ifnull(sum(case when record.rectification=1 then 1 else 0 end),0) rectificationCount," +
            "  ifnull(sum(case when record.rectification=1 then 1 else 0 end)/count(record.id)*100,0) rate,ifnull(sum(record.rectificationFund),0) fund " +
            "   from m024_danger_goods_check_detial_record record right join category c on c.id=record.reason_category_id" +
            " left join org o on o.id=record.org_id " +
            " where record.checkDate<=?1 and c.type='隐患排查原因类别' group by c.name")
    Object[] statisticsTo(Date checkDateTo,String orgId);
    /**
     * 统计
     * @param checkDateTo
     * @return
     */
    @Query(nativeQuery = true,value = "select c.name name,count(record.id) total, ifnull(sum(case when record.rectification=1 then 1 else 0 end),0) rectificationCount," +
            "ifnull(sum(case when record.rectification=1 then 1 else 0 end)/count(record.id)*100,0) rate,ifnull(sum(record.rectificationFund),0) fund " +
            "from m024_danger_goods_check_detial_record record right join category c on c.id=record.reason_category_id" +
            " left join org o on o.id=record.org_id " +
            " where record.checkDate<=?2 and record.checkDate>=?1 and c.type='隐患排查原因类别' group by c.name")
    Object[] statisticsBetween(Date checkDateFrom, Date checkDateTo,String orgId);
}
