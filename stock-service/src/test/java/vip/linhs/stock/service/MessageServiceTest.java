package vip.linhs.stock.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Test
    public void testSend() {
        messageService.send("[大笑]");
    }

    @Test
    public void testSendMd() {
        messageService.sendMd("daily", "### code 300542");
    }

}
