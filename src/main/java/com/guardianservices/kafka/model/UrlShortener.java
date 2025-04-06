package com.guardianservices.kafka.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class UrlShortener {

    private String longUrl;

    private String shortCode;

    private String shortUrl;

    private String qrCode;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    public UrlShortener(String longUrl, String shortCode, String shortUrl, String qrCode, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.longUrl = longUrl;
        this.shortCode = shortCode;
        this.shortUrl = shortUrl;
        this.qrCode = qrCode;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }
}
