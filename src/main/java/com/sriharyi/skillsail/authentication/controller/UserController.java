package com.sriharyi.skillsail.authentication.controller;



import com.sriharyi.skillsail.authentication.dto.UserDto;
import com.sriharyi.skillsail.authentication.model.User;
import com.sriharyi.skillsail.authentication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/get")
    public ResponseEntity<UserDetails> getUserByEmail(@RequestParam String email) {
        UserDetails user = userService.loadUserByUsername(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN') && hasAuthority('user:read')")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getProfile(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getProfile(request));
    }
}

