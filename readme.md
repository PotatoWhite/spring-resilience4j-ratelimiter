# Resilience4j - Ratelimiter

## 시스템 한계치 설정
- 시스템의 한계 이상의 요청이 들어올경우 처리량을 제한하고 싶다면
- builkhead가 Runtime상의 동시성의 제한을 한다면, ratelimiter는 단위 시간당의 호출량을 제한할 뿐 동시성을 제한하지는 않는다.


## 설정
```yaml
resilience4j:
  ratelimiter:
    instances:
      sampleRatelimiter:
        
# 처리량(실행권한)      
        limitForPeriod: 2
        
# 단위시간(제한)
        limitRefreshPeriod: 10s

# 실행권한을 대기 
        timeoutDuration: 20s
```
