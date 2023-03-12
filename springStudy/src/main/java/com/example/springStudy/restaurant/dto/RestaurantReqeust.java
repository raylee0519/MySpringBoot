package com.example.springStudy.restaurant.dto;

import com.example.springStudy.restaurant.domain.entity.Restaurant;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    public Restaurant toEntity() {
        return Restaurant.builder()
                .name(name)
                .category(category)
                .build();
    }
}