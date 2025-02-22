package org.sayar.net.Model.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserTypeIdListDTO {
    private List<String> userTypeIdList = new ArrayList<>();
}
