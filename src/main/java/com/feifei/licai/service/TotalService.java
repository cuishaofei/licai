package com.feifei.licai.service;

import com.feifei.licai.mapper.HistoryMapper;
import com.feifei.licai.mapper.ProjectMapper;
import com.feifei.licai.mapper.TotalMapper;
import com.feifei.licai.model.History;
import com.feifei.licai.util.DateTimeUtil;
import com.feifei.licai.util.xirr.Transaction;
import com.feifei.licai.util.xirr.Xirr;
import com.feifei.licai.vo.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by cuishaofei on 2019/3/16.
 */

@Service
public class TotalService {

    Log logger = LogFactory.getLog(TotalService.class);

    @Autowired
    private TotalMapper totalMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private HistoryMapper historyMapper;

    @Value("${choose.jianqi}")
    private String jianqi;
    @Value("${choose.yhlsd}")
    private String yhlsd;
    @Value("${choose.feifei}")
    private String feifei;


    @Value("${per.zhishu}")
    private double zhishu;
    @Value("${per.p2p}")
    private double p2p;
    @Value("${per.zhaiquan}")
    private double zhaiquan;
    @Value("${per.huobi}")
    private double huobi;


    /**
     *当前金额累计
     * @return
     */
    public double getTotalCurrentMoney(){
        return  totalMapper.getTotalCurrentMoney();
    }

    /**
     *当前收益累计
     * @return
     */
    public double getTotalProfit(){
        return  totalMapper.getTotalProfit();
    }

    /**
     * 当前年化收益率累计
     * @return
     */
    public String getTotalYearRate(){
        String yearRate = "";
        List<HistoryVO> histories = historyMapper.getHistoryList();
        List<ProjectVO> projects = projectMapper.getProjectList();
        List<Transaction> transactions = new ArrayList<Transaction>();
        for(int i = 0;i<histories.size();i++){
            HistoryVO history = histories.get(i);
            Transaction transaction = new Transaction(history.getOptionMoney(),DateTimeUtil.formatStringtoDateTime(history.getCreateTime(),DateTimeUtil.FMT_yyyyMMdd));
            transactions.add(transaction);
        }
        for(int i = 0;i<projects.size();i++){
            ProjectVO project = projects.get(i);
            transactions.add(new Transaction(project.getCurrentMoney(),DateTimeUtil.formatDateTimetoString(new Date(),DateTimeUtil.FMT_yyyyMMdd)));
        }
        double xirr = new Xirr(transactions).xirr();
        BigDecimal bg = new BigDecimal(xirr * 100).setScale(2, RoundingMode.UP);
        yearRate = bg.doubleValue() + "%";
        return  yearRate;
    }

    /**
     * 根据类型获取当前年化收益率累计
     * @return
     */
    public String getTotalYearRate(int type){
        String yearRate = "";
        List<HistoryVO> histories = historyMapper.getHistoryListByType(type);
        List<ProjectVO> projects = projectMapper.getProjectListByType(type);
        List<Transaction> transactions = new ArrayList<Transaction>();
        for(int i = 0;i<histories.size();i++){
            HistoryVO history = histories.get(i);
            Transaction transaction = new Transaction(history.getOptionMoney(),DateTimeUtil.formatStringtoDateTime(history.getCreateTime(),DateTimeUtil.FMT_yyyyMMdd));
            transactions.add(transaction);
        }
        for(int i = 0;i<projects.size();i++){
            ProjectVO project = projects.get(i);
            transactions.add(new Transaction(project.getCurrentMoney(),DateTimeUtil.formatDateTimetoString(new Date(),DateTimeUtil.FMT_yyyyMMdd)));
        }
        double xirr = new Xirr(transactions).xirr();
        BigDecimal bg = new BigDecimal(xirr * 100).setScale(2, RoundingMode.UP);
        yearRate = bg.doubleValue() + "%";
        return  yearRate;
    }

    /**
     * 根据类型获取当前年化收益率累计
     * @return
     */
    public String getTotalYearRate(List<String> ids){
        String yearRate = "";
        List<HistoryVO> histories = historyMapper.getHistoryListByIds(ids);
        List<ProjectVO> projects = projectMapper.getProjectListByIds(ids);
        List<Transaction> transactions = new ArrayList<Transaction>();
        for(int i = 0;i<histories.size();i++){
            HistoryVO history = histories.get(i);
            Transaction transaction = new Transaction(history.getOptionMoney(),DateTimeUtil.formatStringtoDateTime(history.getCreateTime(),DateTimeUtil.FMT_yyyyMMdd));
            transactions.add(transaction);
        }
        for(int i = 0;i<projects.size();i++){
            ProjectVO project = projects.get(i);
            transactions.add(new Transaction(project.getCurrentMoney(),DateTimeUtil.formatDateTimetoString(new Date(),DateTimeUtil.FMT_yyyyMMdd)));
        }
        double xirr = new Xirr(transactions).xirr();
        BigDecimal bg = new BigDecimal(xirr * 100).setScale(2, RoundingMode.UP);
        yearRate = bg.doubleValue() + "%";
        return  yearRate;
    }

    /**
     * 理财收益汇总表数据
     * @return
     */
    public List<ProportionVO> getProportion(){
        List<ProportionVO> list = new ArrayList<ProportionVO>();
        //总额
        double totalCurrentMoney = getTotalCurrentMoney();

        //应投金额
        double zhishuShould = totalCurrentMoney * zhishu;
        double p2pShould  = totalCurrentMoney * p2p;
        double zhaiquanShould  = totalCurrentMoney * zhaiquan;
        double huobiShould = totalCurrentMoney * huobi;

        //实投金额
        double zhishuReal = projectMapper.getCurrentMoneyByType(1);
        double p2pReal = projectMapper.getCurrentMoneyByType(2);
        double zhaiquanReal = projectMapper.getCurrentMoneyByType(3);
        double huobiReal = projectMapper.getCurrentMoneyByType(4);

        //实投金额占比
        String zhishuRealPer = new BigDecimal(zhishuReal / totalCurrentMoney * 100).setScale(2, RoundingMode.UP).doubleValue()  + "%";
        String p2pRealPer = new BigDecimal(p2pReal / totalCurrentMoney * 100).setScale(2, RoundingMode.UP).doubleValue()  + "%";
        String zhaiquanRealPer = new BigDecimal(zhaiquanReal / totalCurrentMoney * 100).setScale(2, RoundingMode.UP).doubleValue()  + "%";
        String huobiRealPer = new BigDecimal(huobiReal / totalCurrentMoney * 100).setScale(2, RoundingMode.UP).doubleValue()  + "%";

        ProportionVO proportionVO1 = new ProportionVO("指数型",zhishu * 100 + "%",zhishuRealPer,new BigDecimal(zhishuShould).setScale(2, RoundingMode.UP).doubleValue(),new BigDecimal(zhishuReal).setScale(2, RoundingMode.UP).doubleValue(),new BigDecimal(zhishuShould-zhishuReal).setScale(2, RoundingMode.UP).doubleValue(),getTotalYearRate(1));
        ProportionVO proportionVO2 = new ProportionVO("P2P",p2p * 100 + "%",p2pRealPer,new BigDecimal(p2pShould).setScale(2, RoundingMode.UP).doubleValue(),new BigDecimal(p2pReal).setScale(2, RoundingMode.UP).doubleValue(),new BigDecimal(p2pShould-p2pReal).setScale(2, RoundingMode.UP).doubleValue(),getTotalYearRate(2));
        ProportionVO proportionVO3 = new ProportionVO("债券型",zhaiquan * 100 + "%",zhaiquanRealPer,new BigDecimal(zhaiquanShould).setScale(2, RoundingMode.UP).doubleValue(),new BigDecimal(zhaiquanReal).setScale(2, RoundingMode.UP).doubleValue(),new BigDecimal(zhaiquanShould-zhaiquanReal).setScale(2, RoundingMode.UP).doubleValue(),getTotalYearRate(3));
        ProportionVO proportionVO4 = new ProportionVO("货币型",huobi * 100 + "%",huobiRealPer,new BigDecimal(huobiShould).setScale(2, RoundingMode.UP).doubleValue(),new BigDecimal(huobiReal).setScale(2, RoundingMode.UP).doubleValue(),new BigDecimal(huobiShould-huobiReal).setScale(2, RoundingMode.UP).doubleValue(),getTotalYearRate(4));

        list.add(proportionVO1);
        list.add(proportionVO2);
        list.add(proportionVO3);
        list.add(proportionVO4);

        return  list;
    }

    /**
     * 获取投资策略
     * @return
     */
    public Map<String,Object>  getStrategy() {
        Map<String,Object> map = new HashMap();
        //根据不同策略对应的项目ID查询年化收益率
        map.put("jq",getTotalYearRate(Arrays.asList(jianqi.split(","))));
        map.put("yhlsd",getTotalYearRate(Arrays.asList(yhlsd.split(","))));
        map.put("feifei",getTotalYearRate(Arrays.asList(feifei.split(","))));
        return  map;
    }

    /**
     * 各理财产品收益对比的图表
     * @return
     */
    public List<Contrast>  getContrast() {
        List<Contrast> list = new ArrayList<Contrast>();
        List<ProjectVO> projectList = projectMapper.getProjectList();
        for(ProjectVO project : projectList){
            Contrast contrast = new Contrast(project.getName(),project.getAllProfit());
            list.add(contrast);
        }
        return  list;
    }

    /**
     * 获取每年的总收益
     * @return
     */
    public List<TotalVO>  getYearProfit() {
        return totalMapper.getYearProfit();
    }
}
