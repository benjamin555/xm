package cn.sp.xm.miyu.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springside.modules.orm.Page;

import cn.sp.xm.miyu.entity.Riddle;

/**
* @author 陈嘉镇
* @version 创建时间：2014-8-19 下午2:17:52
* @email benjaminchen555@gmail.com
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class RiddleServiceTest {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RiddleService riddleService;
	
	@Test
	public void testCreate() throws Exception {
		
		Riddle entityObject = new Riddle();
		entityObject.setQuestion("Am i right?");
		entityObject.setAnswer("Yes");
		riddleService.save(entityObject );
	}
	
	/**
	 * 读前两个记录
	 * 一个最新创建，一个最多z
	 * @throws Exception
	 */
	public void testReadTop2() throws Exception {
		
	}
	
	@Test
	public void testReadAndZan() throws Exception {
		Map<String, String> searchMap = new LinkedHashMap<String, String>();
		int start = 1;
		int size = 1;
		Page<Riddle> riddles = riddleService.getPage(start,size,searchMap );
		Assert.notNull(riddles);
	}

}
