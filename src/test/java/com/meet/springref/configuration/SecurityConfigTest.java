package com.meet.springref.configuration;

import com.meet.springref.common.security.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
        "DATABASE_URL=jdbc:h2:mem:testdb",
        "DATABASE_USERNAME=sa",
        "DATABASE_PASSWORD=password",
        "JWT_SECRET=MTIzNDU2Nzg5MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MA==",
        "APP_ENV=dev"
})
class SecurityConfigTest {

    @Autowired(required = false)
    private SecurityConfig securityConfig;

    @Autowired(required = false)
    private CorsConfigurationSource corsConfigurationSource;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        assertNotNull(securityConfig, "SecurityConfig should be created");
        assertNotNull(corsConfigurationSource, "CorsConfigurationSource should be created");
        assertNotNull(passwordEncoder, "PasswordEncoder should be created");
    }
}
