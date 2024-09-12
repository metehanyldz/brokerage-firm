package com.ing.brokage.brokage_firm.service;

import com.ing.brokage.brokage_firm.constants.Side;
import com.ing.brokage.brokage_firm.constants.Status;
import com.ing.brokage.brokage_firm.model.Asset;
import com.ing.brokage.brokage_firm.model.Order;
import com.ing.brokage.brokage_firm.model.OrderId;
import com.ing.brokage.brokage_firm.repository.OrderRepository;
import com.ing.brokage.brokage_firm.request.CreateOrderRequest;
import com.ing.brokage.brokage_firm.request.DeleteOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    AssetService assetService;

    public List<Order> getOrderListForCustomer(String customerId){
        return orderRepository.findAll(Example.of(Order.builder().orderId(
                OrderId.builder().customerId(customerId).build()).build()));
    }

    @Transactional
    public boolean deleteOrder(DeleteOrderRequest request) {
        Order order = orderRepository.findById(request.toOrderId()).orElse(null);
        if (order == null || !order.getStatus().equals(Status.PENDING)){
            return false;
        }
        Asset customerAssetForOrder = order.getOrderSide().equals(Side.BUY) ?
                assetService.getMoneyAsset(order.getOrderId().getCustomerId()) :
                assetService.getAsset(order.getOrderId().getCustomerId(), order.getOrderId().getAssetName());
        //delete order
        //release usable size accordingly
        customerAssetForOrder.setUsableSize(calculateAssetAfterOrderCancelled(order, customerAssetForOrder));
        //transactional
        assetService.updateAsset(customerAssetForOrder);
        orderRepository.delete(order);
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order createOrder(CreateOrderRequest request) {
        //check order applicable
        Asset customerAssetForOrder = request.getOrderSide().equals(Side.BUY) ?
                assetService.getMoneyAsset(request.getCustomerId()) :
                assetService.getAsset(request.getCustomerId(), request.getAssetName());

        if (customerAssetForOrder == null) {
            //asset not exist exception
            return null;
        }
        BigDecimal assetUsableSizeAfterOrder = calculateAssetAfterOrder(request, customerAssetForOrder);
        if (assetUsableSizeAfterOrder.compareTo(BigDecimal.ZERO) < 0) {
            //insufficient asset exception
            return null;
        }
        customerAssetForOrder.setUsableSize(assetUsableSizeAfterOrder);
        assetService.updateAsset(customerAssetForOrder);
        return orderRepository.save(request.toOrder());
    }

    private BigDecimal calculateAssetAfterOrder(CreateOrderRequest request, Asset asset) {
        if (request.getOrderSide().equals(Side.SELL)) {
            return asset.getUsableSize().subtract(request.getSize());
        } else {
            return asset.getUsableSize().subtract(request.getTotalPrice());
        }
    }

    private BigDecimal calculateAssetAfterOrderCancelled(Order order, Asset asset) {
        if (order.getOrderSide().equals(Side.SELL)) {
            return asset.getUsableSize().add(order.getSize());
        } else {
            return asset.getUsableSize().add(order.getPrice().multiply(order.getSize()));
        }
    }
}
