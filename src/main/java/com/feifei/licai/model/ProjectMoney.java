package com.feifei.licai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author cuishaofei
 * @date 2019/4/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMoney {
    /**
     * id
     */
    private int id;
    /**
     * 当前金额
     */
    private double currentMoney;
    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;
    /**
     * pid
     */
    private int pid;
}
