package com.amarnetake.RateLimiter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amarnetake.RateLimiter.model.RateLimitConfig;

@Repository
public interface RateLimitConfigRepository 
    extends JpaRepository<RateLimitConfig, Long> {

    Optional<RateLimitConfig> findByUserId(String userId);

}