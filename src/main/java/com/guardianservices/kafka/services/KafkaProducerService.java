package com.guardianservices.kafka.services;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {

    private final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, String> template;

    public void sendNotification(String longUrl, LocalDateTime expiryDate, String shortUrl, String email) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("longUrl", longUrl);
        jsonObject.put("validation", expiryDate);
        jsonObject.put("shortUrl", shortUrl);
        jsonObject.put("email", email);

        String request = jsonObject.toString();
        logger.debug("Sending URL created event: {}", request);
        template.send("url-shortener-topic", request);
    }

}
