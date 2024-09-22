package com.numan947.pmbackend.user;

import com.numan947.pmbackend.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile(Authentication auth){
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(userService.getUserProfile(user.getId()));
    }

}
