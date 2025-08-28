package com.DriverSystem_Back.modules.quotation;
import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.Item.request.ItemService;
import com.DriverSystem_Back.modules.Item.request.ItemRequest;
import com.DriverSystem_Back.modules.Item.response.ItemResponse;
import com.DriverSystem_Back.modules.Item.response.ItemResponseRepository;
import com.DriverSystem_Back.modules.quotation.dto.QuotationRequest;
import com.DriverSystem_Back.modules.quotation.dto.QuotationResponse;
import com.DriverSystem_Back.modules.quotation.dto.QuotationStatuRequest;
import com.DriverSystem_Back.modules.quotation.dto.QuotationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
public class QuotationService implements IQuotationService {
    @Autowired
    private QuotationRepository quotationRepository;
    @Autowired
    private ItemService itemService;
    @
    Autowired
    private ItemResponseRepository itemResponseRepository;

    @Override
    public QuotationResponse save(QuotationRequest request) {
        Quotation quotation = new Quotation();
        quotation.setWorkOrderId(request.workOrderId());
        quotation.setStatus(QuotationStatus.DRAFT.name());
        quotation.setApprovedBy(request.approveBy());
        Quotation saved = this.quotationRepository.save(quotation);
        if (request.item() != null) {
            Set<Long> productosGuardados = new HashSet<>(); // asumimos que ItemRequest tiene un getProductId()
            for (ItemRequest item : request.item()) {
                if (!productosGuardados.contains(item.productId() )) {
                    this.itemService.save(item, saved.getId());
                    productosGuardados.add(item.productId() );
                } else {
                    System.out.println("Producto duplicado ignorado: " + item.productId());
                }
            }
        }
        return this.get(saved.getId());
    }

    @Override
    public QuotationResponse update(QuotationRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {
        this.validateQuotation(id);
        this.quotationRepository.deleteById(id);
    }

    @Override
    public QuotationResponse get(Long id) {
        Quotation quotation = this.quotationRepository.findById(id).get();
        List<ItemResponse> itemResponse = this.itemResponseRepository.findByIdQuotationId(quotation.getId());

        QuotationResponse  quotationResponse = new QuotationResponse(quotation,itemResponse);
        return quotationResponse;
    }

    @Override
    public List<QuotationResponse> findAll() {
        return List.of();
    }


    @Override
    public Quotation updataStatus(QuotationStatuRequest request) {
        Quotation quotation = this.validateQuotation(request.id());
        // Convertimos el statusId a la constante del enum
        QuotationStatus status = QuotationStatus.fromCode(Math.toIntExact(request.statusId()));
        quotation.setStatus(status.name());
        quotation.setApprovedAt( OffsetDateTime.now());
        return this.quotationRepository.save(quotation);

    }

    @Override
    public List<QuotationResponse> findAllByUserId(Long userId) {
        List<Quotation>  list = this.quotationRepository.findByCustomerId(userId);
        List<QuotationResponse>  quotationResponseList = new ArrayList<>();
        for (Quotation quotation : list) {
            List<ItemResponse> itemResponse = this.itemResponseRepository.findByIdQuotationId(quotation.getId());
            quotationResponseList.add(new QuotationResponse(quotation,itemResponse));
        }
        return quotationResponseList;
    }

    public Quotation validateQuotation(Long id){
        Optional<Quotation> quotation = this.quotationRepository.findById(id);
        if(quotation.isEmpty())
            throw  new HttpException("", HttpStatus.NOT_FOUND);
        return quotation.get();
    }

}
