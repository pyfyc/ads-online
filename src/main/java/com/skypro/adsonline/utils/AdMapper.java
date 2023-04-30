package com.skypro.adsonline.utils;

import com.skypro.adsonline.dto.Ads;
import com.skypro.adsonline.dto.CreateAds;
import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class AdMapper {

    private final UserRepository userRepository;

    public AdMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Entity -> dto mapping
     * @param dto input entity class
     * @return dto class
     */
    public AdEntity mapToAdEntity(CreateAds dto, MultipartFile image) {

        //todo: Need to use currently logged in user here (not hardcoded user)
        UserEntity author = userRepository.findById(1).orElse(null);

        AdEntity entity = new AdEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImage(String.valueOf(image));
        entity.setAuthor(author);
        return entity;
    }

    /**
     * Entity -> dto mapping
     * @param entity input entity class
     * @return dto class
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
}
