package com.feifei.licai;

import com.feifei.licai.mapper.HistoryMapper;
import com.feifei.licai.mapper.ProjectMapper;
import com.feifei.licai.mapper.TotalMapper;
import com.feifei.licai.service.ProjectService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.ognl.IntHashMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;


/**
 * @author cuishaofei
 * @date 2019/4/23
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class LicaiApplicationTests {

	Log logger = LogFactory.getLog(LicaiApplicationTests.class);
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private HistoryMapper historyMapper;
	@Autowired
	private TotalMapper totalMapper;
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	@Before
	public void setMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void contextLoads() {

	}


}
