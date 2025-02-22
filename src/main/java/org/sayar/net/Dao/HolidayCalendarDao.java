package org.sayar.net.Dao;

import org.sayar.net.Model.DTO.HolidayCalendarDto;
import org.sayar.net.Model.HolidayCalendar;

import java.util.Date;
import java.util.List;

public interface HolidayCalendarDao {
    HolidayCalendar save(HolidayCalendar holidayCalendar);

    HolidayCalendar getDates();

    boolean update(HolidayCalendarDto holidayCalendarDto);
}
