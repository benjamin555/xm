package cn.sp.xm.core.service;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.sp.xm.core.entity.User;

/**
* @author 陈嘉镇
* @version 创建时间：2014-8-9 上午9:20:26
* @email benjaminchen555@gmail.com
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UserServiceTest {
	@Autowired
	private UserService service;
	
	@Test
	public void testSave() throws Exception {
		User user  = new User();
		String operator = null;
		user.setAccountName("ss"+Calendar.getInstance().getTimeInMillis());
		user.setPassword("ss");
		service.create(user,operator);
		
	}
	
	

}
