package com.feifei.licai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by cuishaofei on 2019/3/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    //id
    private int id;
    //投资平台
    private String name;
    //投资编码
    private String code;
    //类型
    private int type;
    //APP名称
    private String appName;
    //风险等级
    private int riskLevel;
    //备注
    private String mark;
    //累计收益
    private double allProfit;
    //当前金额
    private double currentMoney;
    //最后更新时间
    private Date lastUpdateTime;
}
