package com.meet.springref.features.user.dto.response;

import java.util.List;

public record UserResponse(
        Long id,
        String email,
        String name,
        List<String> roles
) {
}
