package com.feifei.licai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cuishaofei on 2019/3/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {
    //id
    private int id;
    //1:投资,2:提现
    private int option;
    //操作金额,投资用负数,提现用正数
    private double optionMoney;
    //操作时间
    private Date createTime;
    //pid
    private int pid;

}
