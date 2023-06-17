package com.examportal.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    String token;
}
