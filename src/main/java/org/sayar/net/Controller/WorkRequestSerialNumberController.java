package org.sayar.net.Controller;

import org.sayar.net.Model.WorkRequestSerialNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/work-request-serial-number")
@RestController
public class WorkRequestSerialNumberController {

    @Autowired
    private MongoOperations mongoOperations;

    public int getNumber() {
        List<WorkRequestSerialNumber> workRequestSerialNumbers = mongoOperations.findAll(WorkRequestSerialNumber.class);
        if (workRequestSerialNumbers.size() == 0) {
            WorkRequestSerialNumber workRequestSerialNumber = new WorkRequestSerialNumber();
            workRequestSerialNumber.setNumber(1);
            mongoOperations.save(workRequestSerialNumber);
            return 1;
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where("type").is("workRequestType"));
            WorkRequestSerialNumber workRequestSerialNumber = mongoOperations.findOne(query, WorkRequestSerialNumber.class);
            return workRequestSerialNumber.getNumber();
        }
    }


    public void increaseNumber() {
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("workRequestType"));
        Update update = new Update();
        update.inc("number", 1);
        mongoOperations.updateFirst(query, update, WorkRequestSerialNumber.class);
    }

    @GetMapping("give-pm-code")
    public ResponseEntity<?> givePmCode() {
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("workRequestType"));
        WorkRequestSerialNumber workRequestSerialNumber = mongoOperations.findOne(query, WorkRequestSerialNumber.class);
        if (workRequestSerialNumber == null) {
            return ResponseEntity.ok().body(1);
        } else {
            return ResponseEntity.ok().body(workRequestSerialNumber.getNumber());
        }
    }

    public void decreaseNumber() {
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("workRequestType"));
        Update update = new Update();
        update.inc("number", -1);
        mongoOperations.updateFirst(query, update, WorkRequestSerialNumber.class);
    }
}
