package com.meet.springref.features.auth.repository;

import com.meet.springref.features.auth.model.RefreshToken;
import com.meet.springref.features.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    void deleteAllByUser(User user);  // for logout-all-devices

    void deleteAllByUserAndRevokedFalse(User user);
}
