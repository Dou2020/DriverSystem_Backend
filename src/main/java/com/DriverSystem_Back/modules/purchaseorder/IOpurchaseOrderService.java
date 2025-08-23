package com.DriverSystem_Back.modules.purchaseorder;

import com.DriverSystem_Back.modules.purchaseorder.dto.*;
import java.util.List;

public interface IOpurchaseOrderService {
    PurchaseOrder create(PurchaseOrderRequest request);
    List<PurchaseOrderResponse> findAll();
    PurchaseOrderResponse findById(Long id);
    void delete(Long id);
}
