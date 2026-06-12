package com.rizz.learn.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Async("notificationExecutor")
    public void sendWelcomeNotification(String email, String name) {
        log.info("Start sending welcome notification to email={} name={}", email, name);

        if (email.contains("fail")) {
            throw new IllegalArgumentException("Simulated async notification failure");
        }

        try {
            Thread.sleep(3000);
            log.info("Welcome notification sent successfully to email={} name={}", email, name);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Welcome notification interrupted for email={}", email, e);
            throw new IllegalStateException("Notification sending interrupted", e);
        }
    }
}
