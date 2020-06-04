package vip.linhs.stock.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HolidayCalendarServiceTest {

    @Autowired
    private HolidayCalendarService holidayCalendarService;

    @Test
    public void testUpdateCurrentYear() {
        holidayCalendarService.updateCurrentYear();
    }

}
