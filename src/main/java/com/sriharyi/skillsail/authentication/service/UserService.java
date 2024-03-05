package com.sriharyi.skillsail.authentication.service;



import com.sriharyi.skillsail.authentication.dto.UserDto;
import com.sriharyi.skillsail.authentication.model.Permission;
import com.sriharyi.skillsail.authentication.model.Role;
import com.sriharyi.skillsail.authentication.model.User;
import com.sriharyi.skillsail.authentication.repository.RoleRepository;
import com.sriharyi.skillsail.authentication.repository.UserRepository;
import com.sriharyi.skillsail.model.FreeLancerProfile;
import com.sriharyi.skillsail.repository.FreeLancerProfileRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final FreeLancerProfileRepository freeLancerProfileRepository;

    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User addUser(UserDto userdto) {
        User user = User.builder()
                .email(userdto.getEmail())
                .password(userdto.getPassword())
                .build();
        log.info("User: {}", user);
            
        String userName = user.getEmail().split("@")[0];
       

        // Fetch roles associated with role names from the database
        Set<Role> userRoles = userdto.getRoles().stream()
                .map(role -> roleRepository.findByNameIgnoreCase(role)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + role)))
                .collect(Collectors.toSet());

        user.setRoles(userRoles);// Set the fetched roles to the user

        // Set the permissions associated with the roles to the user
        Set<Permission> permissions = userRoles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .collect(Collectors.toSet());

        user.setPermissions(permissions);

        User savedUser = userRepository.save(user);

        FreeLancerProfile profile = FreeLancerProfile.builder()
        .id(savedUser.getId())
        .userName(userName)
        .build();
        freeLancerProfileRepository.save(profile);
        return savedUser;
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


    public UserDto getProfile(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUserName(jwt);
        if (userEmail != null) {
            User user = this.userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return UserDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                    .permissions(user.getPermissions().stream().map(Permission::getName).collect(Collectors.toSet()))
                    .build();
        }
        return null;
    }
}
