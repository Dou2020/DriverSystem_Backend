package com.DriverSystem_Back.modules.location;

import com.DriverSystem_Back.modules.location.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository repository;

    public List<LocationResponse> findAll() {
        return repository.findAll().stream()
            .map(loc -> LocationResponse.builder()
                .id(loc.getId())
                .code(loc.getCode())
                .name(loc.getName())
                .build())
            .collect(Collectors.toList());
    }

    public LocationResponse save(LocationRequest request) {
        Location location = Location.builder()
            .code(request.getCode())
            .name(request.getName())
            .build();
        location = repository.save(location);
        return LocationResponse.builder()
            .id(location.getId())
            .code(location.getCode())
            .name(location.getName())
            .build();
    }
}