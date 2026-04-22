package com.meet.springref.features.user.dto.request;

import com.meet.springref.features.user.enums.UserRole;

import java.util.List;

public record AssignRolesRequest(
        List<UserRole> roles
) {
}
