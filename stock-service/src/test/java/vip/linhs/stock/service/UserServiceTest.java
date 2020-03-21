package vip.linhs.stock.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;

import vip.linhs.stock.model.po.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    private static final String token = "d134a298d08e46589f70162d46ceeb55";

    @Autowired
    private UserService userService;

    @Test
    public void testTutToSession() {
        System.out.println(token);
        User user = userService.getById(1);
        userService.putToSession(user, token);
    }

    @Test
    public void testGetByToken() {
        User user = userService.getByToken(token);
        System.out.println(JSON.toJSONString(user));
        Assert.assertNotNull(user);
        Assert.assertTrue(user.getId() == 1);
    }

}
