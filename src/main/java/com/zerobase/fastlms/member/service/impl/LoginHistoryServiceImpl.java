package com.zerobase.fastlms.member.service.impl;

import com.zerobase.fastlms.admin.dto.LoginHistoryDto;
import com.zerobase.fastlms.member.entity.LoginHistory;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.repository.LoginHistoryRepository;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.LoginHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginHistoryServiceImpl implements LoginHistoryService {

    private final MemberRepository memberRepository;
    private final LoginHistoryRepository loginHistoryRepository;

    @Override
    public void saveLoginHistory(String username, String clientIp, String userAgent) {

        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));

        LocalDateTime loginDt = member.getLastLoginDt();

        LoginHistory loginHistory = loginHistoryRepository.save(LoginHistory.of(username, loginDt, clientIp, userAgent));

    }

    @Override
    public List<LoginHistoryDto> getAllLoginHistories(String username) {

        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "loginDt"));

        List<LoginHistory> loginHistories = loginHistoryRepository
                .findAllByUsername(username, pageRequest).getContent();

        return loginHistories.stream()
                .map(LoginHistoryDto::of)
                .collect(Collectors.toList());

    }

}
