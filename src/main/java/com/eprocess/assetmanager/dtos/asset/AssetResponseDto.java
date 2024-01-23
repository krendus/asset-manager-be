package com.eprocess.assetmanager.dtos.asset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetResponseDto {
    private Long id;
    private String name;
    private String accessories;
    private String imageURL;
    private String returnReason;
    private String returnImageURL;
    private Boolean isReturned;
    private Date receivedDate;
    private Date returnDate;
    private Date createdAt;
    private Date updatedAt;
}
