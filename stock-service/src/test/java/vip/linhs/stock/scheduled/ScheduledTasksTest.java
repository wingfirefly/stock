package vip.linhs.stock.scheduled;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    public void testApplyNewStock() {
        scheduledTasks.applyNewStock();
    }

}
