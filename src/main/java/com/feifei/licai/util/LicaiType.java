package com.feifei.licai.util;

/**
 * @author cuishaofei
 * @date 2019/4/23
 */
public enum LicaiType {

    /**
     * 权益型
     */
    QY(1, "权益型"),
    /**
     * 稳健型
     */
    WJ(2, "稳健型"),
    /**
     * 其它
     */
    OTHER(3, "其它");

    public Integer code;
    public String desc;

    LicaiType(Integer code, String desc) {
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
        for (LicaiType enums : LicaiType.values()) {
            if (enums.getCode() == value) {
                return enums.getDesc();
            }
        }
        return "";
    }

}
