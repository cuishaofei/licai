package com.feifei.licai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by cuishaofei on 2019/3/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProportionVO {
    //类型
    private String type;
    //应投金额占比
    private String shouldProportion;
    //实投金额占比
    private String realProportion;
    //应投金额
    private double shouldMoney;
    //实投金额
    private double realMoney;
    //应买入金额
    private double shouldBuyMoney;
    //年化收益率
    private String yearRate;

}
