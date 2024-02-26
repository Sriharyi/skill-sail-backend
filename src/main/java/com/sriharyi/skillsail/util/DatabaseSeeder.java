package com.sriharyi.skillsail.util;

import com.sriharyi.skillsail.authentication.model.Permission;
import com.sriharyi.skillsail.authentication.model.Role;
import com.sriharyi.skillsail.authentication.repository.PermissionRepository;
import com.sriharyi.skillsail.authentication.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;
    @Value("${application.user.roles}")
    private String[] roles;

    @Value("${application.user.permissions}")
    private String[] permissions;

    @PostConstruct
    public void seedDatabase()
    {
        SeedPermissions();
        SeedRoles();
    }


    private void SeedPermissions()
    {
        permissionRepository.deleteAll();

        for(String permission:permissions)
        {
            permissionRepository.save(Permission.builder().name(permission).build());
        }

    }

    private void SeedRoles()
    {
        roleRepository.deleteAll();
        String[] seedRoles = Arrays.stream(roles).map(role -> role.split("_")[1]).distinct().toArray(String[]::new);
        for(String role:seedRoles)
        {
            Set<Permission> rolePermissions = permissionRepository.findAllByNameIn(List.of(role+":READ",role+":WRITE",role+":DELETE",role+":UPDATE"));
            roleRepository.save(Role.builder().name("ROLE_"+role).permissions(rolePermissions).build());
        }
    }





}
