package com.eprocess.assetmanager.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "asset")
public class Asset {
    @Id
    @GeneratedValue
    private Long id;
    private String accessories;
    private String name;
    private String imageURL;
    private String returnReason;
    private String returnImageURL;
    private String serialNumber;
    private Boolean isReturned;
    private Date receivedDate;
    private Date returnDate;
    private Date createdAt;
    private Date updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
