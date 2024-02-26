package com.sriharyi.skillsail.authentication.model;


import com.sriharyi.skillsail.authentication.model.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection =  "tokens")
public class Token {

    @Id
    private String id;

    private String token;

    @Field(targetType = FieldType.STRING)
    private TokenType tokenType;

    private Boolean expired;

    private Boolean revoked;

    @DBRef()
    private User user;
    

}
