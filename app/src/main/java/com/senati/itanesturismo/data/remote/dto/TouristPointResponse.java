package com.senati.itanesturismo.data.remote.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public record TouristPointResponse(
        Integer id,
        String name,
        String category,
        String description,
        String photoUrl,
        Double latitude,
        Double longitude,
        String address
) { }
