package com.psp.sentinel.model.document;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "metrics")
@Getter
@Setter
@Builder
public class ServerMetric {

    @Id
    private String id;

    private Long serverId;

    private double cpuUsage;
    private double memoryUsage;
    private double temperature;

    @Indexed(expireAfter = "1h")
    private LocalDateTime timestamp;
}
