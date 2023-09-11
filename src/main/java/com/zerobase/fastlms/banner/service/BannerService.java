package com.zerobase.fastlms.banner.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerInput;

import java.util.List;

public interface BannerService {

    List<BannerDto> list();

    void add(BannerInput parameter);

    void update(String bannerName, BannerInput parameter);


    void deleteSelectedBanners(List<String> bannerNames);

}
