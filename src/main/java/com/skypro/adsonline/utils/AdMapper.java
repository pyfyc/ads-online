package com.skypro.adsonline.utils;

import com.skypro.adsonline.dto.Ads;
import com.skypro.adsonline.dto.CreateAds;
import com.skypro.adsonline.dto.FullAds;
import com.skypro.adsonline.exception.UserNotFoundException;
import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.repository.UserRepository;
import org.springframework.stereotype.Service;

import static com.skypro.adsonline.constant.ErrorMessage.USER_NOT_FOUND_MSG;

@Service
public class AdMapper {
    private final UserRepository userRepository;
    public AdMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Dto -> entity mapping without image.
     *   Image will be saved separately because it needs a created ad with id.
     * @param dto CreateAds dto class
     * @return AdEntity entity class
     */
    public AdEntity mapToAdEntity(CreateAds dto, String username) {

        UserEntity author = userRepository.findByUsername(username);
        if (author == null) {
            throw new UserNotFoundException(USER_NOT_FOUND_MSG.formatted(username));
        }

        AdEntity entity = new AdEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setAuthor(author);
        return entity;
    }

    /**
     * Entity -> dto mapping
     * @param entity AdEntity entity class
     * @return Ads dto class
     */
    public Ads mapToAdDto(AdEntity entity){
        Ads dto = new Ads();
        dto.setAuthor(entity.getAuthor().getId());
        dto.setImage(entity.getImage());
        dto.setPk(entity.getId());
        dto.setPrice(entity.getPrice());
        dto.setTitle(entity.getTitle());
        return dto;
    }

    /**
     * AdEntity entity -> FullAds dto mapping
     * @param entity AdEntity entity class
     * @return FullAds dto class
     */
    public FullAds mapToFullAdsDto(AdEntity entity) {
        FullAds dto = new FullAds();
        dto.setPk(entity.getId());
        dto.setAuthorFirstName(entity.getAuthor().getFirstName());
        dto.setAuthorLastName(entity.getAuthor().getLastName());
        dto.setDescription(entity.getDescription());
        dto.setEmail(entity.getAuthor().getUsername());
        dto.setImage(entity.getImage());
        dto.setPhone(entity.getAuthor().getPhone());
        dto.setPrice(entity.getPrice());
        dto.setTitle(entity.getTitle());
        return dto;
    }
}
