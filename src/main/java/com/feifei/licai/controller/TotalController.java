package com.feifei.licai.controller;

import com.feifei.licai.service.ProjectService;
import com.feifei.licai.service.TotalService;
import com.feifei.licai.vo.Contrast;
import com.feifei.licai.vo.DataGridResult;
import com.feifei.licai.vo.TotalVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author cuishaofei
 * @date 2019/4/23
 */
@RestController
@RequestMapping("/total")
public class TotalController {

    Log logger = LogFactory.getLog(TotalController.class);

    @Autowired
    private TotalService totalService;

    @Autowired
    private ProjectService projectService;

    @GetMapping
    @RequestMapping("/getTotal")
    public DataGridResult getTotal(){
        try {
            DataGridResult result = new DataGridResult();
            List<TotalVO> list = new ArrayList<TotalVO>();

            TotalVO total = new TotalVO();
            total.setTotalCurrentMoney(totalService.getTotalCurrentMoney());
            total.setTotalProfit(totalService.getTotalProfit());
            total.setTotalYearRate(totalService.getTotalYearRate());
            list.add(total);

            result.setTotal(1);
            result.setRows(list);
            return result;
        } catch (Exception e) {
            logger.error("发生异常", e);
        }
        return null;
    }

    @GetMapping
    @RequestMapping("/getProportion")
    public DataGridResult getProportion(){
        try {
            DataGridResult result = new DataGridResult();
            result.setTotal(3);
            result.setRows(totalService.getProportion());
            return result;
        } catch (Exception e) {
            logger.error("发生异常", e);
        }
        return null;
    }

    @GetMapping
    @RequestMapping("/getStrategy")
    public DataGridResult getStrategy(){
        try {
            DataGridResult result = new DataGridResult();
            List<Map<String,Object>> list = totalService.getStrategy();
            result.setTotal(list.size());
            result.setRows(list);
            return result;
        } catch (Exception e) {
            logger.error("发生异常", e);
        }
        return null;
    }

    @RequestMapping("/getYearProfitContrast")
    public List<Contrast> getYearProfitContrast(){
        List<Contrast> list = null;
        try {
            //从数据库获取数据传给页面展示
            list = totalService.getYearProfitContrast();
            return list;
        } catch (Exception e) {
            logger.error("发生异常", e);
        }
        return null;
    }

    @RequestMapping("/getTotalProfitContrast")
    public List<Contrast> getTotalProfitContrast(){
        List<Contrast> list = null;
        try {
            //从数据库获取数据传给页面展示
            list = totalService.getTotalProfitContrast();
            return list;
        } catch (Exception e) {
            logger.error("发生异常", e);
        }
        return null;
    }

    @GetMapping
    @RequestMapping("/getYearProfit")
    public DataGridResult getYearProfit(){
        try {
            DataGridResult result = new DataGridResult();

            List<TotalVO> list = totalService.getYearProfit();

            result.setTotal(list.size());
            result.setRows(list);
            return result;
        } catch (Exception e) {
            logger.error("发生异常", e);
        }
        return null;
    }
}
