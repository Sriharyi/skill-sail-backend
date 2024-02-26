package com.sriharyi.skillsail.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "roles")
public class Role {

    @Id
    private String id;
    @Indexed(unique = true)
    private String name;

    @DBRef
    private Set<Permission> permissions;

}
