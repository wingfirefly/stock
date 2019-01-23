package vip.linhs.stock.service;

import java.util.Date;

public interface HolidayCalendarService {

    void updateCurrentYear();

    boolean isHoliday(Date date);

}
