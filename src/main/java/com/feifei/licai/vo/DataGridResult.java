package com.feifei.licai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author cuishaofei
 * @date 2019/4/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataGridResult {
    /**
     * 适配datagride的总条数
     */
    private int total;
    /**
     * datagride的数据
     */
    private List<?> rows;
}
