package vip.linhs.stock.strategy.handle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import vip.linhs.stock.trategy.handle.StrategyHandler;

@SpringBootTest
public class VolumeStrategyHandlerTest {

    @Autowired
    @Qualifier("volumeStrategyHandler")
    private StrategyHandler strategyHandler;

    @Test
    public void testHandle() {
        strategyHandler.handle();
    }

}
