package com.feifei.licai.controller;

import com.feifei.licai.service.ProjectService;
import com.feifei.licai.vo.DataGridResult;
import com.feifei.licai.vo.ProjectVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by cuishaofei on 2019/3/16.
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    Log logger = LogFactory.getLog(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    /**
     * 分页查找
     **/
    @GetMapping
    @RequestMapping("/getProjectList")
    public DataGridResult getProjectList(){
        try {
            DataGridResult result = new DataGridResult();
            List<ProjectVO> totals = projectService.getProjectList();
            result.setTotal(totals.size());
            result.setRows(totals);
            if(result != null){
                return result;
            }
        } catch (Exception e) {
            logger.error("发生异常", e);
        }
        return null;
    }
}
