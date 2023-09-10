package com.zerobase.fastlms.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_history_id")
    private Long id;

    private String username;
    private LocalDateTime loginDt;
    private String clientIp;
    private String userAgent;

    @Builder
    private LoginHistory(String username, LocalDateTime loginDt, String clientIp, String userAgent) {
        this.username = username;
        this.loginDt = loginDt;
        this.clientIp = clientIp;
        this.userAgent = userAgent;
    }

    public static LoginHistory of(String username, LocalDateTime loginDt, String clientIp, String userAgent) {

        return LoginHistory.builder()
                .username(username)
                .loginDt(loginDt)
                .clientIp(clientIp)
                .userAgent(userAgent)
                .build();

    }

}
