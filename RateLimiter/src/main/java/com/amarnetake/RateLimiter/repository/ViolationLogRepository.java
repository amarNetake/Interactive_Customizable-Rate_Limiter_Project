package com.amarnetake.RateLimiter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amarnetake.RateLimiter.model.ViolationLog;

@Repository
public interface ViolationLogRepository
        extends JpaRepository<ViolationLog, Long> {

    List<ViolationLog> findByUserId(String userId);
}