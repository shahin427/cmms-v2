package org.sayar.net.Dao;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.sayar.net.Controller.WorkOrderSchedule.dto.WorkOrderScheduleGetPageDto;
import org.sayar.net.Model.Lubricant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.skip;

@Repository
public class LubricantDaoImp implements LubricantDao {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public boolean save(Lubricant lubricant) {
        Lubricant saved = mongoOperations.save(lubricant);
        if (saved.getId() != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Page<Lubricant> getAll(Lubricant lubricant, Pageable pageable, Integer totalElements) {


        Criteria criteria = new Criteria();
        if (lubricant.getTitle() != null && !lubricant.getTitle().equals("")) {
            criteria.and("title").regex(lubricant.getTitle());
        }

        if (lubricant.getType() != null && !lubricant.getType().equals("")) {
            criteria.and("type").regex(lubricant.getType());
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria)
                , skip(pageable.getPageNumber() * pageable.getPageSize())
                , limit(pageable.getPageSize())
        );
        List<Lubricant> lubricantList = mongoOperations.aggregate(aggregation, Lubricant.class, Lubricant.class).getMappedResults();
        if (totalElements == -1) {
            Aggregation count = Aggregation.newAggregation(
                    Aggregation.match(criteria)
            );
            totalElements = mongoOperations.aggregate(count, Lubricant.class, Lubricant.class).getMappedResults().size();
        }
        return new PageImpl<Lubricant>(lubricantList, pageable, totalElements);

    }

    @Override
    public Lubricant getOne(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoOperations.findOne(query, Lubricant.class);
    }

    @Override
    public boolean update(Lubricant lubricant) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(lubricant.getId()));
        Update update = new Update();
        update.set("title", lubricant.getTitle());
        update.set("type", lubricant.getType());
        UpdateResult updateResult = mongoOperations.updateFirst(query, update, Lubricant.class);
        if (updateResult.getModifiedCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        DeleteResult deleteResult = mongoOperations.remove(query, Lubricant.class);
        if (deleteResult.getDeletedCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Lubricant> getAllWithNoPage() {
        return mongoOperations.findAll(Lubricant.class);
    }

}
