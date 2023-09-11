package com.zerobase.fastlms.admin.model;

import com.zerobase.fastlms.banner.validation.group.OnCreate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BannerInput {

    @NotBlank(groups = OnCreate.class, message = "이미지 이름은 필수값입니다.")
    String bannerName;

    @NotNull(groups = OnCreate.class, message = "이미지 파일은 필수값입니다.")
    MultipartFile imageFile;

    @NotBlank(groups = OnCreate.class, message = "링크된 URL은 필수값입니다.")
    String linkedUrl;

    @NotBlank(groups = OnCreate.class, message = "target 정보는 필수값입니다.")
    String targetInfo;

    @NotNull(groups = OnCreate.class, message = "정렬 순서는 필수값입니다.")
    Integer sortOrder;

    Boolean isDisplayedOnFront;

}
