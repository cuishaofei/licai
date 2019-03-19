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
public class TotalVO {
    //当前金额累计
    private double totalCurrentMoney;
    //当前收益累计
    private double totalProfit;
    //当前年化收益率累计
    private String totalYearRate;

}
