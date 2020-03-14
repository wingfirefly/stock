package vip.linhs.stock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Test
    public void testSendDingding() {
        messageService.sendDingding("[大笑]");
    }

    @Test
    public void testSendDingdingMd() {
        messageService.sendDingdingMd("daily", "### code 300542");
    }

}
