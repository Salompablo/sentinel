package com.psp.sentinel.model.document;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "server_logs")
@Getter
@Setter
@Builder
public class ServerLog {

    @Id
    private String id;

    private Long serverId;
    private String serverName;
    private String region;
    private String errorType;
    private String content;
    private String aiAnalysis;
    @Indexed(expireAfter = "2400s")
    private LocalDateTime timestamp;
}
