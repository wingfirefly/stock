package vip.linhs.stock.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
