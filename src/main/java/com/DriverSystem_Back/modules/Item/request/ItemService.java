package com.DriverSystem_Back.modules.Item.request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    public void save (ItemRequest item, Long parendId){
      Item i = new Item();
      i.setParentId(parendId);
      i.setProduct(item.productId());
      i.setQuantity(item.quantity());
        this.itemRepository.save(i);
    }

}
