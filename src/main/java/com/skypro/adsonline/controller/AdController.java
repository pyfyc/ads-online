package com.skypro.adsonline.controller;

import com.skypro.adsonline.dto.Ads;
import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.service.AdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("ads")
public class AdController {

    private final AdService adService;

    @PostMapping("/ads")
    public boolean addAds(@RequestBody Ads ads) {
        return adService.addAds(ads);
    }

    @GetMapping("/ads/{ad_pk}/comments")
    public boolean getComments(@PathVariable("ad_pk") String adPk, @RequestBody Comment comment) {
        return adService.getComments(comment);
    }

    @PostMapping("/ads/{ad_pk}/comments")
    public boolean addComments(@PathVariable("ad_pk") String adPk, @RequestBody Comment comment) {
        return adService.addComments(comment);
    }

    @GetMapping("/ads/{id}")
    public boolean getFullAd(@PathVariable("id") long id) {
        return adService.getFullAd(id);
    }

    @DeleteMapping("/ads/{id}")
    public boolean deleteAds(@PathVariable("id") long id) {
        return adService.removeAds(id);
    }

    @PatchMapping("/ads/{id")
    public boolean updateAds(@PathVariable("id") long id, @RequestBody Ads ads) {
        return adService.updateAds(id, ads);
    }

    @GetMapping("/ads/{ad_pk}/comments/{id}")
    public boolean getComments(@PathVariable("id") long id, @PathVariable("ad_pk") String ad_pk) {
        return adService.getComments(id);
    }

    @DeleteMapping("/ads/{ad_pk}/comments/{id}")
    public boolean deleteComments(@PathVariable("id") long id, @PathVariable("ad_pk") String ad_pk) {
        return adService.deleteComments(id);
    }

    @PatchMapping("/ads/{ad_pk}/comments/{id}")
    public boolean updateComments(@PathVariable("id") long id, @PathVariable("ad_pk") String ad_pk) {
        return adService.updateComments(id);
    }

    @GetMapping("/ads/me")
    public boolean getAdsMe() {
        return adService.getAdsMe();
    }
}
