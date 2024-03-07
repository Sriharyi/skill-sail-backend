package com.sriharyi.skillsail.model;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "bids")
public class Bid {
    private String id;
    @DocumentReference(collection = "projects",lazy = true)
    private String projectId;
    @DocumentReference(collection = "freelancers",lazy = true)
    private String freelancerId;
    private Double amount;
    private String proposal;
    private boolean accepted;
    
    @CreatedDate
    private LocalDateTime createdDate;
    
    @LastModifiedDate
    private LocalDateTime updatedDate;

    private boolean deleted;
}
