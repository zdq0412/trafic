package com.jxqixin.trafic.vo;
import java.io.Serializable;
/**
 * 安全生产费用计划报表
 */
public class SafetyProductionCostPlanVo implements Serializable {
    private String id;
    private String name;
    private Double oneMonth=0d;
    private Double twoMonth=0d ;
    private Double threeMonth=0d ;
    private Double fourMonth =0d;
    private Double fiveMonth =0d;
    private Double sixMonth =0d;
    private Double sevenMonth =0d;
    private Double eightMonth =0d;
    private Double nineMonth =0d;
    private Double tenMonth =0d;
    private Double elevenMonth =0d;
    private Double twelveMonth =0d;

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

    public Double getOneMonth() {
        return oneMonth;
    }

    public void setOneMonth(Double oneMonth) {
        this.oneMonth = oneMonth;
    }

    public Double getTwoMonth() {
        return twoMonth;
    }

    public void setTwoMonth(Double twoMonth) {
        this.twoMonth = twoMonth;
    }

    public Double getThreeMonth() {
        return threeMonth;
    }

    public void setThreeMonth(Double threeMonth) {
        this.threeMonth = threeMonth;
    }

    public Double getFourMonth() {
        return fourMonth;
    }

    public void setFourMonth(Double fourMonth) {
        this.fourMonth = fourMonth;
    }

    public Double getFiveMonth() {
        return fiveMonth;
    }

    public void setFiveMonth(Double fiveMonth) {
        this.fiveMonth = fiveMonth;
    }

    public Double getSixMonth() {
        return sixMonth;
    }

    public void setSixMonth(Double sixMonth) {
        this.sixMonth = sixMonth;
    }

    public Double getSevenMonth() {
        return sevenMonth;
    }

    public void setSevenMonth(Double sevenMonth) {
        this.sevenMonth = sevenMonth;
    }

    public Double getEightMonth() {
        return eightMonth;
    }

    public void setEightMonth(Double eightMonth) {
        this.eightMonth = eightMonth;
    }

    public Double getNineMonth() {
        return nineMonth;
    }

    public void setNineMonth(Double nineMonth) {
        this.nineMonth = nineMonth;
    }

    public Double getTenMonth() {
        return tenMonth;
    }

    public void setTenMonth(Double tenMonth) {
        this.tenMonth = tenMonth;
    }

    public Double getElevenMonth() {
        return elevenMonth;
    }

    public void setElevenMonth(Double elevenMonth) {
        this.elevenMonth = elevenMonth;
    }

    public Double getTwelveMonth() {
        return twelveMonth;
    }

    public void setTwelveMonth(Double twelveMonth) {
        this.twelveMonth = twelveMonth;
    }
}
