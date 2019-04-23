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
public class TotalVO {
    /**
     * 当前金额累计
     */
    private Double totalCurrentMoney;
    /**
     * 当前累计收益
     */
    private Double totalProfit;
    /**
     * 当前年化收益率累计
     */
    private String totalYearRate;
    /**
     * 年份用字符串表示
     */
    private String yearStr;
    /**
     * 当年的累计收益
     */
    private Double yearProfit;

}
