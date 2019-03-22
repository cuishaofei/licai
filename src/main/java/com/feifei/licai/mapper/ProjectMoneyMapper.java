package com.feifei.licai.mapper;

import com.feifei.licai.model.Project;
import com.feifei.licai.model.ProjectMoney;
import com.feifei.licai.vo.ProjectVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by cuishaofei on 2019/3/16.
 */
public interface ProjectMoneyMapper {

    @Insert("INSERT INTO lc_project_currentmoney(currentMoney,lastUpdateTime,pid) VALUES(#{currentMoney},#{lastUpdateTime},#{pid})")
    void addCurrentMoney(ProjectMoney projectMoney);

}
