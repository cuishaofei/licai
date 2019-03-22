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
public class HistoryVO {
    //id
    private int id;
    //1:投资,2:提现
    private String option;
    //操作金额,投资用负数,提现用正数
    private double optionMoney;
    //操作时间
    private String createTime;
    //pid
    private int pid;

}
