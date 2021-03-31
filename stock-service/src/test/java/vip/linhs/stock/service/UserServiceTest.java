package vip.linhs.stock.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;

import vip.linhs.stock.model.po.User;

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

        user = userService.getByToken(token);
        System.out.println(JSON.toJSONString(user));
        Assertions.assertNotNull(user);
        Assertions.assertEquals(1, user.getId());
    }

}
