package com.example.order_manager.service;

import com.example.order_manager.dto.CustomerOrderUse;
import com.example.order_manager.dto.QuantityRequest;
import com.example.order_manager.dto.StockRequest;
import com.example.order_manager.dto.StockResponse;
import com.example.order_manager.exception.ConflictException;
import com.example.order_manager.exception.NotFoundException;
import com.example.order_manager.model.Item;
import com.example.order_manager.model.Stock;
import com.example.order_manager.repository.ItemRepository;
import com.example.order_manager.repository.StockAllocationRepository;
import com.example.order_manager.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StockService {
    private static final Logger LOG = LoggerFactory.getLogger("business");

    private final ItemRepository items;
    private final StockRepository stocks;
    private final StockAllocationRepository allocations;
    private final StockAllocationService allocationService;

    public StockService(ItemRepository items, StockRepository stocks, StockAllocationRepository allocations, StockAllocationService allocationService) {
        this.items = items;
        this.stocks = stocks;
        this.allocations = allocations;
        this.allocationService = allocationService;
    }

    public StockResponse create(StockRequest request) {
        Item item = items.findByIdForUpdate(request.itemId())
                .orElseThrow(() -> new NotFoundException("Item " + request.itemId() + " not found"));

        Stock stock = stocks.save(new Stock(item, request.quantity()));
        LOG.info("STOCK CREATED: stock #{} ({} x item #{})", stock.getId(), stock.getQuantity(), item.getId());

        allocationService.allocateForStock(stock);
        return response(stock);
    }

    public StockResponse response(Stock stock) {
        List<CustomerOrderUse> used = allocations.findByStockIdOrderByIdAsc(stock.getId())
                .stream()
                .map(alloc -> new CustomerOrderUse(alloc.getCustomerOrder().getId(), alloc.getQuantity()))
                .toList();

        long allocated = used
                .stream()
                .mapToLong(CustomerOrderUse::quantity).sum();

        return new StockResponse(stock.getId(), stock.getItem().getId(), stock.getQuantity(), stock.getCreationDate(), allocated, stock.getQuantity() - allocated, used);
    }

    @Transactional(readOnly = true)
    public List<StockResponse> list() {
        return stocks.findAll()
                .stream()
                .map(this::response)
                .toList();
    }

    @Transactional(readOnly = true)
    public StockResponse get(Long id) {
        Stock stock = findStock(id);
        return response(stock);
    }

    public void delete(Long id) {
        Stock stock = findStock(id);
        items.findByIdForUpdate(stock.getItem().getId());
        if (allocations.existsByStockId(id)) {
            throw new ConflictException("Stock " + id + " already has allocations and cannot be deleted");
        }
        stocks.deleteById(id);
    }

    public StockResponse update(Long id, QuantityRequest request) {
        Stock stock = findStock(id);
        items.findByIdForUpdate(stock.getItem().getId());
        if (allocations.existsByStockId(id)) {
            throw new ConflictException("Stock " + id + " already has allocations and cannot be changed");
        }
        stock.setQuantity(request.quantity());
        allocationService.allocateForStock(stock);
        return response(stock);
    }

    private Stock findStock(Long id) {
        return stocks.findById(id)
                .orElseThrow(() -> new NotFoundException("Stock " + id + " not found"));
    }
}
