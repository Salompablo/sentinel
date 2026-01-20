package com.psp.sentinel.model.dto;


import com.psp.sentinel.model.enums.Status;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SystemStatusDto {

    private int cpuUsage;
    private int memUsage;
    private Status status;
    private LocalDateTime dateTime;
}
