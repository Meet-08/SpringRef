package com.meet.springref.features.auth.otp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OtpData {

    private final Integer userId;
    private final String otpHash;
    private int attempts;
    
    public void incrementAttempts() {
        this.attempts++;
    }
}
