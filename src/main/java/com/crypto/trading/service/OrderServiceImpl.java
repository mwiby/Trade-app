package com.crypto.trading.service;

import com.crypto.trading.domain.OrderType;
import com.crypto.trading.modal.Coin;
import com.crypto.trading.modal.Order;
import com.crypto.trading.modal.OrderItem;
import com.crypto.trading.modal.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        return null;
    }

    @Override
    public Order gerOrderById(long orderId) {
        return null;
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return List.of();
    }

    @Override
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) {
        return null;
    }
}
