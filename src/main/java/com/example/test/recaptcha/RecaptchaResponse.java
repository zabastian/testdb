package com.example.test.recaptcha;

/*
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecaptchaResponse {
    private boolean success;

    private double score;

    private String action;

    @JsonProperty("error-codes")
    private List<String> errorCodes;
}
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) // dto에 없는 json 필드는 무시
public class RecaptchaResponse {

    private boolean success;

    private double score;

    private String action;

    @JsonProperty("error-codes") // errorCodes 매핑
    private List<String> errorCodes;
}