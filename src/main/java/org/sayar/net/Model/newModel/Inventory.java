package org.sayar.net.Model.newModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    private String id;
    private String inventoryId;
    private Long currentQuantity;  //موجودی در دسترس
    private Long minQuantity;   //حداقل موجودی
    private String orderAmount; //مقدار سفارش
    private String location; //موقعیت در انبار
    private String inventoryLocationId; //انبار
    private Long previousQuantity;
    private String partId;
    private String ReceiptNumber; //شماره رسید
    private Date creationDate;
    //-------------------------------------------
    private String row;
    private String partName;
    private String partCode;
    private String chargeDepartmentId;
    private String budgetId;
    private String corridor;
    private String warehouse;
    private String price;
    private String inventoryCode;
    private String userId;
    private boolean sameDocumentsDeleted;

    public enum FN {
        id, currentQuantity, previousQuantity, minQuantity, partName, partCode, chargeDepartment, budget, corridor, row, partId, warehouse, price,
        inventoryCode, user, creationDate, inventoryLocation
    }

//    public Inventory() {
//        this.creationDate = new Date();
//    }
}
