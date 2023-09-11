package com.zerobase.fastlms.banner.service;


import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.banner.entity.Banner;
import com.zerobase.fastlms.banner.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@Transactional(isolation = READ_COMMITTED, readOnly = true, timeout = 10)
@Service
@RequiredArgsConstructor
public class BannerDisplayService {

    private final BannerRepository bannerRepository;

    private static final Logger log = LoggerFactory.getLogger(BannerDisplayService.class);

    public List<BannerDto> getBannersToDisplay() {

        log.info("Beginning to retrieve all banners");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<Banner> bannersToDisplay = bannerRepository.findByIsDisplayedOnFrontOrderBySortOrderAsc(true);

        stopWatch.stop();

        log.info("Banners to display retrieved successfully\n Retrieving task execution time: {} ms",
                stopWatch.getTotalTimeMillis());

        return bannersToDisplay.stream()
                .map(BannerDto::of)
                .collect(Collectors.toList());

    }

}
