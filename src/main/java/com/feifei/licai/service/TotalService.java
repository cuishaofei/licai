package com.feifei.licai.service;

import com.feifei.licai.mapper.HistoryMapper;
import com.feifei.licai.mapper.ProjectMapper;
import com.feifei.licai.mapper.TotalMapper;
import com.feifei.licai.util.Constants;
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
 * @author cuishaofei
 * @date 2019/4/23
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
    @Value("${choose.ed}")
    private String ed;
    @Value("${choose.udt}")
    private String udt;
    @Value("${choose.robot}")
    private String robot;
    @Value("${choose.cow}")
    private String cow;
    @Value("${choose.ql}")
    private String ql;


    @Value("${per.zhishu}")
    private double zhishu;
    @Value("${per.p2p}")
    private double p2p;
    @Value("${per.zhaiquan}")
    private double zhaiquan;
    @Value("${per.huobi}")
    private double huobi;


    /**
     * 当前金额累计
     *
     * @return
     */
    public double getTotalCurrentMoney() {
        return totalMapper.getTotalCurrentMoney();
    }

    /**
     * 当前累计收益
     *
     * @return
     */
    public double getTotalProfit() {
        return totalMapper.getTotalProfit();
    }

    /**
     * 当前年化收益率累计
     *
     * @return
     */
    public String getTotalYearRate() {
        String yearRate = "";
        List<HistoryVO> histories = historyMapper.getHistoryList();
        List<ProjectVO> projects = projectMapper.getProjectList(Constants.ORDER_CURRENT_MONEY);
        List<Transaction> transactions = new ArrayList<Transaction>();
        for (int i = 0; i < histories.size(); i++) {
            HistoryVO history = histories.get(i);
            Transaction transaction = new Transaction(history.getOptionMoney(), DateTimeUtil.formatStringtoDateTime(history.getCreateTime(), DateTimeUtil.FMT_YYYYMMDD));
            transactions.add(transaction);
        }
        for (int i = 0; i < projects.size(); i++) {
            ProjectVO project = projects.get(i);
            transactions.add(new Transaction(project.getCurrentMoney(), DateTimeUtil.formatDateTimetoString(new Date(), DateTimeUtil.FMT_YYYYMMDD)));
        }
        double xirr = new Xirr(transactions).xirr();
        BigDecimal bg = new BigDecimal(xirr).multiply(new BigDecimal(String.valueOf(100))).setScale(2, RoundingMode.UP);
        yearRate = bg.doubleValue() + "%";
        return yearRate;
    }

    /**
     * 根据类型获取当前年化收益率累计
     *
     * @return
     */
    public String getTotalYearRate(int type) {
        String yearRate = "";
        List<HistoryVO> histories = historyMapper.getHistoryListByType(type);
        List<ProjectVO> projects = projectMapper.getProjectListByType(type);
        List<Transaction> transactions = new ArrayList<Transaction>();
        for (int i = 0; i < histories.size(); i++) {
            HistoryVO history = histories.get(i);
            Transaction transaction = new Transaction(history.getOptionMoney(), DateTimeUtil.formatStringtoDateTime(history.getCreateTime(), DateTimeUtil.FMT_YYYYMMDD));
            transactions.add(transaction);
        }
        for (int i = 0; i < projects.size(); i++) {
            ProjectVO project = projects.get(i);
            transactions.add(new Transaction(project.getCurrentMoney(), DateTimeUtil.formatDateTimetoString(new Date(), DateTimeUtil.FMT_YYYYMMDD)));
        }
        double xirr = new Xirr(transactions).xirr();
        BigDecimal bg = new BigDecimal(xirr).multiply(new BigDecimal(String.valueOf(100))).setScale(2, RoundingMode.UP);
        yearRate = bg.doubleValue() + "%";
        return yearRate;
    }

    /**
     * 根据类型获取当前年化收益率累计
     *
     * @return
     */
    public String getTotalYearRate(List<String> ids) {
        String yearRate = "";
        try {
            List<HistoryVO> histories = historyMapper.getHistoryListByIds(ids);
            List<ProjectVO> projects = projectMapper.getProjectListByIds(ids);
            List<Transaction> transactions = new ArrayList<Transaction>();
            for (int i = 0; i < histories.size(); i++) {
                HistoryVO history = histories.get(i);
                Transaction transaction = new Transaction(history.getOptionMoney(), DateTimeUtil.formatStringtoDateTime(history.getCreateTime(), DateTimeUtil.FMT_YYYYMMDD));
                transactions.add(transaction);
            }
            for (int i = 0; i < projects.size(); i++) {
                ProjectVO project = projects.get(i);
                transactions.add(new Transaction(project.getCurrentMoney(), DateTimeUtil.formatDateTimetoString(new Date(), DateTimeUtil.FMT_YYYYMMDD)));
            }
            double xirr = new Xirr(transactions).xirr();
            BigDecimal bg = new BigDecimal(xirr).multiply(new BigDecimal(String.valueOf(100))).setScale(2, RoundingMode.UP);
            yearRate = bg.doubleValue() + "%";
        } catch (Exception e) {
            logger.error("发生异常", e);
        }
        return yearRate;
    }



    /**
     * 理财收益汇总表数据
     * @return
     */
    public List<ProportionVO> getProportion(){
        List<ProportionVO> list = new ArrayList<ProportionVO>();
        //总额
        double totalCurrentMoney = getTotalCurrentMoney();

        // 应投金额
        BigDecimal zhishuShouldBD = new BigDecimal(String.valueOf(totalCurrentMoney)).multiply(new BigDecimal(String.valueOf(zhishu)));
        BigDecimal p2pShouldBD = new BigDecimal(String.valueOf(totalCurrentMoney)).multiply(new BigDecimal(String.valueOf(p2p)));
        BigDecimal zhaiquanShouldBD = new BigDecimal(String.valueOf(totalCurrentMoney)).multiply(new BigDecimal(String.valueOf(zhaiquan)));
        BigDecimal huobiShouldBD = new BigDecimal(String.valueOf(totalCurrentMoney)).multiply(new BigDecimal(String.valueOf(huobi)));

        //实投金额
        double zhishuReal = projectMapper.getCurrentMoneyByType(1);
        double p2pReal = projectMapper.getCurrentMoneyByType(2);
        double zhaiquanReal = projectMapper.getCurrentMoneyByType(3);
        double huobiReal = projectMapper.getCurrentMoneyByType(4);

        // 实投金额BigDecimal类型
        BigDecimal zhishuRealBD = new BigDecimal(String.valueOf(zhishuReal));
        BigDecimal p2pRealBD = new BigDecimal(String.valueOf(p2pReal));
        BigDecimal zhaiquanRealBD = new BigDecimal(String.valueOf(zhaiquanReal));
        BigDecimal huobiRealBD = new BigDecimal(String.valueOf(huobiReal));
        BigDecimal totalCurrentMoneyBD = new BigDecimal(String.valueOf(totalCurrentMoney));
        BigDecimal hundredBD = new BigDecimal(String.valueOf(100));

        //实投金额占比
        String zhishuRealPer = zhishuRealBD.divide(totalCurrentMoneyBD,4,RoundingMode.HALF_UP).multiply(hundredBD).doubleValue()  + "%";
        String p2pRealPer = p2pRealBD.divide(totalCurrentMoneyBD,4,RoundingMode.HALF_UP).multiply(hundredBD).doubleValue()  + "%";
        String zhaiquanRealPer = zhaiquanRealBD.divide(totalCurrentMoneyBD,4,RoundingMode.HALF_UP).multiply(hundredBD).doubleValue()  + "%";
        String huobiRealPer = huobiRealBD.divide(totalCurrentMoneyBD,4,RoundingMode.HALF_UP).multiply(hundredBD).doubleValue()  + "%";

        ProportionVO proportionVO1 = new ProportionVO("股票型",new BigDecimal(String.valueOf(zhishu)).multiply(hundredBD) + "%",zhishuRealPer,zhishuShouldBD.setScale(2, RoundingMode.UP).doubleValue(),zhishuRealBD.setScale(2, RoundingMode.UP).doubleValue(),zhishuShouldBD.subtract(zhishuRealBD).setScale(2, RoundingMode.UP).doubleValue(),getTotalYearRate(1));
        ProportionVO proportionVO2 = new ProportionVO("P2P",new BigDecimal(String.valueOf(p2p)).multiply(hundredBD) + "%",p2pRealPer,p2pShouldBD.setScale(2, RoundingMode.UP).doubleValue(),p2pRealBD.setScale(2, RoundingMode.UP).doubleValue(),p2pShouldBD.subtract(p2pRealBD).setScale(2, RoundingMode.UP).doubleValue(),getTotalYearRate(2));
        ProportionVO proportionVO3 = new ProportionVO("债券型",new BigDecimal(String.valueOf(zhaiquan)).multiply(hundredBD) + "%",zhaiquanRealPer,zhaiquanShouldBD.setScale(2, RoundingMode.UP).doubleValue(),zhaiquanRealBD.setScale(2, RoundingMode.UP).doubleValue(),zhaiquanShouldBD.subtract(zhaiquanRealBD).setScale(2, RoundingMode.UP).doubleValue(),getTotalYearRate(3));
        ProportionVO proportionVO4 = new ProportionVO("货币型",new BigDecimal(String.valueOf(huobi)).multiply(hundredBD) + "%",huobiRealPer,huobiShouldBD.setScale(2, RoundingMode.UP).doubleValue(),huobiRealBD.setScale(2, RoundingMode.UP).doubleValue(),huobiShouldBD.subtract(huobiRealBD).setScale(2, RoundingMode.UP).doubleValue(),getTotalYearRate(4));

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
    public List<Map<String,Object>>  getStrategy() {
        List<Map<String,Object>> list = new ArrayList<>();
        // 根据不同策略对应的项目ID查询年化收益率
        Map<String,Object> map1 = new HashMap(3);
        map1.put("name","银行螺丝钉");
        map1.put("value",getTotalYearRate(Arrays.asList(yhlsd.split(","))));
        map1.put("remark","每周二定投");
        Map<String,Object> map2 = new HashMap(3);
        map2.put("name","飞哥自选");
        map2.put("value",getTotalYearRate(Arrays.asList(feifei.split(","))));
        map2.put("remark","每周二定投");
        Map<String,Object> map3 = new HashMap(3);
        map3.put("name","机器人");
        map3.put("value",getTotalYearRate(Arrays.asList(robot.split(","))));
        map3.put("remark","随时投（基金组合、被动指数、智能算法）");
        Map<String,Object> map4 = new HashMap(3);
        map4.put("name","牛基宝<成长型>");
        map4.put("value",getTotalYearRate(Arrays.asList(cow.split(","))));
        map4.put("remark","随时投（基金组合、主动管理、投研实力）");
        Map<String,Object> map5 = new HashMap(3);
        map5.put("name","极简投资组合");
        map5.put("value",getTotalYearRate(Arrays.asList(jianqi.split(","))));
        map5.put("remark","随时投（基金组合、被动指数、动态平衡）");
        Map<String,Object> map6 = new HashMap(3);
        map6.put("name","长赢指数投资机会");
        map6.put("value",getTotalYearRate(Arrays.asList(ed.split(","))));
        map6.put("remark","等待发车信息");
        Map<String,Object> map7 = new HashMap(3);
        map7.put("name","U定投");
        map7.put("value",getTotalYearRate(Arrays.asList(udt.split(","))));
        map7.put("remark","每周二定投");
        Map<String,Object> map8 = new HashMap(3);
        map8.put("name","潜龙计划");
        map8.put("value",getTotalYearRate(Arrays.asList(ql.split(","))));
        map8.put("remark","每周二定投");

        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);
        list.add(map7);
        list.add(map8);
        return  list;
    }

    /**
     * 初始化各理财产品当年收益对比的图表
     * @return
     */
    public List<Contrast>  getYearProfitContrast() {
        List<Contrast> list = new ArrayList<Contrast>();
        List<ProjectVO> projectList = projectMapper.getProjectList(Constants.ORDER_ALL_PROFIT);
        for(ProjectVO project : projectList){
            Contrast contrast = new Contrast(project.getName(),project.getYearProfit());
            list.add(contrast);
        }
        return  list;
    }

    /**
     * 初始化各理财产品累计收益对比的图表
     * @return
     */
    public List<Contrast>  getTotalProfitContrast() {
        List<Contrast> list = new ArrayList<Contrast>();
        List<ProjectVO> projectList = projectMapper.getProjectList(Constants.ORDER_ALL_PROFIT);
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
        List<TotalVO> list = totalMapper.getYearProfit();
        for(TotalVO totalVO:list){
            List<Transaction> transactions = new ArrayList<Transaction>();
            int year = Integer.parseInt(totalVO.getYearStr());
            int lastYear = Integer.parseInt(totalVO.getYearStr()) - 1;
            Map<String,Integer> map = new HashMap<>();
            map.put("year1",year);
            map.put("year2",lastYear);
            List<Map<String,Object>> journalList =  totalMapper.getJournalBetweenYear(map);
            for(Map<String,Object> journalMap : journalList){
                transactions.add(new Transaction((Double) journalMap.get("optionMoney"), (String)journalMap.get("createTime")));
            }
            double xirr = new Xirr(transactions).xirr();
            BigDecimal bg = new BigDecimal(xirr).multiply(new BigDecimal(String.valueOf(100))).setScale(2, RoundingMode.UP);
            String totalYearRate = bg.doubleValue() + "%";
            totalVO.setTotalYearRate(totalYearRate);
        }
        return list;
    }
}
