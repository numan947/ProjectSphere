package com.numan947.pmbackend.user;

import com.numan947.pmbackend.user.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fname(user.getFirstName())
                .lname(user.getLastName())
                .totalProjects(user.getNumberOfProjects())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .build();
    }
}
