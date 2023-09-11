package com.zerobase.fastlms.banner.exception.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
    private int code;
    private String message;
}
