package org.sayar.net.Model.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryGetOneDTO {
    private String id;
    private String partId;
    private String partName;
    private String partCode;
    private String location;
    private String orderAmount;
    private Long currentQuantity;
    private Long previousQuantity;
    private Long minQuantity;
    private String inventoryLocationId;
    private String inventoryLocationName;
    private String inventoryCode;
    private String ReceiptNumber;
}
