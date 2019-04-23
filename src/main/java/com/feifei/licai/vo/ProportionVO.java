package com.feifei.licai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cuishaofei
 * @date 2019/4/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProportionVO {
    /**
     * 类型
     */
    private String type;
    /**
     * 应投金额占比
     */
    private String shouldProportion;
    /**
     * 实投金额占比
     */
    private String realProportion;
    /**
     * 应投金额
     */
    private Double shouldMoney;
    /**
     * 实投金额
     */
    private Double realMoney;
    /**
     * 应买入金额
     */
    private Double shouldBuyMoney;
    /**
     * 年化收益率
     */
    private String yearRate;

}
