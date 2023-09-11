package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.banner.service.BannerService;
import com.zerobase.fastlms.banner.validation.group.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("admin/banner")
public class AdminBannerController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Boolean.class, new CustomBooleanEditor("true", "false", true));
    }

    private final BannerService bannerService;

    @GetMapping("list.do")
    public String list(Model model) {

        List<BannerDto> banners = bannerService.list();
        model.addAttribute("banners", banners);

        return "admin/banner/list";

    }

    @GetMapping("add.do")
    public String add(Model model) {
        return "admin/banner/add";
    }

    @PostMapping("add.do")
    public String add(@Validated(OnCreate.class) @ModelAttribute BannerInput parameter) {

        bannerService.add(parameter);

        return "redirect:/admin/banner/list.do";

    }

    @GetMapping("update.do/{bannerName}")
    public String update(Model model, @PathVariable String bannerName) {
        return "admin/banner/update";
    }

    @PatchMapping("update.do/{bannerName}")
    public ResponseEntity<Void> update(@PathVariable String bannerName, BannerInput parameter) {

        bannerService.update(bannerName, parameter);

        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @DeleteMapping("list.do")
    public ResponseEntity<Void> deleteSelected(@RequestBody List<String> bannerNames) {

        bannerService.deleteSelectedBanners(bannerNames);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
