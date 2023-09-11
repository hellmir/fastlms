package com.zerobase.fastlms.banner.service;


import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.banner.entity.Banner;
import com.zerobase.fastlms.banner.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@Transactional(isolation = READ_COMMITTED, readOnly = true, timeout = 10)
@Service
@RequiredArgsConstructor
public class BannerDisplayService {

    private final BannerRepository bannerRepository;

    public List<BannerDto> getBannersToDisplay() {

        List<Banner> bannersToDisplay = bannerRepository.findByIsDisplayedOnFrontOrderBySortOrderAsc(true);

        return bannersToDisplay.stream()
                .map(BannerDto::of)
                .collect(Collectors.toList());

    }

}
