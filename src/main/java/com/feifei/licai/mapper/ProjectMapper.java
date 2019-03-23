package com.feifei.licai.mapper;

import com.feifei.licai.vo.ProjectVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by cuishaofei on 2019/3/16.
 */
public interface  ProjectMapper {

    //获取全部项目列表（包含每个项目的累计收益）
    @Select("SELECT t8.*,t8.allProfit - t8.previousYearAllProfit yearProfit FROM ( " +
            "SELECT " +
            "    t2.*,t3.currentMoney, " +
            "    t3.lastUpdateTime, " +
            "    t3.currentMoney + IFNULL( " +
            "        ( " +
            "            SELECT " +
            "                SUM(t4.optionMoney) " +
            "            FROM " +
            "                lc_history t4 " +
            "            WHERE " +
            "                t4.pid = t2.id " +
            "        ), " +
            "        0 " +
            "    ) allProfit, " +
            "    IFNULL( " +
            "        ( " +
            "            SELECT " +
            "                t7.previousYearAllProfit " +
            "            FROM " +
            "                ( " +
            "                    SELECT " +
            "                        t5.pid, " +
            "                        t5.currentMoney + ( " +
            "                            SELECT " +
            "                                SUM(t6.optionMoney) " +
            "                            FROM " +
            "                                lc_history t6 " +
            "                            WHERE " +
            "                                t5.pid = t6.pid " +
            "                            AND t6.createTime < t5.lastUpdateTime " +
            "                        ) previousYearAllProfit " +
            "                    FROM " +
            "                        ( " +
            "                            SELECT " +
            "                                pid, " +
            "                                currentMoney, " +
            "                                lastUpdateTime " +
            "                            FROM " +
            "                                lc_project_currentmoney " +
            "                            WHERE " +
            "                                lastUpdateTime < ( " +
            "                                    SELECT " +
            "                                        DATE_SUB( " +
            "                                            CURDATE(), " +
            "                                            INTERVAL dayofyear(now()) - 1 DAY " +
            "                                        ) " +
            "                                ) " +
            "                            ORDER BY " +
            "                                lastUpdateTime DESC " +
            "                        ) t5 " +
            "                    GROUP BY " +
            "                        t5.pid " +
            "                ) t7 " +
            "            WHERE " +
            "                t2.id = t7.pid " +
            "        ), " +
            "        0 " +
            "    ) previousYearAllProfit " +
            "FROM " +
            "    lc_project t2, " +
            "    ( " +
            "        SELECT " +
            "            t1.* " +
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
            "    ) t3 " +
            "WHERE " +
            "    t2.id = t3.pid " +
            ") t8 " +
            "ORDER BY " +
            "    t8.allProfit DESC")
    List<ProjectVO> getProjectList();

    @Select("SELECT " +
            "    SUM(t3.currentMoney) " +
            "FROM " +
            "    lc_project t2, " +
            "    ( " +
            "        SELECT " +
            "            t1.* " +
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
            "    ) t3 " +
            "WHERE " +
            "    t2.id = t3.pid " +
            "AND t2.type = #{type}")
    double getCurrentMoneyByType(int type);

    //获取全部项目列表（包含每个项目的累计收益）
    @Select("SELECT " +
            "    t2.*, t3.currentMoney, " +
            "    t3.lastUpdateTime, " +
            "    t3.currentMoney + IFNULL( " +
            "        ( " +
            "            SELECT " +
            "                SUM(t4.optionMoney) " +
            "            FROM " +
            "                lc_history t4 " +
            "            WHERE " +
            "                t4.pid = t2.id " +
            "        ), " +
            "        0 " +
            "    ) allProfit " +
            "FROM " +
            "    lc_project t2, " +
            "    ( " +
            "        SELECT " +
            "            t1.* " +
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
            "    ) t3 " +
            "WHERE " +
            "    t2.id = t3.pid AND t2.type = #{type} " +
            "ORDER BY " +
            "    allProfit DESC")
    List<ProjectVO> getProjectListByType(int type);

    //获取全部项目列表（包含每个项目的累计收益）
    @Select({
            "<script>",
            "SELECT " ,
            "    t2.*, t3.currentMoney, " ,
            "    t3.lastUpdateTime, " ,
            "    t3.currentMoney + ( " ,
            "        SELECT " ,
            "            SUM(t4.optionMoney) " ,
            "        FROM " ,
            "            lc_history t4 " ,
            "        WHERE " ,
            "            t4.pid = t2.id " ,
            "    ) allProfit " ,
            "FROM " ,
            "    lc_project t2, " ,
            "    ( " ,
            "        SELECT " ,
            "            t1.* " ,
            "        FROM " ,
            "            ( " ,
            "                SELECT " ,
            "                    * " ,
            "                FROM " ,
            "                    lc_project_currentmoney " ,
            "                ORDER BY " ,
            "                    lastUpdateTime DESC " ,
            "            ) t1 " ,
            "        GROUP BY " ,
            "            t1.pid " ,
            "    ) t3 " ,
            "WHERE " ,
            "    t2.id = t3.pid AND t2.id in " ,
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "ORDER BY " ,
            "    allProfit DESC",
            "</script>"
    })
    List<ProjectVO> getProjectListByIds(@Param("ids") List<String> ids);

    @Select("SELECT " +
            "    t1.currentMoney " +
            "FROM " +
            "    ( " +
            "        SELECT " +
            "            * " +
            "        FROM " +
            "            lc_project_currentmoney " +
            "        WHERE " +
            "            pid = #{pid} " +
            "        ORDER BY " +
            "            lastUpdateTime DESC " +
            "    ) t1 " +
            "GROUP BY " +
            "    t1.pid")
    double getCurrentMoneyByPid(int pid);

}
