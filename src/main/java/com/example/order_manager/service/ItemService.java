package com.example.order_manager.service;

import com.example.order_manager.dto.ItemRequest;
import com.example.order_manager.exception.ConflictException;
import com.example.order_manager.exception.NotFoundException;
import com.example.order_manager.model.Item;
import com.example.order_manager.repository.CustomerOrderRepository;
import com.example.order_manager.repository.CustomerRepository;
import com.example.order_manager.repository.ItemRepository;
import com.example.order_manager.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemService {

    private final ItemRepository items;
    private final CustomerOrderRepository customerOrders;
    private final StockRepository stocks;

    public ItemService(ItemRepository items, CustomerOrderRepository customerOrders, StockRepository stocks) {
        this.items = items;
        this.customerOrders = customerOrders;
        this.stocks = stocks;
    }

    public Item createItem(ItemRequest request) {
        return items.save(new Item(request.name()));
    }

    @Transactional(readOnly = true)
    public List<Item> itemList() {
        return items.findAll();
    }

    @Transactional(readOnly = true)
    public Item getItem(Long id) {
        return findItem(id);
    }

    public Item updateItem(Long id, ItemRequest request) {
        Item item = getItem(id);
        item.setName(request.name());
        return item;
    }

    public void deleteItem(Long id) {
        Item item = findItem(id);
        items.findByIdForUpdate(item.getId());
        if (customerOrders.existsByItemId(id) || stocks.existsByItemId(id)) {
            throw new ConflictException("Item " + id + " has orders or stock and cannot be deleted");
        }
        items.delete(item);
    }

    private Item findItem(Long id) {
        return items.findById(id)
                .orElseThrow(() -> new NotFoundException("Item " + id + " not found"));
    }
}
