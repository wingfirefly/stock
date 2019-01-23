package vip.linhs.stock.scheduled;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduledTasksTest {

    @Autowired
    private ScheduledTasks scheduledTasks;

    @Test
    public void testRunBeginOfYear() {
        scheduledTasks.runBeginOfYear();
    }

    @Test
    public void testRunUpdateOfStock() {
        scheduledTasks.runUpdateOfStock();
    }

    @Test
    public void testRunUpdateOfDailyIndex() {
        scheduledTasks.runUpdateOfDailyIndex();
    }

    @Test
    public void testRunTicker() {
        scheduledTasks.runTicker();
    }

}
