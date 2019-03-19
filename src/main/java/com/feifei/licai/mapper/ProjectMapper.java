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
    @Select("SELECT t1.*,t1.currentMoney + (SELECT SUM(t2.optionMoney) FROM lc_history t2 WHERE t2.pid = t1.id) allProfit FROM lc_project t1 ORDER BY allProfit DESC")
    List<Project> getProjectList();

    @Update("UPDATE lc_project t1 SET t1.currentMoney = t1.currentMoney + #{currentMoney},lastUpdateTime = #{lastUpdateTime} WHERE id = #{id}")
    void IncreaseCurrentMoneyByID(Project project);

    @Update("UPDATE lc_project t1 SET t1.currentMoney = #{currentMoney},lastUpdateTime = #{lastUpdateTime} WHERE id = #{id}")
    void updateCurrentMoneyByID(Project project);

    @Select("SELECT SUM(t1.currentMoney) FROM lc_project t1 WHERE type = #{type}")
    double getCurrentMoneyByType(int type);

    //获取全部项目列表（包含每个项目的累计收益）
    @Select("SELECT t1.*,t1.currentMoney + (SELECT SUM(t2.optionMoney) FROM lc_history t2 WHERE t2.pid = t1.id) allProfit FROM lc_project t1 WHERE t1.type = #{type} ORDER BY t1.currentMoney DESC")
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


}
