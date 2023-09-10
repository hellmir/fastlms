package com.zerobase.fastlms.main.controller;


import com.zerobase.fastlms.member.service.LoginHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final LoginHistoryService loginHistoryService;

    @RequestMapping("/")
    public String index(HttpServletRequest request) {

        String userAgent = request.getHeader("User-Agent");
        String clientIp = getClientIP(request);

        log.info(userAgent);
        log.info(clientIp);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        loginHistoryService.saveLoginHistory(username, clientIp, userAgent);

        return "index";

    }

    private String getClientIP(HttpServletRequest request) {

        String clientIp = request.getHeader("X-Forwarded-For");

        if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }

        return clientIp;

    }

    @RequestMapping("/error/denied")
    public String errorDenied() {
        return "error/denied";
    }

}
