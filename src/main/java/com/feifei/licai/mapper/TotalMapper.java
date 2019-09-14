package com.feifei.licai.mapper;

import com.feifei.licai.vo.TotalVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author cuishaofei
 * @date 2019/4/23
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
            "                    lastUpdateTime DESC " +
            "            ) t1 " +
            "        GROUP BY " +
            "            t1.pid " +
            "    ) t2")
    double getTotalCurrentMoney();

    /**
     * 当前累计收益
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
            "                            lastUpdateTime DESC " +
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

    /**
     * 获取每年的总收益
     * @return
     */
    @Select("SELECT " +
            "    t6.yearStr, " +
            "    SUM(t6.yearProfit) yearProfit " +
            "FROM " +
            "    ( " +
            "        SELECT " +
            "            t1.pid, " +
            "            t1.lastUpdateTimeYear yearStr, " +
            "            t1.currentMoney + ( " +
            "                SELECT " +
            "                    SUM(t2.optionMoney) " +
            "                FROM " +
            "                    lc_history t2 " +
            "                WHERE " +
            "                    t2.pid = t1.pid " +
            "                AND t2.createTime <= t1.lastUpdateTime " +
            "            ) - IFNULL( " +
            "                ( " +
            "                    SELECT " +
            "                        t5.allYearProfit " +
            "                    FROM " +
            "                        ( " +
            "                            SELECT " +
            "                                t3.lastUpdateTimeYear, " +
            "                                t3.currentMoney + ( " +
            "                                    SELECT " +
            "                                        SUM(t4.optionMoney) " +
            "                                    FROM " +
            "                                        lc_history t4 " +
            "                                    WHERE " +
            "                                        t4.pid = t3.pid " +
            "                                    AND t4.createTime <= t3.lastUpdateTime " +
            "                                ) allYearProfit " +
            "                            FROM " +
            "                                ( " +
            "                                    SELECT " +
            "                                        currentMoney, " +
            "                                        pid, " +
            "                                        lastUpdateTime, " +
            "                                        DATE_FORMAT(lastUpdateTime, '%Y') lastUpdateTimeYear " +
            "                                    FROM " +
            "                                        lc_project_currentmoney " +
            "                                    ORDER BY " +
            "                                        lastUpdateTime DESC " +
            "                                ) t3 " +
            "                            GROUP BY " +
            "                                t3.pid, " +
            "                                DATE_FORMAT(t3.lastUpdateTime, '%Y') " +
            "                        ) t5 " +
            "                    WHERE " +
            "                        t5.lastUpdateTimeYear = t1.lastUpdateTimeYear - 1 " +
            "                ), " +
            "                0 " +
            "            ) yearProfit " +
            "        FROM " +
            "            ( " +
            "                SELECT " +
            "                    *, DATE_FORMAT(lastUpdateTime, '%Y') lastUpdateTimeYear " +
            "                FROM " +
            "                    lc_project_currentmoney " +
            "                ORDER BY " +
            "                    lastUpdateTime DESC " +
            "            ) t1 " +
            "        GROUP BY " +
            "            t1.pid, " +
            "            DATE_FORMAT(t1.lastUpdateTime, '%Y') " +
            "    ) t6 " +
            "GROUP BY " +
            "    t6.yearStr " +
            "ORDER BY " +
            "    t6.yearStr DESC")
    List<TotalVO>  getYearProfit();

    /**
     * 获取年区间的流水记录
     * @param map
     * @return
     */
    @Select(
            "SELECT " +
                    "	- t2.currentMoney optionMoney, " +
                    "	date_format(t2.lastUpdateTime,'%Y-%m-%d') createTime " +
                    "FROM " +
                    "	( " +
                    "		SELECT " +
                    "			t1.currentMoney, " +
                    "			t1.pid, " +
                    "			t1.lastUpdateTime " +
                    "		FROM " +
                    "			lc_project_currentmoney t1 " +
                    "		WHERE " +
                    "			DATE_FORMAT(t1.lastUpdateTime, '%Y') = #{year2} " +
                    "		ORDER BY " +
                    "			t1.lastUpdateTime DESC " +
                    "	) t2 " +
                    "GROUP BY " +
                    "	t2.pid " +
                    "UNION ALL " +
                    "	SELECT " +
                    "		t1.optionMoney optionMoney, " +
                    "		date_format(t1.createTime,'%Y-%m-%d') createTime " +
                    "	FROM " +
                    "		lc_history t1 " +
                    "	WHERE " +
                    "		DATE_FORMAT(t1.createTime, '%Y') = #{year1} " +
                    "	UNION ALL " +
                    "SELECT " +
                    "	t2.currentMoney, " +
                    "	date_format( " +
                    "		NOW(), " +
                    "		'%Y-%m-%d' " +
                    "	) createTime " +
                    "FROM " +
                    "	( " +
                    "		SELECT " +
                    "			t1.currentMoney, " +
                    "			t1.pid, " +
                    "			t1.lastUpdateTime " +
                    "		FROM " +
                    "			lc_project_currentmoney t1 " +
                    "		WHERE " +
                    "			DATE_FORMAT(t1.lastUpdateTime, '%Y') =  #{year1} " +
                    "		ORDER BY " +
                    "			t1.lastUpdateTime DESC " +
                    "	) t2 " +
                    "GROUP BY " +
                    "	t2.pid "
    )
    List<Map<String,Object>> getJournalBetweenYear(Map<String,Integer> map);
}
