package com.sriharyi.skillsail.authentication.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String email;
    private String password;
    private Set<String> roles;
    private Set<String> permissions;
  

}
