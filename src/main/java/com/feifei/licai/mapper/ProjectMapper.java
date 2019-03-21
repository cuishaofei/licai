package com.feifei.licai.mapper;

import com.feifei.licai.model.Project;
import com.feifei.licai.vo.ProjectVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by cuishaofei on 2019/3/16.
 */
public interface  ProjectMapper {

    //获取全部项目列表（包含每个项目的累计收益）
    @Select("SELECT t1.*,(SELECT currentMoney FROM lc_project_currentmoney WHERE pid = t1.id AND lastUpdateTime = (SELECT MAX(lastUpdateTime) lastUpdateTime FROM lc_project_currentmoney WHERE pid = t1.id)) currentMoney,(SELECT MAX(lastUpdateTime) lastUpdateTime FROM lc_project_currentmoney WHERE pid = t1.id) lastUpdateTime,(SELECT currentMoney FROM lc_project_currentmoney WHERE pid = t1.id AND lastUpdateTime = (SELECT MAX(lastUpdateTime) FROM lc_project_currentmoney WHERE pid = t1.id)) + (SELECT SUM(t2.optionMoney) FROM lc_history t2 WHERE t2.pid = t1.id) allProfit FROM lc_project t1 ORDER BY allProfit DESC")
    List<Project> getProjectList();

    @Insert("INSERT INTO lc_project_currentmoney(currentMoney,lastUpdateTime,pid) VALUES(#{currentMoney},#{lastUpdateTime},#{id})")
    void addCurrentMoney(Project project);

    @Select("SELECT SUM(t4.currentMoney) currentMoney FROM (SELECT (SELECT currentMoney FROM lc_project_currentmoney t3 WHERE t3.pid = t1.id AND t3.lastUpdateTime = (SELECT MAX(lastUpdateTime) lastUpdateTime FROM lc_project_currentmoney t2 WHERE t2.pid = t1.id)) currentMoney FROM lc_project t1 WHERE t1.type = #{type}) t4")
    double getCurrentMoneyByType(int type);

    //获取全部项目列表（包含每个项目的累计收益）
    @Select("SELECT t1.*,(SELECT currentMoney FROM lc_project_currentmoney WHERE pid = t1.id AND lastUpdateTime = (SELECT MAX(lastUpdateTime) lastUpdateTime FROM lc_project_currentmoney WHERE pid = t1.id)) currentMoney,(SELECT MAX(lastUpdateTime) lastUpdateTime FROM lc_project_currentmoney WHERE pid = t1.id) lastUpdateTime,(SELECT currentMoney FROM lc_project_currentmoney WHERE pid = t1.id AND lastUpdateTime = (SELECT MAX(lastUpdateTime) FROM lc_project_currentmoney WHERE pid = t1.id)) + (SELECT SUM(t2.optionMoney) FROM lc_history t2 WHERE t2.pid = t1.id) allProfit FROM lc_project t1 WHERE t1.type = #{type} ORDER BY allProfit DESC")
    List<Project> getProjectListByType(int type);

    //获取全部项目列表（包含每个项目的累计收益）
    @Select({
            "<script>",
            "select",
            "t1.*",
            "from lc_project t1",
            "where t1.id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<Project> getProjectListByIds(@Param("ids") List<String> ids);

    @Select("SELECT currentMoney FROM lc_project_currentmoney WHERE pid = #{id} AND lastUpdateTime = (SELECT MAX(lastUpdateTime) FROM lc_project_currentmoney WHERE pid = #{id})")
    double getCurrentMoneyByPid(int pid);

}
