package com.meet.springref.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String tokenHash; // store SHA-256 hash, never raw token

    @Column(nullable = false)
    private Instant expiresAt;

    private boolean revoked = false;

}