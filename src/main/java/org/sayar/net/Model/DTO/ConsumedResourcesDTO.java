package org.sayar.net.Model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsumedResourcesDTO {
    private String fossilFuel;
    private String electricity;
    private String water;
    private String compressedAir;
    private List<ConsumedResourcesKeyValue> consumedResourcesKeyValueList;
}
