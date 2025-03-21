package org.sayar.net.Model.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubSystemFailureDto {

    private String subSystemId;
    private String subSystemName;
    private Integer count;


}
