package com.DriverSystem_Back.modules.location;

import com.DriverSystem_Back.modules.location.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService service;

    @GetMapping
    public List<LocationResponse> getAll() {
        return service.findAll();
    }

    @PostMapping
    public LocationResponse create(@RequestBody LocationRequest request) {
        return service.save(request);
    }
}