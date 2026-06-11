package com.rizz.learn.app.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rizz.learn.app.service.SystemMaintenanceService;

@Component
public class SystemMaintenanceScheduler {

    private final SystemMaintenanceService systemMaintenanceService;

    public SystemMaintenanceScheduler(SystemMaintenanceService systemMaintenanceService) {
        this.systemMaintenanceService = systemMaintenanceService;
    }

    @Scheduled(fixedRate = 10000)
    public void runHeartbeatTask() {
        systemMaintenanceService.runHeartbeatCheck();
    }

}
