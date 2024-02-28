package com.sriharyi.skillsail.model;

import com.sriharyi.skillsail.dto.Education;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "freelancers")
public class FreeLancerProfile {
    private String id;
    private String profilePic;
    private String displayName;
    private String userName;
    private String description;
    private List<Education> education;
    private boolean deleted;
}
