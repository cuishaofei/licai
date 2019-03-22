package com.feifei.licai.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * Created by cuishaofei on 2019/3/16.
 */
public interface TotalMapper {

    /**
     * 当前金额累计
     * @return
     */
    @Select("SELECT " +
            "    SUM(t2.currentMoney) currentMoney " +
            "FROM " +
            "    ( " +
            "        SELECT " +
            "            t1.currentMoney " +
            "        FROM " +
            "            ( " +
            "                SELECT " +
            "                    * " +
            "                FROM " +
            "                    lc_project_currentmoney " +
            "                ORDER BY " +
            "                    id DESC " +
            "            ) t1 " +
            "        GROUP BY " +
            "            t1.pid " +
            "    ) t2")
    double getTotalCurrentMoney();

    /**
     * 当前收益累计
     * @return
     */
    @Select("SELECT " +
            "    ( " +
            "        SELECT " +
            "            SUM(t2.currentMoney) currentMoney " +
            "        FROM " +
            "            ( " +
            "                SELECT " +
            "                    t1.currentMoney " +
            "                FROM " +
            "                    ( " +
            "                        SELECT " +
            "                            * " +
            "                        FROM " +
            "                            lc_project_currentmoney " +
            "                        ORDER BY " +
            "                            id DESC " +
            "                    ) t1 " +
            "                GROUP BY " +
            "                    t1.pid " +
            "            ) t2 " +
            "    ) + ( " +
            "        SELECT " +
            "            SUM(t3.optionMoney) AS totalProfit " +
            "        FROM " +
            "            lc_history t3 " +
            "    ) allProfit " +
            "FROM " +
            "    DUAL")
    double getTotalProfit();


}
