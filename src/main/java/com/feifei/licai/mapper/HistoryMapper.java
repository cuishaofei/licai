package com.feifei.licai.mapper;

import com.feifei.licai.model.History;
import com.feifei.licai.model.Project;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by cuishaofei on 2019/3/16.
 */
public interface  HistoryMapper {

    /**
     * 根据ID获取历史明细
     * @param pid
     * @return
     */
    @Select("SELECT t1.* FROM lc_history t1 WHERE t1.pid = #{pid} ORDER BY t1.createTime DESC")
    List<History> getHistoryByID(int pid);

    /**
     * 根据ID获取历史明细
     * @param history
     * @return
     */
    @Insert("INSERT INTO `lc_history` (`option`, `optionMoney`, `createTime`, `pid`) VALUES(#{option},#{optionMoney},#{createTime},#{pid})")
    void addHistory(History history);

    /**
     * 根据ID获取历史明细
     * @param historyId
     * @return
     */
    @Delete("DELETE FROM lc_history WHERE id = #{historyId}")
    int deleteHistoryByID(int historyId);

    /**
     * 获取全部明细
     * @return
     */
    @Select("SELECT t1.* FROM lc_history t1 ORDER BY t1.createTime")
    List<History> getHistoryList();

    /**
     * 获取全部明细
     * @return
     */
    @Select("SELECT t1.* FROM lc_history t1,lc_project t2 WHERE t1.pid = t2.id AND t2.type = #{type} ORDER BY t1.createTime")
    List<History> getHistoryListByType(int type);

    /**
     * 获取全部明细
     * @return
     */
    @Select({
            "<script>",
            "select",
            "t2.*",
            "from lc_project t1,lc_history t2",
            "where t1.id = t2.pid AND t1.id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<History> getHistoryListByIds(@Param("ids") List<String> ids);

}
