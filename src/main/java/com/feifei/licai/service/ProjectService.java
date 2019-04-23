package com.feifei.licai.service;

import com.feifei.licai.mapper.HistoryMapper;
import com.feifei.licai.mapper.ProjectMapper;
import com.feifei.licai.model.History;
import com.feifei.licai.util.Constants;
import com.feifei.licai.util.DateTimeUtil;
import com.feifei.licai.util.LicaiType;
import com.feifei.licai.util.RiskType;
import com.feifei.licai.util.xirr.Transaction;
import com.feifei.licai.util.xirr.Xirr;
import com.feifei.licai.vo.HistoryVO;
import com.feifei.licai.vo.ProjectVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cuishaofei
 * @date 2019/4/23
 */
@Service
public class ProjectService{
    @Autowired
    private  ProjectMapper projectMapper;

    @Autowired
    private HistoryMapper historyMapper;

    Log logger = LogFactory.getLog(ProjectService.class);


    /**
     * 获取全部理财项目
     * @return
     */
    public List<ProjectVO> getProjectList() {
        List<ProjectVO> list = projectMapper.getProjectList(Constants.ORDER_CURRENT_MONEY);
        List<HistoryVO> allHistory = historyMapper.getHistoryList();
        List<ProjectVO> projectVOArrayList = new ArrayList<ProjectVO>();
        if(list != null && list.size() > 0){
           for(int i = 0;i<list.size();i++){
               ProjectVO project = list.get(i);
               //获取当前金额
               double currentMoney = project.getCurrentMoney();
               //获取当前ID
               int pid = project.getId();
               //计算年化收益率用明年的第一天
               List<HistoryVO> histories = null;
               if(allHistory != null && allHistory.size() > 0){
                   histories = new ArrayList<HistoryVO>();
                   for(int j = 0;j<allHistory.size();j++){
                       HistoryVO history = allHistory.get(j);
                       if(history.getPid().equals(project.getId())){
                           histories.add(history);
                       }
                   }
               }
               String yearRate = getYearRateByHistorys(histories,currentMoney);
               ProjectVO total = new ProjectVO(pid,project.getName(),project.getCode(),currentMoney,project.getYearProfit(),
                       project.getAllProfit(),yearRate, LicaiType.getDescByValue(Integer.parseInt(project.getType())),
                       project.getAppName(), RiskType.getDescByValue(Integer.parseInt(project.getRiskLevel())),
                       project.getMark(), project.getLastUpdateTime());
               projectVOArrayList.add(total);
           }
        }
        return projectVOArrayList;
    }

    /**
     * 根据历史明细计算年化收益率
     * @param histories
     * @param currentMoney
     * @return
     */
    private String getYearRateByHistorys(List<HistoryVO> histories,double currentMoney) {
        String yearRate = "";
        try {
            if(histories != null && histories.size() > 0){
                List<Transaction> transactions = new ArrayList<Transaction>();
                for(int i = 0;i < histories.size();i++){
                    HistoryVO history = histories.get(i);
                    Transaction transaction = new Transaction(history.getOptionMoney(),DateTimeUtil.formatStringtoDateTime(history.getCreateTime(),DateTimeUtil.FMT_YYYYMMDD));
                    transactions.add(transaction);
                }
                //添加最后一笔明细
                transactions.add(new Transaction(currentMoney,DateTimeUtil.formatDateTimetoString(new Date(),DateTimeUtil.FMT_YYYYMMDD)));
                double xirr = new Xirr(transactions).xirr();
                BigDecimal bg = new BigDecimal(xirr * 100).setScale(2, RoundingMode.UP);
                yearRate = bg.doubleValue() + "%";
            }
        }catch (Exception e){
            logger.error("发生异常", e);
        }
        return  yearRate;
    }

    /**
     * 获取该分类的总额
     * @param type
     * @return
     */
    public double getCurrentMoneyByType(int type) {
        return  projectMapper.getCurrentMoneyByType(type);
    }
}
