# Smart Rate Limiter Service
---

A configurable backend rate limiting service built using **Spring Boot**, **PostgreSQL**, and **React**.

Supports multiple industry-standard algorithms:

- Fixed Window
- Sliding Window
- Token Bucket

Designed with scalability, extensibility, and concurrency in mind.

---

## Features

- Configurable rate limit per user  
- Supports 3 algorithms via Strategy Pattern  
- Factory Pattern for dynamic algorithm creation  
- Thread-safe implementation using ConcurrentHashMap  
- Violation logging and stats tracking  
- REST API based microservice  
- Basic React dashboard for demonstration  

---

### Architecture Overview

1. React UI sends requests to backend.
2. Spring Boot exposes REST APIs.
3. RateLimiterService loads configuration from PostgreSQL.
4. Factory dynamically creates strategy.
5. In-memory concurrent map stores active limiters.
6. Violations and Request logs are logged in database.

---

##  Design Patterns Used

### Strategy Pattern
Each rate limiting algorithm implements a RateLimiterStrategy interface:

Implementations:
- FixedWindowRateLimiter
- SlidingWindowRateLimiter
- TokenBucketRateLimiter

---

### Factory Pattern

RateLimiterFactory dynamically creates strategy based on configuration.

---

##  Algorithms Implemented

### 1. Fixed Window
- O(1) time complexity
- Simple counter reset mechanism
- Burst issue at boundary

---

### 2. Sliding Window
- Stores timestamps
- Removes expired entries
- More accurate than fixed window

---

### 3. Token Bucket
- Industry-preferred approach
- Smooth traffic control
- Supports burst handling

---

## 📊 API Endpoints

### Create Rate Limit

POST `/rate-limit`

Postman API output:
![CreateRule](docs/create_rule.png)

### Access API

POST `/rate-limit/access/{userId}`

* Postman API output:
![Access Allowed](docs/simulate_access_success.png)
![Access Blocked](docs/simulate_access_blocked.png)

### Get latest top 50 stats

GET `/rate-limit/stats/{userId}`

* Postman API output:
![Access Allowed](docs/get_request_logs_stats.png)

## Working:
---
#### Create Rule:
![Access Allowed](docs/create_rule_ui.png)

#### Simulate requests(Send requests entered userid):
![Access Allowed](docs/simulate_request.png)

#### Fetch top 50 request stats:
![Access Allowed](docs/request_log.png)

---
## Database Schema
---

#### RateLimitConfig
- id
- userId
- algorithmType
- limitValue
- windowSizeInMillis

#### ViolationLog
- id
- userId
- timestamp
- reason