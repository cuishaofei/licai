package com.feifei.licai.controller;

import com.feifei.licai.service.HistoryService;
import com.feifei.licai.vo.HistoryVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by cuishaofei on 2019/3/16.
 */
@RestController
@RequestMapping("/history")
public class HistoryController {

    Log logger = LogFactory.getLog(HistoryController.class);

    @Autowired
    private HistoryService historyService;

    /**
     * 分页查找
     **/
    @RequestMapping("/getHistoryByID")
    public List<HistoryVO> getHistoryByID(@RequestBody Map<String, Integer> params) {
        List<HistoryVO> histories = null;
        try {
            histories = historyService.getHistoryByID(params.get("id"));
            if (histories != null) {
                // 资源存在，返回200
                return histories;
            }
        } catch (Exception e) {
            logger.error("发生异常", e);
        }
        return histories;
    }

    /**
     * 分页查找
     **/
    @RequestMapping("/addHistory")
    public boolean addHistory(@RequestBody Map<String, Object> params) {
        boolean flag = false;
        try{
            flag = historyService.addHistory(params);
            return flag;
        }catch (Exception e){
            logger.error("发生异常", e);
        }
        return  flag;
    }

    /**
     * 分页查找
     **/
    @RequestMapping("/deleteHistoryByID")
    public boolean deleteHistoryByID(@RequestBody Map<String, Integer> params) {
        boolean flag = false;
        try{
            int historyId = params.get("historyId");
            flag = historyService.deleteHistoryByID(historyId);
            return flag;
        }catch (Exception e){
            logger.error("发生异常", e);
        }
        return  flag;
    }
}
