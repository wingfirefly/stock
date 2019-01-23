package vip.linhs.stock.dao;

import java.util.Date;
import java.util.List;

import vip.linhs.stock.model.po.HolidayCalendar;

public interface HolidayCalendarDao {

    void deleteByYear(int year);

    void save(List<HolidayCalendar> list);

    HolidayCalendar getByDate(Date date);

}
