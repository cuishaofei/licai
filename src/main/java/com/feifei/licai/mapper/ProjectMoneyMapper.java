package com.feifei.licai.mapper;

import com.feifei.licai.model.Project;
import com.feifei.licai.model.ProjectMoney;
import com.feifei.licai.vo.ProjectVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author cuishaofei
 * @date 2019/4/23
 */
public interface ProjectMoneyMapper {

    /**
     * 添加当前金额
     * @param projectMoney
     */
    @Insert("INSERT INTO lc_project_currentmoney(currentMoney,lastUpdateTime,pid) VALUES(#{currentMoney},#{lastUpdateTime},#{pid})")
    void addCurrentMoney(ProjectMoney projectMoney);

}
