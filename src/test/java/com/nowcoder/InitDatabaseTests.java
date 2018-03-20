package com.nowcoder;

import com.nowcoder.DAO.UserDAO;
import com.nowcoder.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Random;


//测试用例--使用junit4工具
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {


	@Autowired
	UserDAO userDAO;

	@Test
	public void contextLoads() {
		Random random = new Random(1000);
		for(int i = 0; i < 11; i ++){
			User user = new User();
			user.setHeadUrl(String.format("Http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
			user.setName(String.format("USER%d", i));
			user.setPassword("");
			user.setSalt("");
			userDAO.addUser(user);

			user.setPassword("newPassword");
			userDAO.updatePassword(user);
		}

		Assert.assertEquals("newPassword",userDAO.selectById(1).getPassword());
		userDAO.deleteById(1);
		Assert.assertNull(userDAO.selectById(1));
	}

}
