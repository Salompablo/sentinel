package com.psp.sentinel.service;

import com.psp.sentinel.model.dto.SystemStatusDto;
import com.psp.sentinel.model.enums.Status;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.psp.sentinel.model.enums.Status.CRITICAL;
import static com.psp.sentinel.model.enums.Status.STABLE;

@Service
public class SystemMonitorService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public SystemMonitorService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Scheduled(fixedRate = 3000)
    public void sendSystemStatus() {
        int cpuUsage = (int) (Math.random() * 100);
        int memUsage = (int) (Math.random() * 100);

        Status status = cpuUsage > 80 ? CRITICAL : STABLE;

        SystemStatusDto payload = new SystemStatusDto(cpuUsage, memUsage, status, LocalDateTime.now());
        simpMessagingTemplate.convertAndSend("/topic/system-metrics", payload);

        System.out.println("Sending metrics: " + payload);
    }
}
