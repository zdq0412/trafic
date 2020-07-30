package com.jxqixin.trafic.dto;
/**
 * 文件上传携带的额外信息
 */
public class UploadFileDto {
    private String id;
    /**上传的是什么文件，如：meetingTemplate 会议模板文件，
     * 约定如下：
     * meetingTemplate:会议模板文件
     * trainingTemplate:培训模板文件
     * securityCheckTemplate:安全检查模板文件
     * tankVehicleTemplate:罐式车辆检查模板文件
     * ruleTemplate:安全规章制度模板文件
     * responsibilityTemplate:责任书模板文件
     * dangerGoodsCheckTemplate:危险品自查模板文件
     * */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
