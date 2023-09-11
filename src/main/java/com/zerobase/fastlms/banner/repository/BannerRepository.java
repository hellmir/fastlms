package com.zerobase.fastlms.banner.repository;

import com.zerobase.fastlms.banner.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BannerRepository extends JpaRepository<Banner, Long> {

    boolean existsByBannerName(String bannerName);


    Optional<Banner> findByBannerName(String bannerName);


    List<Banner> findByBannerNameIn(List<String> bannerNames);

    void deleteByBannerNameIn(List<String> bannerNames);

    List<Banner> findByIsDisplayedOnFrontOrderBySortOrderAsc(boolean isDisplayedOnFront);

}
