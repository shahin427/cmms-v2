package org.sayar.net.Service;


import org.sayar.net.Dao.HolidayCalendarDao;
import org.sayar.net.Model.DTO.HolidayCalendarDto;
import org.sayar.net.Model.HolidayCalendar;
import org.springframework.stereotype.Service;
import sun.util.resources.cldr.CalendarData;

import java.util.Date;
import java.util.List;

@Service
public class HolidayCalendarServiceImp implements HolidayCalendarService {

    private HolidayCalendarDao holidayCalendarDao;

    public HolidayCalendarServiceImp(HolidayCalendarDao holidayCalendarDao) {
        this.holidayCalendarDao = holidayCalendarDao;
    }


    @Override
    public HolidayCalendar save(HolidayCalendarDto dates) {
        HolidayCalendar holidayCalendar = new HolidayCalendar(dates.getHolidays());
        return holidayCalendarDao.save(holidayCalendar);
    }

    @Override
    public HolidayCalendar getDates() {
        return holidayCalendarDao.getDates();
    }

    @Override
    public boolean update(HolidayCalendarDto holidayCalendarDto) {
        return holidayCalendarDao.update(holidayCalendarDto);
    }
}
