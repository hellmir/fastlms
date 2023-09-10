package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.admin.dto.LoginHistoryDto;

import java.util.List;

public interface LoginHistoryService {
    void saveLoginHistory(String username, String clientIp, String userAgent);

    List<LoginHistoryDto> getLoginHistory(String username);

}
