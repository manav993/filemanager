package com.filemanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class RequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String requestUri;
    private LocalDateTime requestTimestamp;
    private int httpResponseCode;
    private String requestIpAddress;
    private String requestCountryCode;
    private String requestIpProvider;
    private long timeLapsed;
}
