package org.sayar.net.Model.DTO;

import lombok.Data;
import org.sayar.net.Model.Asset.ManufacturerCompany;
import org.sayar.net.Model.Asset.SellerCompany;

@Data
public class PartCompanyDetailsDTO {
    private String partId;
    private ManufacturerCompany manufacturerCompany;  //شرکت سازنده
    private SellerCompany sellerCompany;//شرکت فروشنده
}
