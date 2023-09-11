package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.banner.entity.Banner;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Base64;

@Builder
@Getter
public class BannerDto {

    private String bannerName;
    private String imageFile;
    private String altText;
    private String linkedUrl;
    private String targetInfo;
    private Integer sortOrder;
    private Boolean isDisplayedOnFront;

    private LocalDate regDate;

    public static BannerDto of(Banner banner) {

        String encodedImage = Base64.getEncoder().encodeToString(banner.getImageFile());

        return BannerDto.builder()
                .bannerName(banner.getBannerName())
                .imageFile(encodedImage)
                .altText(banner.getAltText())
                .linkedUrl(banner.getLinkedUrl())
                .targetInfo(banner.getTargetInfo())
                .sortOrder(banner.getSortOrder())
                .isDisplayedOnFront(banner.getIsDisplayedOnFront())
                .regDate(banner.getRegDate())
                .build();

    }

}
