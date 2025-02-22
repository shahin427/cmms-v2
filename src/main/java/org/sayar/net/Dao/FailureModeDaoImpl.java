package org.sayar.net.Dao;

import com.mongodb.client.result.UpdateResult;
import org.sayar.net.Model.ActivityType;
import org.sayar.net.Model.DTO.FailureModeDto;
import org.sayar.net.Model.FailureMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FailureModeDaoImpl implements FailureModeDao {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public FailureMode save(FailureMode failureMode) {
        return mongoOperations.save(failureMode);
    }

    @Override
    public List<FailureMode> getAllPage(String term, Pageable pageable) {
        Criteria criteria = new Criteria();
        criteria.and("deleted").is(false);
        if (term != null && !term.isEmpty()) {
            criteria.and("name").regex(term);
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize()),
                Aggregation.limit(pageable.getPageSize())
        );
        return mongoOperations.aggregate(aggregation, FailureMode.class, FailureMode.class).getMappedResults();
    }

    @Override
    public long count(String term) {
        Criteria criteria = new Criteria();
        criteria.and("deleted").is(false);
        if (term != null && !term.isEmpty()) {
            criteria.and("term").regex(term);
        }
        Query query = new Query();
        query.addCriteria(criteria);
        return mongoOperations.count(query, FailureMode.class);
    }

    @Override
    public FailureMode getOne(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id).and("deleted").is(false));
        return mongoOperations.findOne(query, FailureMode.class);
    }

    @Override
    public UpdateResult update(FailureModeDto failureModeDto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(failureModeDto.getId()));
        Update update = new Update();
        update.set("name", failureModeDto.getName());
        return mongoOperations.updateFirst(query, update, FailureMode.class);
    }

    @Override
    public UpdateResult remove(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("deleted", true);
        return mongoOperations.updateFirst(query, update, FailureMode.class);
    }

    @Override
    public List<FailureMode> getAll() {
        Query query = new Query();
        query.addCriteria(Criteria.where("deleted").is(false));
        return mongoOperations.find(query,FailureMode.class);
    }
}
