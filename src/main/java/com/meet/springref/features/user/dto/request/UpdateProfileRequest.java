package com.meet.springref.features.user.dto.request;

public record UpdateProfileRequest(
        String name,
        String email
) {
}
