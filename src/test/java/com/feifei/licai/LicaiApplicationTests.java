package com.feifei.licai;

import com.feifei.licai.mapper.HistoryMapper;
import com.feifei.licai.mapper.ProjectMapper;
import com.feifei.licai.service.ProjectService;
import com.feifei.licai.util.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	@Before
	public void setMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void contextLoads() {
//		projectMapper.getProjectList(Constants.orderCurrentMoney);
//		projectMapper.getProjectList(Constants.orderAllProfit);
	}

}
