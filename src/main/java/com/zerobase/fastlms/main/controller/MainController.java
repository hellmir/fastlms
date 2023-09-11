package com.zerobase.fastlms.main.controller;


import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.banner.service.BannerDisplayService;
import com.zerobase.fastlms.member.service.LoginHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final LoginHistoryService loginHistoryService;
    private final BannerDisplayService bannerDisplayService;


    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {

        String userAgent = request.getHeader("User-Agent");
        String clientIp = getClientIP(request);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        loginHistoryService.saveLoginHistory(username, clientIp, userAgent);

        List<BannerDto> bannersToDisplay = bannerDisplayService.getBannersToDisplay();
        model.addAttribute("banners", bannersToDisplay);

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
