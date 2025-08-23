package com.DriverSystem_Back.modules.stockMovement;

import com.DriverSystem_Back.modules.stockMovement.dto.*;
import com.DriverSystem_Back.modules.product.ProductRepository;
import com.DriverSystem_Back.modules.location.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockMovementService {
    private final StockMovementRepository repository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;

    public List<StockMovementResponse> findAll() {
        return repository.findAll().stream()
            .map(mov -> StockMovementResponse.builder()
                .id(mov.getId())
                .productId(mov.getProduct().getId())
                .locationId(mov.getLocation().getId())
                .movementType(mov.getMovementType())
                .quantity(mov.getQuantity())
                .unitCost(mov.getUnitCost())
                .referenceType(mov.getReferenceType())
                .referenceId(mov.getReferenceId())
                .occurredAt(mov.getOccurredAt())
                .build())
            .collect(Collectors.toList());
    }

    public StockMovementResponse save(StockMovementRequest request) {
        var product = productRepository.findById(request.getProductId()).orElseThrow();
        var location = locationRepository.findById(request.getLocationId()).orElseThrow();
        var movement = StockMovement.builder()
            .product(product)
            .location(location)
            .movementType(request.getMovementType())
            .quantity(request.getQuantity())
            .unitCost(request.getUnitCost())
            .referenceType(request.getReferenceType())
            .referenceId(request.getReferenceId())
            .occurredAt(OffsetDateTime.now())
            .build();
        movement = repository.save(movement);
        return StockMovementResponse.builder()
            .id(movement.getId())
            .productId(movement.getProduct().getId())
            .locationId(movement.getLocation().getId())
            .movementType(movement.getMovementType())
            .quantity(movement.getQuantity())
            .unitCost(movement.getUnitCost())
            .referenceType(movement.getReferenceType())
            .referenceId(movement.getReferenceId())
            .occurredAt(movement.getOccurredAt())
            .build();
    }
}