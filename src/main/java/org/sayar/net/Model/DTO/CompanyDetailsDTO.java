package org.sayar.net.Model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sayar.net.Model.Asset.ManufacturerCompany;
import org.sayar.net.Model.Asset.SellerCompany;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDetailsDTO {
    private String assetId;
    private ManufacturerCompany manufacturerCompany;  //شرکت سازنده
    private SellerCompany sellerCompany;//شرکت فروشنده
}
