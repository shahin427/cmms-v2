package org.sayar.net.Service;

import org.bson.types.ObjectId;
import org.sayar.net.Controller.ActivitySampleController;
import org.sayar.net.Controller.WorkRequestSerialNumberController;
import org.sayar.net.Controller.newController.NewWorkRequestDTO;
import org.sayar.net.Dao.WorkRequestDao;
import org.sayar.net.General.service.GeneralServiceImpl;
import org.sayar.net.Model.*;
import org.sayar.net.Model.Asset.Asset;
import org.sayar.net.Model.DTO.*;
import org.sayar.net.Model.Mongo.MyModel.Activity;
import org.sayar.net.Model.newModel.WorkOrder.WorkOrder;
import org.sayar.net.Service.Mongo.activityServices.activity.ActivityService;
import org.sayar.net.Service.newService.AssetService;
import org.sayar.net.Service.newService.WorkOrderService;
import org.sayar.net.Tools.Print;
import org.sayar.net.sendNotification.ActivitySampleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class WorkRequestServiceImp extends GeneralServiceImpl<WorkRequest> implements WorkRequestService {

    @Autowired
    private WorkRequestDao workRequestDao;

    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivitySampleController activitySampleController;

    @Autowired
    private WorkOrderService workOrderService;

    @Autowired
    private ActivitySampleNotification activitySampleNotification;

    public void createWorkRequest(WorkRequest workRequest) {
        String workRequestId = new ObjectId().toString();
        workRequest.setId(workRequestId);
        Date date = new Date();
        workRequest.setRequestDate(date);
        String activitySampleId = UUID.randomUUID().toString();
        WorkRequest savedWorkRequest = workRequestDao.createWorkRequest(workRequest, activitySampleId);
        /**
         * set workRequest fields on workOrder
         */
        WorkOrder workOrder = workOrderService.createWorkOrderAccordingToAssociatedWorkRequest(savedWorkRequest);
        /**
         * create relevant activitySample
         */
        ActivitySample activitySample = activitySampleController.createActivitySampleByWorkRequest
                (savedWorkRequest.getNumber(), activitySampleId, workRequest.getPmSheetCode(), workRequest.getRequestDate(), workRequest.getUserId(),
                        workRequest.getActivityId(), workRequest.getTitle(),
                        workOrder.getId(), workRequest.getId(), workRequest.getAssetId(),
                        workRequest.getPriority(), workRequest.getMaintenanceType(), workRequest.getMainSubSystemId());

        activitySampleController.changeMentionedActivityFirsStepStatus(activitySample);


        //for notification
        List<String> idList = new ArrayList<>();
        if (activitySample.getActivityLevelList().get(1).getAssignedUserId() != null) {
            System.out.println("1111111");
            idList.add(activitySample.getActivityLevelList().get(1).getAssignedUserId());
        }
        if (activitySample.getActivityLevelList().get(1).getCandidateUserIdList().size() > 0) {
            System.out.println("222222");
            idList.addAll(activitySample.getActivityLevelList().get(1).getCandidateUserIdList());
        }
        if (idList.size() == 0) {
            System.out.println("33333");
            idList.addAll(activitySample.getActivityLevelList().get(1).getUserTypeId());
        }
        Print.print("idList", idList);
        //TODO:: we need websocket server
//        if (idList.size() > 0) {
//            idList.forEach(id -> {
//                activitySampleNotification.sendActivitySampleNotification(id);
//            });
//        }
    }

    @Override
    public NewWorkRequestDTO getOneWorkRequest(String workRequestId) {
        return workRequestDao.getOneWorkRequest(workRequestId);
    }

    @Override
    public Page<WorkRequestDTO> getAllWorkRequest(Pageable pageable, Integer element, WorkRequestSearchDTO workRequestSearchDTO, String userId) {
        List<WorkRequest> workRequestPage = workRequestDao.getAllWorkRequest(pageable, element, workRequestSearchDTO, userId);
        List<String> assetIdList = new ArrayList<>();
        List<String> activityIdList = new ArrayList<>();

        workRequestPage.forEach(workRequest -> {
            assetIdList.add(workRequest.getAssetId());
            activityIdList.add(workRequest.getActivityId());
        });

        List<Asset> assetList = assetService.getAssetNameOfWorkRequest(assetIdList);
        List<Activity> activityList = activityService.getAssociatedActivityOfWorkRequest(activityIdList);
        return new PageImpl<>(
                WorkRequestDTO.map(workRequestPage, assetList, activityList),
                pageable,
                workRequestDao.getNumberOfWorkRequestPage(workRequestSearchDTO, userId)
        );
    }

    @Override
    public WorkRequest getWorkRequestForActivitySampleValidation(String workRequestId) {
        return workRequestDao.getWorkRequestForActivitySampleValidation(workRequestId);
    }

    @Override
    public WorkRequesterSpecificationDTO getWorkRequesterSpecification(String instanceId) {
        WorkRequest workRequest = workRequestDao.getWorkRequesterSpecification(instanceId);
        if (workRequest != null && workRequest.getUserId() != null) {
            User requesterUser = userService.getRequesterDetails(workRequest.getUserId());
            UserType userType = null;
            if (requesterUser.getUserTypeId() != null) {
                userType = userTypeService.getRequesterUserType(requesterUser.getUserTypeId());
            }
            return WorkRequesterSpecificationDTO.map(workRequest, requesterUser, userType);
        } else
            return null;
    }

    @Override
    public List<Activity> getActivitiesOfAsset(String assetId) {
        Asset workRequestAsset = assetService.getActivitiesOfAsset(assetId);
        if (workRequestAsset != null && workRequestAsset.getActivityIdList() != null)
            return activityService.getActivitiesOfAsset(workRequestAsset.getActivityIdList());
        else
            return null;
    }

    @Override
    public List<WorkRequest> getRelevantWorkRequests(List<String> relevantWorkRequestId) {
        return workRequestDao.getRelevantWorkRequests(relevantWorkRequestId);
    }

    @Override
    public List<WorkRequest> getWorkRequestOfTheUser(String userId) {
        return workRequestDao.getWorkRequestOfTheUser(userId);
    }

    @Override
    public boolean ifAssetExistsInWorkRequest(String assetId) {
        return workRequestDao.ifAssetExistsInWorkRequest(assetId);
    }

    @Override
    public UserDetailAndUserTypeDTO getRequesterUser(String requestId) {
        WorkRequest workRequest = workRequestDao.getRequesterUser(requestId);
        return userService.getRequesterUser(workRequest.getUserId());
    }

    @Override
    public void changeWorkRequestInProcessStatusToFalse(String workRequestId) {
        workRequestDao.changeWorkRequestInProcessStatusToFalse(workRequestId);
    }

    @Override
    public void changeWorkRequestStatus(String workRequestId, String rejectionReason) {
        workRequestDao.changeWorkRequestStatus(workRequestId, rejectionReason);
    }

    @Override
    public void workRequestStatusIsInProcess(String workRequestId) {
        workRequestDao.workRequestStatusIsInProcess(workRequestId);
    }

    @Override
    public void setAssessmentTrue(String workRequestId) {
        workRequestDao.setAssessmentTrue(workRequestId);
    }

    @Override
    public List<TechnicianIdAndNameDTO> getWorkRequestTechnician(String workRequestId) {
        WorkOrder workOrder = workOrderService.getWorkOrderTechnicians(workRequestId);
        if (workOrder != null && workOrder.getUserIdList() != null) {
            List<User> technicians = userService.getTechnicians(workOrder.getUserIdList());
            return TechnicianIdAndNameDTO.map(workOrder, technicians);
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public void setAssessmentFalse(String workRequestId) {
        workRequestDao.setAssessmentFalse(workRequestId);
    }

    @Override
    public boolean checkPmSheetCode(String pmSheetCode) {
        return workRequestDao.checkPmSheetCode(pmSheetCode);
    }

    @Override
    public void setSeenDateForWorkRequest(String workRequestId) {
        workRequestDao.setSeenDateForWorkRequest(workRequestId);
    }

    @Override
    public WorkRequest getMaxWorkRequest() {
        return workRequestDao.getMaxWorkRequest();
    }


}
