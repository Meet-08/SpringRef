package com.meet.springref.features.user.controller;

import com.meet.springref.common.api.ApiResponse;
import com.meet.springref.features.user.dto.request.AssignRolesRequest;
import com.meet.springref.features.user.dto.request.UpdateProfileRequest;
import com.meet.springref.features.user.dto.response.UserResponse;
import com.meet.springref.features.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> me() {
        return ResponseEntity.ok(ApiResponse.ok("Current user fetched successfully", userService.getCurrentUser()));
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(@RequestBody @Valid UpdateProfileRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Profile updated successfully", userService.updateProfile(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.ok("User deleted successfully", null));
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<ApiResponse<UserResponse>> assignRoles(
            @PathVariable Long id,
            @RequestBody @Valid AssignRolesRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("Roles assigned successfully", userService.assignRoles(id, request)));
    }
}
