package org.sayar.net.Model.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.sayar.net.Enumes.AssetStatus;
import org.sayar.net.Model.User;
import org.sayar.net.Tools.Print;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
public class ActivityScheduleGetPageDTO {
    private String activityInstanceId;
    private String workOrderId;
    private String activityLevelId;
    private String assetId;
    private String assetName;
    private String mainSubSystemId;//قطعه ی اصلی
    private String mainSubSystemName;//قطعه ی اصلی
    private String minorSubSystem;//قطعه جزئی
    private String workCategoryId;// رسته کاری
    private String workCategoryName;// رسته کاری
    private String activityTypeId;//نوع فعالیت
    private String activityTypeName;//نوع فعالیت
    private String importanceDegreeId;//درجه  اهمیت
    private String importanceDegreeName;//درجه  اهمیت
    //    public List<ActivityLevel> activityLevelList;
    private String solution;//شرح فعالیت
    private AssetStatus assetStatus;//وضعیت تجهیز
    private Long activityTime;//مدت زمان فعالیت
    private Date startDate;//تاریخ  سررسید
    private Date creationDateOfWorkRequest;//تاریخ  سررسید
    private Long estimateCompletionDate;//هلت زمان انجامم
    private boolean workRequestAcceptor;
    private List<UserIdListDTO> scheduleUserIdList;

    public static List<ActivityScheduleGetPageDTO> map(List<PrimaryActivityScheduleGetPageDTO> primaryActivityScheduleGetPageDTOS, List<User> userList) {
        List<ActivityScheduleGetPageDTO> activityScheduleGetPageDTOS = new ArrayList<>();
        primaryActivityScheduleGetPageDTOS.forEach(primaryActivityScheduleGetPageDTO -> {
            List<UserIdListDTO> userIdListDTOList = new ArrayList<>();
            ActivityScheduleGetPageDTO activityScheduleGetPageDTO = new ActivityScheduleGetPageDTO();
            activityScheduleGetPageDTO.setActivityInstanceId(primaryActivityScheduleGetPageDTO.getActivityInstanceId());
            activityScheduleGetPageDTO.setActivityLevelId(primaryActivityScheduleGetPageDTO.getActivityLevelId());
            activityScheduleGetPageDTO.setWorkOrderId(primaryActivityScheduleGetPageDTO.getWorkOrderId());
            activityScheduleGetPageDTO.setAssetId(primaryActivityScheduleGetPageDTO.getAssetId());
            activityScheduleGetPageDTO.setAssetName(primaryActivityScheduleGetPageDTO.getAssetName());
            activityScheduleGetPageDTO.setMainSubSystemId(primaryActivityScheduleGetPageDTO.getMainSubSystemId());
            activityScheduleGetPageDTO.setMainSubSystemName(primaryActivityScheduleGetPageDTO.getMainSubSystemName());
            activityScheduleGetPageDTO.setMinorSubSystem(primaryActivityScheduleGetPageDTO.getMinorSubSystem());
            activityScheduleGetPageDTO.setWorkCategoryId(primaryActivityScheduleGetPageDTO.getWorkCategoryId());
            activityScheduleGetPageDTO.setWorkCategoryName(primaryActivityScheduleGetPageDTO.getWorkCategoryName());
            activityScheduleGetPageDTO.setActivityTypeId(primaryActivityScheduleGetPageDTO.getActivityTypeId());
            activityScheduleGetPageDTO.setActivityTypeName(primaryActivityScheduleGetPageDTO.getActivityTypeName());
            activityScheduleGetPageDTO.setImportanceDegreeId(primaryActivityScheduleGetPageDTO.getImportanceDegreeId());
            activityScheduleGetPageDTO.setImportanceDegreeName(primaryActivityScheduleGetPageDTO.getImportanceDegreeName());
            activityScheduleGetPageDTO.setSolution(primaryActivityScheduleGetPageDTO.getSolution());
            activityScheduleGetPageDTO.setAssetStatus(primaryActivityScheduleGetPageDTO.getAssetStatus());
            activityScheduleGetPageDTO.setActivityTime(primaryActivityScheduleGetPageDTO.getActivityTime());
            activityScheduleGetPageDTO.setStartDate(primaryActivityScheduleGetPageDTO.getStartDate());
            activityScheduleGetPageDTO.setCreationDateOfWorkRequest(primaryActivityScheduleGetPageDTO.getCreationDateOfWorkRequest());
            activityScheduleGetPageDTO.setEstimateCompletionDate(primaryActivityScheduleGetPageDTO.getEstimateCompletionDate());
            activityScheduleGetPageDTO.setWorkRequestAcceptor(primaryActivityScheduleGetPageDTO.isWorkRequestAcceptor());

            if (primaryActivityScheduleGetPageDTO.getScheduleUserIdList() != null) {
                primaryActivityScheduleGetPageDTO.getScheduleUserIdList().forEach(user -> {
                    userList.forEach(us -> {
                        if (us.getId().equals(user)) {
                            UserIdListDTO userIdListDTO = new UserIdListDTO();
                            userIdListDTO.setUserId(us.getId());
                            userIdListDTO.setUserName(us.getName());
                            userIdListDTO.setUserFamily(us.getFamily());
                            userIdListDTOList.add(userIdListDTO);
                        }
                    });
                });
            }
            activityScheduleGetPageDTO.setScheduleUserIdList(userIdListDTOList);
            activityScheduleGetPageDTOS.add(activityScheduleGetPageDTO);
        });
        return activityScheduleGetPageDTOS;
    }
}
