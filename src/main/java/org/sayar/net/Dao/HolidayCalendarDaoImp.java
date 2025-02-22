package org.sayar.net.Dao;

import com.mongodb.client.result.UpdateResult;
import org.sayar.net.Model.DTO.HolidayCalendarDto;
import org.sayar.net.Model.HolidayCalendar;
import org.sayar.net.Model.Lubricant;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class HolidayCalendarDaoImp implements HolidayCalendarDao {

    private MongoOperations mongoOperations;

    public HolidayCalendarDaoImp(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public HolidayCalendar save(HolidayCalendar holidayCalendar) {
        return mongoOperations.save(holidayCalendar);
    }

    @Override
    public HolidayCalendar getDates() {
        List<HolidayCalendar> holidayCalendars = mongoOperations.findAll(HolidayCalendar.class);
        if (holidayCalendars.size() > 0) {
            return holidayCalendars.get(0);
        } else {
            return new HolidayCalendar();
        }
    }

    @Override
    public boolean update(HolidayCalendarDto holidayCalendarDto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(holidayCalendarDto.getId()));
        Update update = new Update();
        update.set("holidays", holidayCalendarDto.getHolidays());
        UpdateResult updateResult = mongoOperations.updateFirst(query, update, HolidayCalendar.class);
        return updateResult.getModifiedCount() > 0;
    }


}
