package com.numan947.pmbackend.user.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse
{
    String id;
    String email;
    String fname;
    String lname;
    Long totalProjects;
    String fullName;

}
