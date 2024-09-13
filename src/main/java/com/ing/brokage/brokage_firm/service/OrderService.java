package com.ing.brokage.brokage_firm.service;

import com.ing.brokage.brokage_firm.constants.Side;
import com.ing.brokage.brokage_firm.constants.Status;
import com.ing.brokage.brokage_firm.exceptions.BaseException;
import com.ing.brokage.brokage_firm.model.Asset;
import com.ing.brokage.brokage_firm.model.Customer;
import com.ing.brokage.brokage_firm.model.Order;
import com.ing.brokage.brokage_firm.repository.OrderRepository;
import com.ing.brokage.brokage_firm.request.CreateOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.ing.brokage.brokage_firm.util.AssetUtil.MONEY_ASSET;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    AssetService assetService;

    @Autowired
    CustomerService customerService;

    public List<Order> getOrderListForCustomer(String customerId){
        Customer customer = customerService.getCustomer(customerId);
        if (customer != null) {
            return customer.getOrders();
        }
        throw new BaseException(String.format("Customer not found for id: %s", customerId), HttpStatus.NOT_FOUND);
    }

    @Transactional
    public void deleteOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null || !order.getStatus().equals(Status.PENDING)){
            throw new BaseException(String.format("Order not found for orderId: %s", orderId), HttpStatus.NOT_FOUND);
        }
        Asset customerAssetForOrder = order.getOrderSide().equals(Side.BUY) ?
                assetService.getMoneyAsset(order.getCustomer().getId()) :
                assetService.getAsset(order.getCustomer().getId(), order.getAssetName());

        //release usable size accordingly
        customerAssetForOrder.setUsableSize(calculateAssetAfterOrderCancelled(order, customerAssetForOrder));
        //make order status cancelled
        order.setStatus(Status.CANCELLED);
        //transactional
        assetService.updateAsset(customerAssetForOrder);
        orderRepository.save(order);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order createOrder(CreateOrderRequest request) {
        String customerId = request.getCustomerId();
        String assetNameToValidate = request.getOrderSide().equals(Side.BUY) ? MONEY_ASSET : request.getAssetName();
        //check order applicable
        Asset customerAssetForOrder = assetService.getAsset(customerId, assetNameToValidate);

        if (customerAssetForOrder == null) {
            String assetNotFoundError = String.format("Asset: %s not found for customer: %s", assetNameToValidate,
                    customerId);

            throw new BaseException(assetNotFoundError, HttpStatus.NOT_FOUND);
        }

        BigDecimal assetUsableSizeAfterOrder = calculateAssetAfterOrder(request, customerAssetForOrder);
        if (assetUsableSizeAfterOrder.compareTo(BigDecimal.ZERO) < 0) {
            String insufficientAssetError = String.format("Customer asset: %s insufficient for order request: %s",
                    assetNameToValidate, request);
            throw new BaseException(insufficientAssetError, HttpStatus.NOT_ACCEPTABLE);
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
