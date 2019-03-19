package com.feifei.licai.service;

import com.feifei.licai.mapper.HistoryMapper;
import com.feifei.licai.mapper.ProjectMapper;
import com.feifei.licai.model.History;
import com.feifei.licai.model.Project;
import com.feifei.licai.util.DateTimeUtil;
import com.feifei.licai.util.OptionType;
import com.feifei.licai.vo.HistoryVO;
import com.feifei.licai.vo.ProjectVO;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by cuishaofei on 2019/3/16.
 */

@Service
public class HistoryService {

    @Autowired
    private HistoryMapper historyMapper;

    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 根据pid获取单条记录的详情
     * @param pid
     * @return
     */
    public List<HistoryVO> getHistoryByID(int pid){
        List<HistoryVO> historyVOList = new ArrayList<HistoryVO>();
        List<History> histories = historyMapper.getHistoryByID(pid);
        if(histories != null && histories.size() > 0){
           for (int i = 0;i<histories.size();i++){
               History history = histories.get(i);
               String createTime = DateTimeUtil.formatDateTimetoString(history.getCreateTime());
               HistoryVO historyVO = new HistoryVO(history.getId(),OptionType.getDescByValue(history.getOption()),Math.abs(history.getOptionMoney()),createTime);
               historyVOList.add(historyVO);
           }
        }
        return  historyVOList;
    }

    /**
     * 增加流水记录
     * @param params
     * @return
     */
    @Transactional
    public boolean addHistory(Map<String, Object> params){
        boolean flag = false;
        int id = Integer.valueOf((String)params.get("id"));
        String optionMoneyStr = (String)params.get("optionMoney");
        String currentMoneyStr = (String)params.get("currentMoney");
        Integer option = Integer.valueOf((String)params.get("option"));
        //如果两个同时为空，则直接返回false
        if(StringUtils.isEmpty(optionMoneyStr) && StringUtils.isEmpty(currentMoneyStr)){
            return  flag;
        }
        //先新增明细，统计表的当前金额增加明细的操作金额
        if(!StringUtils.isEmpty(optionMoneyStr) && StringUtils.isEmpty(currentMoneyStr)){
            //新增明细
            History history = new History();
            history.setOption(option);
            double optionMoney = Double.parseDouble(optionMoneyStr);
            //如果是投资，则写入数据库应该是负数
            if(option == 1){
                history.setOptionMoney(-optionMoney);
            }else{
                history.setOptionMoney(optionMoney);
            }
            String optionDate =(String) params.get("optionDate");
            if(StringUtils.isEmpty(optionDate)){
                history.setCreateTime(new Date());
            }else {
                history.setCreateTime(DateTimeUtil.formatStringtoDateTime(optionDate));
            }
            history.setPid(id);
            historyMapper.addHistory(history);
            //更新统计表
            Project project = new Project();
            project.setId(id);
            if(option == 1){
                project.setCurrentMoney(optionMoney);
            }else {
                project.setCurrentMoney(-optionMoney);
            }
            project.setLastUpdateTime(new Date());
            projectMapper.IncreaseCurrentMoneyByID(project);
            flag = true;
            return  flag;
        }
        //仅更新统计表
        if(StringUtils.isEmpty(optionMoneyStr) && !StringUtils.isEmpty(currentMoneyStr)){
            //更新统计表
            Double currentMoney = Double.valueOf(currentMoneyStr);
            Project project = new Project();
            project.setId(id);
            project.setCurrentMoney(currentMoney);
            project.setLastUpdateTime(new Date());
            projectMapper.updateCurrentMoneyByID(project);
            flag = true;
            return  flag;
        }

        //先新增明细，再更新统计表
        if(!StringUtils.isEmpty(optionMoneyStr) && !StringUtils.isEmpty(currentMoneyStr)){
            //新增明细
            History history = new History();
            history.setOption(option);
            double optionMoney = Double.parseDouble(optionMoneyStr);
            //如果是投资，则写入数据库应该是负数
            if(option == 1){
                history.setOptionMoney(-optionMoney);
            }else{
                history.setOptionMoney(optionMoney);
            }
            String optionDate =(String) params.get("optionDate");
            if(StringUtils.isEmpty(optionDate)){
                history.setCreateTime(new Date());
            }else {
                history.setCreateTime(DateTimeUtil.formatStringtoDateTime(optionDate));
            }
            history.setPid(id);
            historyMapper.addHistory(history);
            //更新统计表
            Double currentMoney = Double.valueOf(currentMoneyStr);
            Project project = new Project();
            project.setId(id);
            project.setCurrentMoney(currentMoney);
            project.setLastUpdateTime(new Date());
            projectMapper.updateCurrentMoneyByID(project);
            flag = true;
            return  flag;
        }
        return  flag;
    }

    /**
     * 删除明细记录的方法
     * @param historyId
     * @return
     */
    public boolean deleteHistoryByID(int historyId){
        boolean flag = false;
        int rows = historyMapper.deleteHistoryByID(historyId);
        if(rows == 1){
            flag = true;
        }
        return flag;
    }
}