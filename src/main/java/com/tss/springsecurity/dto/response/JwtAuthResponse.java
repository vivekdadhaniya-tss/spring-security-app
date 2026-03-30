package com.tss.springsecurity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtAuthResponse {

    private String accessToken;

    @Builder.Default
    private String tokenType = "Bearer";
}
