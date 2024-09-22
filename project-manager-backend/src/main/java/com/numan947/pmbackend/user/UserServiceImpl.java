package com.numan947.pmbackend.user;

import com.numan947.pmbackend.exception.OperationNotPermittedException;
import com.numan947.pmbackend.security.jwt.JWTService;
import com.numan947.pmbackend.user.dto.UserResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final UserMapper userMapper;

    @Override
    public UserResponse findByJwt(String jwt) {
        Claims allClaimsFromToken = jwtService.extractAllClaims(jwt);
        String email = jwtService.extractClaim(allClaimsFromToken, Claims::getSubject);
        return findByEmail(email);
    }

    @Override
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new OperationNotPermittedException("User not found"));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse findByUserId(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new OperationNotPermittedException("User not found"));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateProjectSize(String userId, Boolean isIncrement) {
        User user = userRepository.findById(userId).orElseThrow(() -> new OperationNotPermittedException("User not found"));
        if (isIncrement)
            user.setNumberOfProjects(user.getNumberOfProjects() + 1);
        else
            user.setNumberOfProjects(user.getNumberOfProjects() - 1);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
}
