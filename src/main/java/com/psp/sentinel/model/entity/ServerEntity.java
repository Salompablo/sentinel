package com.psp.sentinel.model.entity;

import com.psp.sentinel.model.enums.ServerStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "servers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String region;
    private String ipAddress;

    @Enumerated(EnumType.STRING)
    private ServerStatus status;
}
