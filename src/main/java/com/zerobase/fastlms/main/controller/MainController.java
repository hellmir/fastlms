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
    public String index() {
        return "index";
    }

    @RequestMapping("/error/denied")
    public String errorDenied() {

        return "error/denied";
    }

}
