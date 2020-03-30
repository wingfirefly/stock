package vip.linhs.stock.strategy.handle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import vip.linhs.stock.trategy.handle.StrategyHandler;

@RunWith(SpringRunner.class)
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
