package org.sayar.net.Model.DTO;

import lombok.Data;

@Data
public class InventorySearchDTO {

    private String partId; //ایدی قطعه
    private String inventoryLocation;//انبار قطعه
    private String location; //موقعیت مکانی در انبار
}
