package com.sriharyi.skillsail.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "bids")
public class Bid {
    private String id;
    private String projectId;
    private String freelancerId;
    private Double amount;
    private String proposal;
    private boolean accepted;
    
    @CreatedDate
    private Date createdDate;
    
    @LastModifiedDate
    private Date updatedDate;

    private boolean deleted;
}
