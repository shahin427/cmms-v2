package org.sayar.net.Scheduler;

import lombok.extern.slf4j.Slf4j;
import org.sayar.net.Model.WorkOrderSchedule;
import org.sayar.net.Service.Mongo.activityServices.activity.ActivityService;
import org.sayar.net.Service.WorkOrderSchedule.WorkOrderScheduleService;
import org.sayar.net.Service.newService.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author masoud
 */
@Component
@Slf4j
public class Schedule {
    @Autowired
    private WorkOrderScheduleService workOrderScheduleService;
    @Autowired
    private SchedulerTransactionHandler schedulerTransactionalHandler;

    //     @Scheduled(cron = "0 1 1 * * *")
    @Scheduled(cron = "0 */1 * ? * *")
    public void memoryErasureSchedule() {
        List<WorkOrderSchedule> res = workOrderScheduleService.getAll();
        res.forEach(a -> {
            try {
                schedulerTransactionalHandler.scheduling(a);
            } catch (Exception ex) {
                log.error("Error happened in WorkOrderSchedule with id equals to " + a.getId(), ex);
            }
        });
    }
}
