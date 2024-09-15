package com.crypto.trading.service;

import com.crypto.trading.domain.OrderStatus;
import com.crypto.trading.domain.OrderType;
import com.crypto.trading.modal.Coin;
import com.crypto.trading.modal.Order;
import com.crypto.trading.modal.OrderItem;
import com.crypto.trading.modal.User;
import com.crypto.trading.repository.OrderItemRepository;
import com.crypto.trading.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    WalletService walletService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {

        double price = orderItem.getCoin().getCurrentPrice() * orderItem.getQuantity();

        Order order = new Order();
        order.setUser(user);
        order.setOrderType(orderType);
        order.setOrderType(orderType);
        order.setAmount(BigDecimal.valueOf(price));
        order.setTimestamp(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    @Override
    public Order gerOrderById(long orderId) throws Exception {
        return orderRepository
                .findById(orderId)
                .orElseThrow( ()-> new Exception("Order not found"));

    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);
    }

    private OrderItem createOrderItem(Coin coin, double quantity,
                                      double buyPrice, double sellPrice) {

        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);

        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public Order buyAsset(Coin coin, double quantity, User user) throws Exception {

        if(quantity<= 0){
            throw new Exception("quantity should be >0");
        }
        double buyPrice = coin.getCurrentPrice();

        OrderItem orderItem = createOrderItem(coin,quantity,buyPrice,0);
        Order order = createOrder(user, orderItem, OrderType.BUY);
        orderItem.setOrder(order);

        walletService.payOrderPayment(order, user);
        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);

        // create asset

        return orderRepository.save(order);
    }

    @Transactional
    public Order sellAsset(Coin coin, double quantity, User user) throws Exception {

        if(quantity<= 0){
            throw new Exception("quantity should be >0");
        }
        double sellPrice = coin.getCurrentPrice();

         double buyPrice = assetTosell.getPrice();

        OrderItem orderItem = createOrderItem(coin,quantity,buyPrice,sellPrice);
        Order order = createOrder(user, orderItem, OrderType.SELL);
        orderItem.setOrder(order);

        if(assetToSell.getQuantity() >= quantity){

            order.setStatus(OrderStatus.SUCCESS);
            order.setOrderType(OrderType.SELL);
            Order savedOrder = orderRepository.save(order);

            walletService.payOrderPayment(order, user);

            Asset updatedAsset = assetService.updateAsset(assetToSell.getId(),-quantity);
            if(updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1){
                assetService.deleteAsset(updatedAsset.getId);
            }
            return savedOrder;

        }
        throw new Exception("Insufficient quantity to sell");
    }


    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {

        if(orderType.equals(OrderType.BUY)) {
            return buyAsset(coin,quantity,user);
        }
        else if(orderType.equals(OrderType.SELL)) {
            sellAsset(coin,quantity,user);
        }
        throw new Exception("invalid order type");
    }
}
