package com.skypro.adsonline.utils;

import com.skypro.adsonline.dto.RegisterReq;
import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.model.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    /**
     * Dto -> entity mapping
     * @param dto input dto class
     * @return entity class
     */
    public UserEntity mapToUserEntity(RegisterReq dto){
        UserEntity entity = new UserEntity();
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhone(dto.getPhone());
        entity.setRole(dto.getRole());
        return entity;
    }

    /**
     * Entity -> dto mapping
     * @param entity input entity class
     * @return dto class
     */
    public User mapToUserDto(UserEntity entity) {
        User dto = new User();
        dto.setId(entity.getId());
        dto.setEmail(entity.getUsername());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhone(entity.getPhone());
        dto.setImage(entity.getImage());
        return dto;
    }
}
