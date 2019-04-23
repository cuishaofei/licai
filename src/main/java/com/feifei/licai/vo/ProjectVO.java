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
public class ProjectVO {
    /**
     * id
     */
    private Integer id;
    /**
     * 投资平台
     */
    private String name;
    /**
     * 投资编码
     */
    private String code;
    /**
     * 当前金额
     */
    private Double currentMoney;
    /**
     * 当年累计收益
     */
    private Double yearProfit;
    /**
     * 累计收益
     */
    private Double allProfit;
    /**
     * 年化收益率
     */
    private String yearRate;
    /**
     * 类型
     */
    private String type;
    /**
     * APP名称
     */
    private String appName;
    /**
     * 风险等级
     */
    private String riskLevel;
    /**
     * 备注
     */
    private String mark;
    /**
     * 最后更新时间
     */
    private String lastUpdateTime;
}
