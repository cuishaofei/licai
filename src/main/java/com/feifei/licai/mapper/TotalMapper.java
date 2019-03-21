package com.feifei.licai.mapper;

import com.feifei.licai.model.Project;
import com.feifei.licai.vo.ProjectVO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by cuishaofei on 2019/3/16.
 */
public interface TotalMapper {

    /**
     * 当前金额累计
     * @return
     */
    @Select("SELECT SUM(t2.currentMoney) FROM lc_project_currentmoney t2 LEFT JOIN (SELECT t1.pid,MAX(lastUpdateTime) lastUpdateTime FROM lc_project_currentmoney t1 GROUP BY t1.pid) t3 ON t2.pid = t3.pid AND t2.lastUpdateTime = t3.lastUpdateTime")
    double getTotalCurrentMoney();

    /**
     * 当前收益累计
     * @return
     */
    @Select("SELECT (SELECT SUM(t2.currentMoney) FROM lc_project_currentmoney t2 LEFT JOIN (SELECT t1.pid,MAX(lastUpdateTime) lastUpdateTime FROM lc_project_currentmoney t1 GROUP BY t1.pid) t3 ON t2.pid = t3.pid AND t2.lastUpdateTime = t3.lastUpdateTime) + (SELECT SUM(t1.optionMoney) AS totalProfit FROM lc_history t1) FROM DUAL")
    double getTotalProfit();


}
