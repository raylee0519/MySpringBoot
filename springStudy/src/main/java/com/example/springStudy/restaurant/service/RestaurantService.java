package com.example.springStudy.restaurant.service;

import com.example.springStudy.restaurant.domain.entity.Restaurant;
import com.example.springStudy.restaurant.domain.repository.RestaurantRepository;
import com.example.springStudy.restaurant.dto.RestaurantRequest;
import com.example.springStudy.restaurant.dto.RestaurantResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantResponse save(RestaurantRequest request) {
        Restaurant entity = repository.save(request.toEntity());
        return RestaurantResponse.of(entity);
    }

    public List<RestaurantResponse> findAll() {
        return repository.findAllByIsDeleted(false)
                .stream()
                .map(RestaurantResponse::of)
                .collect(Collectors.toList());
    }

    public List<RestaurantResponse> findAllByCategory(String category) {
        return repository.findAllByCategoryAndIsDeleted(category, false)
                .stream()
                .map(RestaurantResponse::of)
                .collect(Collectors.toList());
    }
 
    public Restaurant findEntity(Integer id) {
        return repository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new NotFoundException("레스토랑을 찾을 수 없습니다."));
    }

    public RestaurantResponse findById(Integer id) {
        return RestaurantResponse.of(findEntity(id));
    }

    @Transactional
    public RestaurantResponse updateCategory(Integer id, String category) {
        Restaurant entity = findEntity(id);
        entity.updateCategory(category);
        return RestaurantResponse.of(entity);
    }

    @Transactional
    public void delete(Integer id) {
        findEntity(id).delete();
    }

}
