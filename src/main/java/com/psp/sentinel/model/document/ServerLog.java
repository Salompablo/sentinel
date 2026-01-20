package com.psp.sentinel.model.document;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Setter
@Builder
public class ServerLog {

    @Id
    private String id;

    private Long serverId;
    private String errorType;
    private String content;
    private LocalDateTime timestamp;
}
