package org.sayar.net.Model.DTO;

 import com.fasterxml.jackson.annotation.JsonInclude;
 import lombok.Data;
import org.sayar.net.Model.newModel.Storage;

 import javax.validation.constraints.NotNull;

@Data
public class InventoryGetAllDTO {
    private String inventoryId;
    private Storage inventoryLocation;
    private String currentQuantity;
    private String minQuantity;
    private String partId;
    private String partName;
    private String partCode;
    private String location;
    private String orderAmount;
    private String inventoryCode;
    private String previousQuantity;
}
