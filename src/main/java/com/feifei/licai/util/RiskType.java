package com.feifei.licai.util;

/**
 * @author cuishaofei
 * @date 2019/4/23
 */
public enum RiskType {

    /**
     * 高
     */
    HIGH(1, "高"),
    /**
     * 中
     */
    MIDDLE(2, "中"),
    /**
     * 低
     */
    LOW(3, "低");

    public Integer code;
    public String desc;

    RiskType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 通过value取描述
     * @param value
     * @return
     */
    public static String getDescByValue(int value) {
        for (RiskType enums : RiskType.values()) {
            if (enums.getCode() == value) {
                return enums.getDesc();
            }
        }
        return "";
    }

}
