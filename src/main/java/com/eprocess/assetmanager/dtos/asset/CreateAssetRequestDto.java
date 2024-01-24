package com.eprocess.assetmanager.dtos.asset;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAssetRequestDto {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "accessories is required")
    private String accessories;
    @NotBlank(message = "image is required")
    private String imageURL;
    @NotNull(message = "serial number is required")
    private String serialNumber;
    @NotNull(message = "receive date is required")
    private Date receivedDate;
}
