package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.*;
import com.skypro.adsonline.exception.AdNotFoundException;
import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.service.AdService;
import com.skypro.adsonline.service.ImageService;
import com.skypro.adsonline.service.UserService;
import com.skypro.adsonline.utils.AdMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static com.skypro.adsonline.constant.ErrorMessage.ACCESS_DENIED_MSG;
import static com.skypro.adsonline.constant.ErrorMessage.AD_NOT_FOUND_MSG;

@Service
@Transactional
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final AdMapper adMapper;

    @Value("${ads.image.dir.path}")
    private String adsImageDir;

    public AdServiceImpl(AdRepository adRepository, AdMapper adMapper, UserService userService, ImageService imageService) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
        this.userService = userService;
        this.imageService = imageService;
    }

    /**
     * Creates new ad without image first. Then updates the image for the created ad.
     *   Implemented in two steps because image update needs a created ad with id.
     * @param properties new ad properties
     * @param image image for the new ad
     * @param currentUser authorized user
     * @return Ads dto
     * @throws IOException
     */
    @Override
    public Ads addAd(CreateAds properties, MultipartFile image, UserDetails currentUser) throws IOException {
        AdEntity ad = adMapper.mapToAdEntity(properties, currentUser.getUsername());
        ad = adRepository.save(ad);

        Path filePath = getFilePath(ad, image);
        imageService.saveFileOnDisk(image, filePath);
        imageService.updateAdImageDetails(ad, image, filePath);

        ad.setImage("/" + adsImageDir + "/" + ad.getId());
        adRepository.save(ad);

        return adMapper.mapToAdDto(ad);
    }

    @Override
    public FullAds getAds(Integer id, UserDetails currentUser) {
        AdEntity ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(id)));
        return adMapper.mapToFullAdsDto(ad);
    }

    @Override
    public boolean removeAd(Integer id, UserDetails currentUser) {
        checkPermission(id, currentUser);
        AdEntity ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(id)));
        adRepository.delete(ad);
        return true;
    }

    @Override
    public ResponseWrapperAds getAdsMe(UserDetails currentUser) {
        UserEntity author = userService.checkUserByUsername(currentUser.getUsername());
        List<Ads> ads = adRepository.findByAuthor(author).stream()
                .map(ad -> adMapper.mapToAdDto(ad))
                .toList();
        return new ResponseWrapperAds(ads.size(), ads);
    }

    @Override
    public ResponseWrapperAds getAllAds() {
        List<Ads> ads = adRepository.findAll().stream()
                .map(ad -> adMapper.mapToAdDto(ad))
                .toList();
        return new ResponseWrapperAds(ads.size(), ads);
    }

    @Override
    public ResponseWrapperAds getAdsByTitleLike(String title) {
        List<Ads> ads = adRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(ad -> adMapper.mapToAdDto(ad))
                .toList();
        return new ResponseWrapperAds(ads.size(), ads);
    }

    @Override
    public Ads updateAds(Integer id, CreateAds ads, UserDetails currentUser) {
        checkPermission(id, currentUser);
        AdEntity ad = adRepository.findById(id).orElseThrow(() ->
                new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(id)));

        ad.setTitle(ads.getTitle());
        ad.setDescription(ads.getDescription());
        ad.setPrice(ads.getPrice());

        adRepository.save(ad);
        return adMapper.mapToAdDto(ad);
    }

    @Override
    public boolean updateImage(Integer id, MultipartFile image, UserDetails currentUser) throws IOException {
        checkPermission(id, currentUser);

        AdEntity ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(id)));

        Path filePath = getFilePath(ad, image);
        imageService.saveFileOnDisk(image, filePath);
        imageService.updateAdImageDetails(ad, image, filePath);

        ad.setImage("/" + adsImageDir + "/" + ad.getId());
        adRepository.save(ad);

        return true;
    }

    /**
     * Checks if the author of the ad is the same as logged-in user.
     * If it is not and the user is not an ADMIN then throw an exception.
     * @param adId ad id
     * @param currentUser logged-in user
     */
    @Override
    public void checkPermission(Integer adId, UserDetails currentUser) {
        String authorUsername = adRepository
                .findById(adId)
                .orElseThrow(() -> new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(adId)))
                .getAuthor()
                .getUsername();
        if (!authorUsername.equals(currentUser.getUsername())
                && !currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.name()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ACCESS_DENIED_MSG.formatted(currentUser.getUsername()));
        }
    }

    private Path getFilePath(AdEntity ad, MultipartFile image) {
        return Path.of(adsImageDir, ad.getAuthor().getId() + "-" + ad.getId() + "." + imageService.getExtension(image.getOriginalFilename()));
    }
}
