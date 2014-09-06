#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package};

import java.io.IOException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ${package}.model.User;
import ${package}.service.UserService;

/**
 * @author WMS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
		"classpath:applicationContext-shiro.xml" })
public class UserServiceTestByDubbo {

	@Autowired
	private UserService service;
	
	@Test
	public void testUserService() throws IOException {
		long start = System.currentTimeMillis();
		for(int i=0;i<100000;i++){
			User user = service.findById("201158080111");
		}
		long end = System.currentTimeMillis();
		System.out.println((end-start)/1000.0);
		System.in.read();
	}
	
	@Test
	public void saveUser(){
User user = new User();
		
		user.setId("201158080111");
		user.setPassword("201158080111");
		user.setBirthday(new Date());
		user.setEmail("560130911@163.com");
		user.setName("吴梦升");
		user.setIp("127.0.0.1");
		user.setRegtime(new Date());
		this.service.save(user);
	}

}
