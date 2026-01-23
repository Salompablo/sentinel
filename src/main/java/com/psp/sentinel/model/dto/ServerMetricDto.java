package com.psp.sentinel.model.dto;


import com.psp.sentinel.model.enums.Status;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ServerMetricDto {

    private String serverName;
    private String region;
    private double cpuUsage;
    private double memUsage;
    private Status status;
    private String latestError;
    private String latestLogId;
    private LocalDateTime dateTime;
}
