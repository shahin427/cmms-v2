package org.sayar.net.Controller.newController.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ResWorkOrderForCalendarGetListDTO {

    private String id;
    private String assetId;
    private String assetName;
    private Date startDate;
    private Date endDate;
    private Long estimateCompletionDate;
    private List<String> userIdList;

}
