package com.eprocess.assetmanager.dtos.asset;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnAssetRequestDto {
    @NotBlank(message = "asset id is required")
    private Long id;
    @NotBlank(message = "return reason is required")
    private String returnReason;
    @NotBlank(message = "return image url required")
    private String returnImageURL;
    @NotBlank(message = "return date required")
    private Date returnDate;
}
