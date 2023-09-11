package com.zerobase.fastlms.banner.entity;

import com.zerobase.fastlms.admin.model.BannerInput;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String bannerName;

    @Lob
    private byte[] imageFile;

    private String altText;
    private String linkedUrl;
    private String targetInfo;
    private Integer sortOrder;
    private Boolean isDisplayedOnFront;

    private LocalDate regDate;

    @Builder
    private Banner(Long id, String bannerName, byte[] imageFile, String altText, String linkedUrl,
                   String targetInfo, Integer sortOrder, Boolean isDisplayedOnFront, LocalDate regDate) {

        this.id = id;
        this.bannerName = bannerName;
        this.imageFile = imageFile;
        this.altText = altText;
        this.linkedUrl = linkedUrl;
        this.targetInfo = targetInfo;
        this.sortOrder = sortOrder;
        this.isDisplayedOnFront = isDisplayedOnFront;
        this.regDate = regDate;

    }

    public static Banner of(BannerInput parameter) throws IOException {

        return Banner.builder()
                .bannerName(parameter.getBannerName())
                .imageFile(parameter.getImageFile().getBytes())
                .altText(parameter.getBannerName())
                .linkedUrl(parameter.getLinkedUrl())
                .targetInfo(parameter.getTargetInfo())
                .sortOrder(parameter.getSortOrder())
                .isDisplayedOnFront(parameter.getIsDisplayedOnFront() != null)
                .regDate(LocalDate.now())
                .build();

    }

    public Banner updateFrom(BannerInput parameter) throws IOException {

        return Banner.builder()
                .id(id)
                .bannerName(!parameter.getBannerName().isEmpty() ? parameter.getBannerName() : bannerName)
                .imageFile(!parameter.getImageFile().isEmpty() ? parameter.getImageFile().getBytes() : imageFile)
                .altText(!parameter.getBannerName().isEmpty() ? parameter.getBannerName() : altText)
                .linkedUrl(!parameter.getLinkedUrl().isEmpty() ? parameter.getLinkedUrl() : linkedUrl)
                .targetInfo(Optional.ofNullable(parameter.getTargetInfo()).orElse(this.targetInfo))
                .sortOrder(Optional.ofNullable(parameter.getSortOrder()).orElse(this.sortOrder))
                .isDisplayedOnFront(parameter.getIsDisplayedOnFront() != null)
                .regDate(regDate)
                .build();

    }

}
