package com.guardianservices.kafka.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaProducerService {

    private final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, String> template;

    public void sendNotification(String longUrl, LocalDateTime expiryDate, String shortUrl, String email) throws JsonProcessingException {
        Map<String, String> jsonObject = new HashMap<>();
        jsonObject.put("longUrl", longUrl);
        jsonObject.put("validation", expiryDate.toString());
        jsonObject.put("shortUrl", shortUrl);
        jsonObject.put("email", email);


        ObjectMapper objectMapper = new ObjectMapper();
        String notification = objectMapper.writeValueAsString(jsonObject);

        logger.debug("Sending URL created event: {}", notification);
        template.send("url-shortener-topic", notification);
    }

}
