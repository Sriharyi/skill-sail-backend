package com.sriharyi.skillsail.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "ratings")
public class Rating {
    @Id
    private String id;
    @Indexed(unique = true)
    private String raterId; // the person who is giving the rating
    @Indexed(unique = true)
    private String revieweeId; // the person who is being rated
    private String review;
    private Integer rating;
    private boolean deleted;

    @CreatedDate
    private Date CreatedDate;
    @LastModifiedDate
    private Date updatedDate;

}
