package org.sayar.net.Model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.sayar.net.Enumes.AssetPriority;
import org.sayar.net.Model.newModel.CategoryType;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetDTO {
    private String id;
    private String name;
    private String assetName;
    private String code;
    private Boolean status;
    private AssetPriority assetPriority;
    private CategoryType categoryType;
    private String categoryId;
    private Date warrantyDate;

    //__________________
    private String assetTemplateId;
    private String assetTemplateName;
    private String parentLocationId;
    private String parentLocationName;

    public AssetDTO() {
    }
}
