package org.sayar.net.Model.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubSystemCalDto {

    private String subSystemId;
    private Integer count;
}
