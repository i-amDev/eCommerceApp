package com.project.order_service.service;

import com.project.order_service.clients.ProductServiceClient;
import com.project.order_service.dto.CartItemRequest;
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

    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {
//        Optional<Product> productOptional = productRepository.findById(cartItemRequest.getProductId());
//
//        if (productOptional.isEmpty()) return false;
//
//        Product product = productOptional.get();
//
//        if (product.getStockQuantity() < cartItemRequest.getQuantity()) return false;
//
//        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
//
//        if (userOptional.isEmpty()) return false;
//
//        User user = userOptional.get();

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
