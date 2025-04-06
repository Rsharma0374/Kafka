package com.guardianservices.kafka.client;

import com.guardianservices.kafka.request.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "EMAIL-SERVICE")
public interface EMailClient {
    @PostMapping("email-connector/send-mail")
    ResponseEntity<?> sendEmail(
            @RequestBody EmailRequest emailRequest);
}
