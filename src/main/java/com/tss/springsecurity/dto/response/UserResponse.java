package com.tss.springsecurity.dto.response;

import com.tss.springsecurity.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long userId;
    private String username;
    private Role role;
}
