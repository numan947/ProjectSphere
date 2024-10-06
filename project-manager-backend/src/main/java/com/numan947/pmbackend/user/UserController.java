package com.numan947.pmbackend.user;

import com.numan947.pmbackend.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User", description = "User details related endpoints")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile(Authentication auth){
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(userService.getUserProfile(user.getId()));
    }

    @GetMapping("/usable-tags")
    public ResponseEntity<List<String>>getAllTags(Authentication auth){
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(userService.getAllTags(user.getId()));
    }

    @GetMapping("/usable-categories")
    public ResponseEntity<List<String>>getAllCategories(Authentication auth){
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(userService.getAllCategories(user.getId()));
    }

}
