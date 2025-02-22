package org.sayar.net.Controller;

import org.sayar.net.Model.DTO.HolidayCalendarDto;
import org.sayar.net.Model.HolidayCalendar;
import org.sayar.net.Service.HolidayCalendarService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/holiday")
public class HolidayCalendarController {


    private final HolidayCalendarService holidayCalendarService;

    public HolidayCalendarController(HolidayCalendarService holidayCalendarService) {
        this.holidayCalendarService = holidayCalendarService;
    }

    @PostMapping("/save")
    public HolidayCalendar save(@RequestBody HolidayCalendarDto dates) {
        return holidayCalendarService.save(dates);
    }

    @GetMapping("/all")
    public HolidayCalendar getDates() {
        return holidayCalendarService.getDates();
    }

    @PutMapping("/update")
    public boolean update(@RequestBody HolidayCalendarDto holidayCalendarDto) {
        return holidayCalendarService.update(holidayCalendarDto);
    }
}
