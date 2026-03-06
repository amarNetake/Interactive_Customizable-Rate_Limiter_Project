package com.amarnetake.RateLimiter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amarnetake.RateLimiter.model.RequestLog;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {

	List<RequestLog> findTop50ByUserIdOrderByTimestampDesc(String userId);

    long countBy();
  

}