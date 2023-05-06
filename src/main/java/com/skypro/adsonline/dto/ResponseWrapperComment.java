package com.skypro.adsonline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseWrapperComment {
    private int count; // общее количество комментариев
    private List<Comment> results;
}
