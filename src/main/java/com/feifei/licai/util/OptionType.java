package com.feifei.licai.util;

/**
 * Created by cuishaofei on 2019/3/16.
 */
public enum OptionType {

    INPUT(1, "投资"),OUTPUT(2, "提现");

    public Integer code;
    public String desc;

    OptionType(Integer code, String desc) {
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
        for (OptionType enums : OptionType.values()) {
            if (enums.getCode() == value) {
                return enums.getDesc();
            }
        }
        return "";
    }

}
