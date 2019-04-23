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
public class HistoryVO {
    /**
     * id
     */
    private Integer id;
    /**
     * 1:投资,2:提现
     */
    private String option;
    /**
     * 操作金额,投资用负数,提现用正数
     */
    private Double optionMoney;
    /**
     * 操作时间
     */
    private String createTime;
    /**
     * pid
     */
    private Integer pid;

}
