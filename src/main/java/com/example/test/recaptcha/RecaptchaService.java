package com.example.test.recaptcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaService {

    @Value("${recaptcha.secret-key}")
    private String secretKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean verify(String token) {

        String url =
                "https://www.google.com/recaptcha/api/siteverify";

        MultiValueMap<String, String> body =
                new LinkedMultiValueMap<>();

        body.add("secret", secretKey);
        body.add("response", token);

        RecaptchaResponse response =
                restTemplate.postForObject(
                        url,
                        body,
                        RecaptchaResponse.class
                );

        if (response == null) {
            return false;
        }

        return response.isSuccess()
                && response.getScore() >= 0.5
                && "login".equals(response.getAction());
    }
}