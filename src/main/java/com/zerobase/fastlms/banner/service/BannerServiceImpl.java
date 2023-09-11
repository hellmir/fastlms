package com.zerobase.fastlms.banner.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.banner.entity.Banner;
import com.zerobase.fastlms.banner.exception.DuplicateImageFileException;
import com.zerobase.fastlms.banner.exception.FailedToConvertImageFileException;
import com.zerobase.fastlms.banner.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    private static final Logger log = LoggerFactory.getLogger(BannerService.class);

    @Override
    @Transactional(isolation = READ_COMMITTED, readOnly = true, timeout = 10)
    public List<BannerDto> list() {

        log.info("Beginning to retrieve all banners");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<Banner> banners = bannerRepository.findAll();

        stopWatch.stop();

        log.info("All banners retrieved successfully\n Retrieving task execution time: {} ms",
                stopWatch.getTotalTimeMillis());

        return banners.stream()
                .map(BannerDto::of)
                .collect(Collectors.toList());

    }

    @Override
    public void add(BannerInput parameter) {

        log.info("Beginning to save banner for image: {}", parameter.getBannerName());

        boolean isExists = bannerRepository.existsByBannerName(parameter.getBannerName());

        if (isExists) {
            throw new DuplicateImageFileException("이미 존재하는 배너입니다. bannerName: " + parameter.getBannerName());
        }

        try {
            bannerRepository.save(Banner.of(parameter));
        } catch (IOException e) {

            log.error("IOException occurred while converting the image file: ", e);

            throw new FailedToConvertImageFileException
                    ("이미지 파일 변환에 실패했습니다. bannerName: " + parameter.getBannerName());

        }

        log.info("Banner saved successfully for image: {}", parameter.getBannerName());

    }

    @Override
    public void update(String bannerName, BannerInput parameter) {

        log.info("Beginning to update banner for image: {}", bannerName);

        Banner banner = bannerRepository.findByBannerName(bannerName)
                .orElseThrow(() -> new EntityNotFoundException(
                        "존재하지 않는 배너입니다. bannerName: " + bannerName
                ));

        Banner updatedBanner;

        try {
            updatedBanner = bannerRepository.save(banner.updateFrom(parameter));
        } catch (IOException e) {

            log.error("IOException occurred while converting the image file: ", e);

            throw new FailedToConvertImageFileException
                    ("이미지 파일 변환에 실패했습니다. bannerName: " + parameter.getBannerName());

        }

        log.info("Banner updated successfully for image: {}", updatedBanner.getBannerName());

    }

    @Override
    @Transactional(isolation = READ_COMMITTED, timeout = 10)
    public void deleteSelectedBanners(List<String> bannerNames) {

        log.info("Beginning to delete selected banners");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<Banner> banners = bannerRepository.findByBannerNameIn(bannerNames);

        if (banners.size() != bannerNames.size()) {
            throw new EntityNotFoundException("선택된 배너 중 존재하지 않는 배너가 포함되어 있습니다.");
        }

        bannerRepository.deleteByBannerNameIn(bannerNames);

        stopWatch.stop();

        log.info("Selected banners deleted successfully\n Deleting task execution time: {} ms",
                stopWatch.getTotalTimeMillis());

    }

}
