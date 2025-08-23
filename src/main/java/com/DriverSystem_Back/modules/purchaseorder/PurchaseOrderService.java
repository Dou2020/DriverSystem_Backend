package com.DriverSystem_Back.modules.purchaseorder;

import com.DriverSystem_Back.dto_common.ResponseHttpDto;
import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.product.Product;
import com.DriverSystem_Back.modules.product.ProductRepository;
import com.DriverSystem_Back.modules.purchaseOrderItem.PurchaseOrderItem;
import com.DriverSystem_Back.modules.purchaseOrderItem.dto.PurchaseOrderItemResponse;
import com.DriverSystem_Back.modules.purchaseorder.dto.*;
import com.DriverSystem_Back.modules.user.User;
import com.DriverSystem_Back.modules.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService implements IOpurchaseOrderService {
    private final PurchaseOrderRepository orderRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;

    @Override
    public PurchaseOrder create(PurchaseOrderRequest request) {
        // Validar proveedor
        User supplier = userRepo.findById(request.getSupplierId())
            .orElseThrow(() -> new HttpException("Proveedor no encontrado", HttpStatus.NOT_FOUND));

        // Crear orden
        PurchaseOrder order = PurchaseOrder.builder()
            .code(request.getCode())
            .supplier(supplier)
            .status(request.getStatus())
            .orderedAt(request.getOrderedAt())
            .expectedAt(request.getExpectedAt())
            .currency(request.getCurrency())
            .notes(request.getNotes())
            .build();

        // Validar y mapear ítems
        List<PurchaseOrderItem> items = request.getItems().stream().map(itemRequest -> {
            Product product = productRepo.findById(itemRequest.getProductId())
                .orElseThrow(() -> new HttpException("Producto no encontrado", HttpStatus.NOT_FOUND));

            return PurchaseOrderItem.builder()
                .purchaseOrder(order)
                .product(product)
                .quantity(itemRequest.getQuantity())
                .unitCost(itemRequest.getUnitCost())
                .discount(itemRequest.getDiscount())
                .taxRate(itemRequest.getTaxRate())
                .build();
        }).toList();

        order.setItems(items);

        // Guardar orden
        return orderRepo.save(order);
    }


@Override
public List<PurchaseOrderResponse> findAll() {
    return orderRepo.findAll().stream()
        .map(order -> PurchaseOrderResponse.builder()
            .id(order.getId())
            .code(order.getCode())
            .supplierId(order.getSupplier().getId())
            .status(order.getStatus())
            .orderedAt(order.getOrderedAt())
            .expectedAt(order.getExpectedAt())
            .currency(order.getCurrency())
            .notes(order.getNotes())
            .items(order.getItems().stream().map(item -> PurchaseOrderItemResponse.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .quantity(item.getQuantity())
                .unitCost(item.getUnitCost())
                .discount(item.getDiscount())
                .taxRate(item.getTaxRate())
                .build()
            ).toList())
            .build()
        ).toList();
}

    @Override
    public PurchaseOrderResponse findById(Long id) {
        PurchaseOrder order = orderRepo.findById(id)
            .orElseThrow(() -> new HttpException("Orden de compra no encontrada", HttpStatus.NOT_FOUND));

        return PurchaseOrderResponse.builder()
            .id(order.getId())
            .code(order.getCode())
            .supplierId(order.getSupplier().getId())
            .status(order.getStatus())
            .orderedAt(order.getOrderedAt())
            .expectedAt(order.getExpectedAt())
            .currency(order.getCurrency())
            .notes(order.getNotes())
            .items(order.getItems().stream().map(item -> PurchaseOrderItemResponse.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .quantity(item.getQuantity())
                .unitCost(item.getUnitCost())
                .discount(item.getDiscount())
                .taxRate(item.getTaxRate())
                .build()
            ).toList())
            .build();
    }

    @Override
    public void delete(Long id) {
        orderRepo.deleteById(id);
    }
}