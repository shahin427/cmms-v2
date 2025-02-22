package org.sayar.net.Scheduler;

import org.sayar.net.Enumes.AssetStatus;
import org.sayar.net.Enumes.RequestStatus;
import org.sayar.net.Model.ActivitySample;
import org.sayar.net.Model.HolidayCalendar;
import org.sayar.net.Model.Mongo.MyModel.Activity;
import org.sayar.net.Model.WorkOrderSchedule;
import org.sayar.net.Model.newModel.WorkOrder.WorkOrder;
import org.sayar.net.Service.HolidayCalendarService;
import org.sayar.net.Service.Mongo.activityServices.activity.ActivityService;
import org.sayar.net.Service.WorkOrderSchedule.WorkOrderScheduleService;
import org.sayar.net.Service.newService.AssetService;
import org.sayar.net.Service.newService.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.sayar.net.Model.WorkOrderSchedule.Mode.FIXED;

@Component
public class SchedulerTransactionHandler {


    private WorkOrderService workOrderService;
    private ActivityService activityService;
    private WorkOrderScheduleService workOrderScheduleService;
    private MongoOperations mongoOperations;
    private AssetService assetService;

    private HolidayCalendarService holidayCalendarService;


    public SchedulerTransactionHandler(WorkOrderService workOrderService, ActivityService activityService, WorkOrderScheduleService workOrderScheduleService, MongoOperations mongoOperations, AssetService assetService, HolidayCalendarService holidayCalendarService) {
        this.workOrderService = workOrderService;
        this.activityService = activityService;
        this.workOrderScheduleService = workOrderScheduleService;
        this.mongoOperations = mongoOperations;
        this.assetService = assetService;
        this.holidayCalendarService = holidayCalendarService;
    }

    @Transactional
    public void scheduling(WorkOrderSchedule a) {
        WorkOrder workOrder = workOrderService.createWorkOrderAccordingToAssociatedWorkRequest(a);
        ActivitySample activitySample = createActivitySampleBySchedule(
                a.getUserIdList(), a.getId(), a.getActivityId(), workOrder.getId(), a.getAssetId(),
                a.getActivityTypeId(), a.getWorkCategoryId(), a.getImportanceDegreeId(),
                a.getMainSubSystemId(), a.getAssetStatus(), workOrder.getMinorSubSystem(), workOrder.getStartDate());

        changeMentionedActivityFirsStepStatus(activitySample);
        if (a.getMode().equals(FIXED)) {
            updateNextDate(a);
        } else {
            workOrderScheduleService.makeNextDateNull(a.getId());
        }
    }

    private void updateNextDate(WorkOrderSchedule entity) {
        Date nextDate = this.setNextDate(entity);

        if (nextDate.before(entity.getEndDate())) {
            workOrderScheduleService.updateNextDate(entity.getId(), nextDate);
        } else {
            workOrderScheduleService.deActiveWorkOrderSchedule(entity.getId());
        }
    }

    private Date setNextDate(WorkOrderSchedule entity) {

        long workingTime = assetService.getAssetWorkingTime(entity.getAssetId());

        int interval = (int) (entity.getPer() / workingTime);
        Calendar today = Calendar.getInstance();
        Calendar nextScheduledDay = (Calendar) today.clone();
        nextScheduledDay.add(Calendar.DAY_OF_MONTH, interval);

        HolidayCalendar holidayCalendar = holidayCalendarService.getDates();

        if (holidayCalendar.getHolidays().isEmpty()) {
            return nextScheduledDay.getTime();
        } else {
            List<Date> offDays = holidayCalendar.getHolidays()
                    .stream()
                    .filter(date -> !date.before(today.getTime()) && !date.after(nextScheduledDay.getTime()))
                    .collect(Collectors.toList());
            if (!offDays.isEmpty()) {
                nextScheduledDay.add(Calendar.DAY_OF_MONTH, offDays.size());
                while (holidayCalendar.getHolidays().contains(nextScheduledDay.getTime())) {
                    nextScheduledDay.add(Calendar.DAY_OF_MONTH, 1);
                }
                return nextScheduledDay.getTime();
            } else {
                return nextScheduledDay.getTime();
            }
        }

//
//        if (entity.getFrequency().equals(DAILY)) {
//            today.setTime(entity.getNexDate());
//            today.add(Calendar.DAY_OF_MONTH, entity.getPer());
//        }
//
//        if (entity.getFrequency().equals(WEEKLY)) {
//            today.setTime(entity.getNexDate());
//            today.add(Calendar.WEEK_OF_MONTH, entity.getPer());
//        }
//
//        if (entity.getFrequency().equals(MONTHLY)) {
//            today.setTime(entity.getNexDate());
//            today.add(Calendar.MONTH, entity.getPer());
//        }
//
//        if (entity.getFrequency().equals(YEARLY)) {
//            today.setTime(entity.getNexDate());
//            today.add(Calendar.YEAR, entity.getPer());
//        }

//        return today.getTime();
    }


    public void changeMentionedActivityFirsStepStatus(ActivitySample activitySample) {
        if (activitySample != null) {
            activitySample.getActivityLevelList().get(0).setActionLevel("accepted");
            activitySample.getActivityLevelList().get(0).getNextActivityLevel().setActionLevel("pending");
            int nextActivityLevel = Integer.parseInt(activitySample.getActivityLevelList().get(0).getNextActivityLevel().getId());
            activitySample.getActivityLevelList().get(nextActivityLevel).setActionLevel("pending");
            // setting pending date of next activityLevel
            activitySample.getActivityLevelList().get(nextActivityLevel).setPendingDate(new Date());
            mongoOperations.save(activitySample);
        }
    }

    public ActivitySample createActivitySampleBySchedule(
            List<String> userIdList,
            String scheduleId,
            String activityId,
            String workOrderId,
            String assetId,
            String activityTypeId,//نوع فعالیت
            String workCategoryId,// رسته کاری
            String importanceDegreeId,//درجه  اهمیت
            String mainSubSystemId,
            AssetStatus assetStatus,
            String minorSubSystem,
            Date startDate) {

        Activity activity = activityService.getActivityByActivityId(activityId);
        ActivitySample activitySample = new ActivitySample();

        if (assetId != null)
            activitySample.setAssetId(assetId);
        if (workOrderId != null)
            activitySample.setWorkOrderId(workOrderId);
        if (activityId != null)
            activitySample.setRelatedActivityId(activityId);

        activitySample.setActivityTypeId(activityTypeId);
        activitySample.setFromSchedule(true);
        activitySample.setMainSubSystemId(mainSubSystemId);
        activitySample.setMinorSubSystem(minorSubSystem);
        activitySample.setWorkCategoryId(workCategoryId);
        activitySample.setImportanceDegreeId(importanceDegreeId);
        activitySample.setAssetStatus(assetStatus);
        activitySample.setTitle(activity.getTitle());
        activitySample.setScheduleId(scheduleId);
        activitySample.setScheduleUserIdList(userIdList);
        activitySample.setActivityLevelList(activity.getActivityLevelList());
        activitySample.setActivityInstanceId(UUID.randomUUID().toString());
        activitySample.getActivityLevelList().get(1).setRequestStatus(RequestStatus.NEW_REQUEST);
        int lastActivityLevel = activitySample.getActivityLevelList().size() - 1;
        activitySample.getActivityLevelList().get(lastActivityLevel).setActionLevel("finish");
        activitySample.setCreationDateOfWorkRequest(startDate);
        return mongoOperations.save(activitySample);
    }

}
