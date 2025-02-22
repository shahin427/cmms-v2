package org.sayar.net.Service;

import org.sayar.net.Model.DTO.HolidayCalendarDto;
import org.sayar.net.Model.HolidayCalendar;

import java.util.Date;
import java.util.List;

public interface HolidayCalendarService {
    HolidayCalendar save(HolidayCalendarDto dates);

    HolidayCalendar getDates();

    boolean update(HolidayCalendarDto holidayCalendarDto);
}
