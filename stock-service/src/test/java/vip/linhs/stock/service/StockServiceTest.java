package vip.linhs.stock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Test
    public void testSaveDailyIndexToFile() {
        String rootPath = "/logs";
        stockService.saveDailyIndexToFile(rootPath);
    }

    @Test
    public void testSaveDailyIndexFromFile() {
        String rootPath = "/logs";
        stockService.saveDailyIndexFromFile(rootPath);
    }

}
