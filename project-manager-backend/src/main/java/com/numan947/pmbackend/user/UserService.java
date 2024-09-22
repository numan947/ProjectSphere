package com.numan947.pmbackend.user;

import com.numan947.pmbackend.user.dto.UserResponse;

public interface UserService {
    UserResponse findByJwt(String jwt);
    UserResponse findByEmail(String email);
    UserResponse findByUserId(String userId);
    UserResponse updateProjectSize(String userId, Boolean isIncrement);
}
