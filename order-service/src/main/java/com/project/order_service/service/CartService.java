package com.project.order_service.service;

import com.project.order_service.clients.ProductServiceClient;
import com.project.order_service.clients.UserServiceClient;
import com.project.order_service.dto.CartItemRequest;
import com.project.order_service.dto.ProductResponse;
import com.project.order_service.dto.UserResponse;
import com.project.order_service.entity.CartItem;
import com.project.order_service.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;

    private final ProductServiceClient productServiceClient;

    private final UserServiceClient userServiceClient;

    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {
        ProductResponse productResponse = productServiceClient.getProductDetails(cartItemRequest.getProductId());

        if (productResponse == null || productResponse.getStockQuantity() < cartItemRequest.getQuantity()) return false;

        UserResponse userResponse = userServiceClient.getUserDetails(userId);

        if (userResponse == null) return false;

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, cartItemRequest.getProductId());

        if (existingCartItem != null) {
            // Update the quantity.
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000));
            cartItemRepository.save(existingCartItem);
        }
        else {
            // Create a new cart item.
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(cartItemRequest.getProductId());
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000));
            cartItemRepository.save(cartItem);
        }

        return true;
    }

    public boolean deleteItemFromCart(String userId, String productId) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    @Transactional
    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }

}
