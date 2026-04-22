package com.meet.springref.features.auth.service;

import com.meet.springref.common.security.jwt.JwtService;
import com.meet.springref.features.auth.dto.internal.TokenPair;
import com.meet.springref.features.auth.dto.request.LoginRequest;
import com.meet.springref.features.auth.dto.request.RegisterRequest;
import com.meet.springref.features.auth.exception.AuthException;
import com.meet.springref.features.user.exception.UserException;
import com.meet.springref.features.user.model.User;
import com.meet.springref.features.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public TokenPair login(LoginRequest request) {
        User user;
        try {
            user = userService.getByEmail(request.email());
        } catch (UserException e) {
            throw new AuthException("Invalid email or password");
        }

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new AuthException("Invalid email or password");
        }

        return issueTokenPair(user);
    }

    public TokenPair register(RegisterRequest request) {
        User user;
        try {
            user = userService.createUser(
                    request.name(),
                    request.email(),
                    passwordEncoder.encode(request.password())
            );
        } catch (UserException e) {
            throw new AuthException(e.getMessage());
        }

        return issueTokenPair(user);
    }

    public TokenPair refresh(String rawRefreshToken) {
        User user = refreshTokenService.getUserFromToken(rawRefreshToken);
        String newRefresh = refreshTokenService.rotateRefreshToken(rawRefreshToken);
        String newAccess = jwtService.generateAccessToken(user);

        return new TokenPair(newAccess, newRefresh);
    }

    public void logout(String rawRefreshToken) {
        User user = refreshTokenService.getUserFromToken(rawRefreshToken);
        refreshTokenService.revokeAllForUser(user);
    }

    private TokenPair issueTokenPair(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user);
        return new TokenPair(accessToken, refreshToken);
    }
}