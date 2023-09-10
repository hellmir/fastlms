package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.LoginHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class LoginHistoryDto {

    private LocalDateTime loginDt;
    private String clientIp;
    private String userAgent;

    public static LoginHistoryDto of(LoginHistory loginHistory) {

        return new LoginHistoryDto(
                loginHistory.getLoginDt(), loginHistory.getClientIp(), loginHistory.getUserAgent()
        );
    }

}
