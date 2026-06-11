package com.rizz.learn.app.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SystemMaintenanceService {

    private static final Logger log = LoggerFactory.getLogger(SystemMaintenanceService.class);

    public void runHeartbeatCheck() {
        log.info("System heartbeat check executed at {}", LocalDateTime.now());
    }
}
