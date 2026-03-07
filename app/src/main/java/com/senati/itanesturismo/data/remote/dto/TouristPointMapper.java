package com.senati.itanesturismo.data.remote.dto;

import com.senati.itanesturismo.data.model.TouristPoint;

public final class TouristPointMapper {

    public static TouristPoint toEntity(TouristPointResponse dto) {
        return TouristPoint.builder()
            .id(dto.id())
            .name(dto.name())
            .category(dto.category())
            .description(dto.description())
            .photoUrl(dto.photoUrl())
            .address(dto.address())
            .latitude(dto.latitude())
            .longitude(dto.longitude())
            .isSynchronized(true)
            .build();
    }
}
