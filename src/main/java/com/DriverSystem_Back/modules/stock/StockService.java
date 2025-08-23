package com.DriverSystem_Back.modules.stock;

import com.DriverSystem_Back.modules.stock.dto.*;
import com.DriverSystem_Back.modules.product.ProductRepository;
import com.DriverSystem_Back.modules.location.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository repository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;

    public List<StockResponse> findAll() {
        return repository.findAll().stream()
            .map(stock -> StockResponse.builder()
                .productId(stock.getProduct().getId())
                .locationId(stock.getLocation().getId())
                .qty(stock.getQty())
                .minQty(stock.getMinQty())
                .build())
            .collect(Collectors.toList());
    }

    public StockResponse save(StockRequest request) {
        var product = productRepository.findById(request.getProductId()).orElseThrow();
        var location = locationRepository.findById(request.getLocationId()).orElseThrow();
        var stock = Stock.builder()
            .product(product)
            .location(location)
            .qty(request.getQty())
            .minQty(request.getMinQty())
            .build();
        stock = repository.save(stock);
        return StockResponse.builder()
            .productId(stock.getProduct().getId())
            .locationId(stock.getLocation().getId())
            .qty(stock.getQty())
            .minQty(stock.getMinQty())
            .build();
    }
}