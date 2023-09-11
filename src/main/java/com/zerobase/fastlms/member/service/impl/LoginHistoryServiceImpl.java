package com.zerobase.fastlms.member.service.impl;

import com.zerobase.fastlms.admin.dto.LoginHistoryDto;
import com.zerobase.fastlms.member.entity.LoginHistory;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.repository.LoginHistoryRepository;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.LoginHistoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@Service
@RequiredArgsConstructor
public class LoginHistoryServiceImpl implements LoginHistoryService {

    private final MemberRepository memberRepository;
    private final LoginHistoryRepository loginHistoryRepository;

    private final Logger log = LoggerFactory.getLogger(LoginHistoryService.class);

    @Override
    public void saveLoginHistory(String username, String clientIp, String userAgent) {

        log.info("Beginning to save login history for user: {}", username);

        Member member = memberRepository.findById(username)
                .orElseThrow(() -> {
                    log.error("Warning exception occured. User not found: {}", username);
                    return new UsernameNotFoundException("회원 정보가 존재하지 않습니다. username: " + username);
                });

        LocalDateTime loginDt = member.getLastLoginDt();

        LoginHistory loginHistory = loginHistoryRepository.save
                (LoginHistory.of(username, loginDt, clientIp, userAgent));

        log.info("Login history saved successfully for user: {}", username);

    }

    @Override
    @Transactional(isolation = READ_UNCOMMITTED, readOnly = true, timeout = 10)
    public List<LoginHistoryDto> getAllLoginHistories(String username) {

        log.info("Beginning to retrieve latest 5 login histories for user: {}", username);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "loginDt"));

        List<LoginHistory> loginHistories = loginHistoryRepository
                .findAllByUsername(username, pageRequest).getContent();

        stopWatch.stop();

        log.info("Latest 5 login histories retrieved successfully for user: {}\n Retrieving task execution time: {} ms",
                username, stopWatch.getTotalTimeMillis());

        return loginHistories.stream()
                .map(LoginHistoryDto::of)
                .collect(Collectors.toList());

    }

}
