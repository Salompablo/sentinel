package com.psp.sentinel.service;

import com.psp.sentinel.model.document.ServerLog;
import com.psp.sentinel.model.document.ServerMetric;
import com.psp.sentinel.model.dto.ServerMetricDto;
import com.psp.sentinel.model.entity.ServerEntity;
import com.psp.sentinel.model.enums.Status;
import com.psp.sentinel.repository.ServerLogRepository;
import com.psp.sentinel.repository.ServerMetricRepository;
import com.psp.sentinel.repository.ServerRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.psp.sentinel.model.enums.Status.CRITICAL;
import static com.psp.sentinel.model.enums.Status.STABLE;

@Service
public class SystemMonitorService {

    private final List<String> POSIBLE_ERRORS = List.of(
            "java.lang.OutOfMemoryError: Java heap space - Failed to allocate 2048 bytes",
            "org.postgresql.util.PSQLException: FATAL: remaining connection slots are reserved for non-replication superuser connections",
            "kernel: [1234.56] Out of memory: Kill process 123 (java) score 950 or sacrifice child",
            "java.net.SocketTimeoutException: Read timed out after 30000ms at okhttp3.internal.http2.Http2Stream.waitForIo",
            "ERROR 2002 (HY000): Can't connect to local MySQL server through socket '/var/run/mysqld/mysqld.sock' (2)"
    );

    private final SimpMessagingTemplate messagingTemplate;
    private final ServerRepository serverRepository;
    private final ServerMetricRepository serverMetricRepository;
    private final ServerLogRepository serverLogRepository;

    public SystemMonitorService(SimpMessagingTemplate messagingTemplate,
                                ServerRepository serverRepository,
                                ServerMetricRepository serverMetricRepository,
                                ServerLogRepository serverLogRepository) {
        this.messagingTemplate = messagingTemplate;
        this.serverRepository = serverRepository;
        this.serverMetricRepository = serverMetricRepository;
        this.serverLogRepository = serverLogRepository;
    }

    private final Map<Long, LocalDateTime> lastAlertTime = new ConcurrentHashMap<>();

    @Scheduled(fixedRate = 10000)
    public void sendSystemStatus() {
        List<ServerEntity> servers = serverRepository.findAll();

        for(ServerEntity server : servers) {

            String currentError = null;
            String currentLogId = null;

            double cpu = Math.random() * 100;
            double ram = Math.random() * 100;
            double temp = 30 + (Math.random() * 50);

            boolean isCritical = cpu > 90;
            boolean shouldLog = false;

            if (isCritical) {
                LocalDateTime lastTime = lastAlertTime.get(server.getId());

                if (lastTime == null || lastTime.isBefore(LocalDateTime.now().minusSeconds(30))) {
                    shouldLog = true;
                    lastAlertTime.put(server.getId(), LocalDateTime.now());
                }
            }

            if(shouldLog) {
                String randomError = POSIBLE_ERRORS.get((int) (Math.random() * POSIBLE_ERRORS.size()));

                currentError = randomError;

                ServerLog log = ServerLog.builder()
                        .serverId(server.getId())
                        .serverName(server.getName())
                        .region(server.getRegion())
                        .errorType("CRITICAL_FAILURE")
                        .content(randomError)
                        .timestamp(LocalDateTime.now())
                        .build();

                ServerLog savedLog = serverLogRepository.save(log);

                currentLogId = savedLog.getId();
            }

            ServerMetric metric = ServerMetric.builder()
                    .serverId(server.getId())
                    .cpuUsage(cpu)
                    .memoryUsage(ram)
                    .temperature(temp)
                    .timestamp(LocalDateTime.now())
                    .build();

            serverMetricRepository.save(metric);

            ServerMetricDto payload = ServerMetricDto.builder()
                    .serverName(server.getName())
                    .region(server.getRegion())
                    .cpuUsage(cpu)
                    .memUsage(ram)
                    .status(isCritical ? CRITICAL : STABLE)
                    .latestError(shouldLog ? currentError : null)
                    .latestLogId(shouldLog ? currentLogId : null)
                    .dateTime(LocalDateTime.now())
                    .build();

            messagingTemplate.convertAndSend("/topic/system-metrics", payload);
        }
    }
}
