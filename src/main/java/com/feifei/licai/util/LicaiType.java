package com.feifei.licai.util;

/**
 * @author cuishaofei
 * @date 2019/4/23
 */
public enum LicaiType {

    /**
     * 股票型
     */
    GUPIAO(1, "股票型"),
    /**
     * P2P
     */
    P2P(2, "P2P"),
    /**
     * 债券型
     */
    ZHAIQUAN(3, "债券型"),
    /**
     * 货币型
     */
    HUOBI(4, "货币型"),
    /**
     * 定期理财
     */
    DINGQI(5, "定期理财"),
    /**
     * 养老基金
     */
    YANGLAO(6, "养老基金");

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
