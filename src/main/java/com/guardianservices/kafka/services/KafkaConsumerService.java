package com.guardianservices.kafka.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guardianservices.kafka.client.EMailClient;
import com.guardianservices.kafka.request.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class KafkaConsumerService {
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    private EMailClient emailClient;

    @KafkaListener(topics = "url-shortener-topic", groupId = "url-shortener-topic")
    public void topicListener1(String message) {

        try {
            // Parse the message
            ObjectMapper mapper = new ObjectMapper();
            // Unescape if needed
            if (message.startsWith("\"") && message.endsWith("\"")) {
                message = message.substring(1, message.length() - 1).replace("\\\"", "\"");
            }

            HashMap<String, Object> map = mapper.readValue(message, HashMap.class);
            String longUrl = (String) map.get("longUrl");
            String shortUrl = (String) map.get("shortUrl");
            String email = (String) map.get("email");
            String expiry = (String) map.get("validation");

            logger.info("Message received in Listener 1: longUrl : {}, shortUrl: {}, expiry {}", longUrl, shortUrl, expiry);

            sendEmail(longUrl, shortUrl, expiry, email);

        } catch (Exception e) {
            logger.error("Exception occurred while processing topicListener1 with probable cause - ", e);
        }
    }

    @KafkaListener(topics = "url-shortener-topic", groupId = "url-shortener-topic")
    public void topicListener2(String message) {

        try {
            // Parse the message
            ObjectMapper mapper = new ObjectMapper();
            // Unescape if needed
            if (message.startsWith("\"") && message.endsWith("\"")) {
                message = message.substring(1, message.length() - 1).replace("\\\"", "\"");
            }

            HashMap<String, Object> map = mapper.readValue(message, HashMap.class);

            String longUrl = (String) map.get("longUrl");
            String shortUrl = (String) map.get("shortUrl");
            String email = (String) map.get("email");
            String expiry = (String) map.get("validation");
            logger.info("Message received in Listener 2: longUrl : {}, shortUrl: {}, expiry {}", longUrl, shortUrl, expiry);
            sendEmail(longUrl, shortUrl, expiry, email);

        } catch (Exception e) {
            logger.error("Exception occurred while processing topicListener2 with probable cause - ", e);
        }
    }


    private void sendEmail(String longUrl, String shortUrl, String expiry, String email) {
        EmailRequest emailRequest = new EmailRequest();

        String message = String.format("Hi there,\n\n Your URL has been successfully shortened!\n\n\uD83D\uDD17 Original URL: %s  \n\uD83D\uDD17 Shortened URL: %s  \n\uD83D\uDCC5 This link is valid until: %s\n\nYou can share the shortened URL above, and it will redirect users to your original link until the expiry date.\n\nThank you for using our service!\n\nBest regards,  \nThe URL Shortener Team", longUrl, shortUrl, expiry) ;
        emailRequest.setTo(email);
        emailRequest.setSubject("Your shortened URL is ready!");
        emailRequest.setMessage(message);

        emailClient.sendEmail(emailRequest);


    }


}
