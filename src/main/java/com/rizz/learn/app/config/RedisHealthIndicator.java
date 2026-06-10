package com.rizz.learn.app.config;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component("redis")
public class RedisHealthIndicator implements HealthIndicator {

  private final RedisConnectionFactory redisConnectionFactory;

  public RedisHealthIndicator(RedisConnectionFactory redisConnectionFactory) {
    this.redisConnectionFactory = redisConnectionFactory;
  }

  @Override
  public Health health() {
    try {
      var connection = redisConnectionFactory.getConnection();
      var pong = connection.ping();
      connection.close();

      return Health.up().withDetail("ping", pong).withDetail("Provider", "Upstash Redis").build();
    } catch (Exception e) {
      return Health.down().withDetail("error", e.getMessage()).build();
    }
  }

}
