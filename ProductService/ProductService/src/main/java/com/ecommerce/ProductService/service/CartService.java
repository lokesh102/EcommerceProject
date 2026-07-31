package com.ecommerce.ProductService.service;

import com.ecommerce.ProductService.utils.CartUtils;
import com.ecommerce.ProductService.client.InventoryClient;
import com.ecommerce.ProductService.client.OrderClient;
import com.ecommerce.ProductService.entity.Cart;
import com.ecommerce.ProductService.entity.CartItem;
import com.ecommerce.ProductService.entity.Product;
import com.ecommerce.ProductService.exception.CartNotFoundException;
import com.ecommerce.ProductService.exception.ProductNotFoundException;
import com.ecommerce.ProductService.model.*;
import com.ecommerce.ProductService.repository.CartItemRepository;
import com.ecommerce.ProductService.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final OrderClient orderClient;
    private final InventoryClient inventoryClient;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;

    public Cart addToCart(String userId, CartItem cartItem) {
        Cart cart =  cartRepository.findByUserId(userId).orElse(null);

        assert cart != null;
        cart.getItems().add(cartItem);
        cartItem.setCart(cart);

        cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getPrice() * cartItem.getQuantity()));

        return cartRepository.save(cart);
    }

    public Cart addToCarts(String userId, String productId, int quantity) throws ProductNotFoundException {
        Cart cart =  cartRepository.findByUserId(userId).orElse(new Cart());

        Product product = productService.getProductById(productId);
        CartItem cartItem = new CartItem();
        cartItem.setProductId(product.getProductId());
        cartItem.setPrice(product.getPrice());
        cartItem.setProductName(product.getName());
        cartItem.setSellerId(product.getSeller().getEmail());
        cartItem.setQuantity(quantity);
        List<CartItem> cartList = new ArrayList<>();
        if(ObjectUtils.isNotEmpty(cart.getItems())){
            cartList = cart.getItems();
        }else{
            cart.setUserId(userId);
            cart.setId(UUID.randomUUID().toString());
        }
        cartList.add(cartItem);
        cart.setItems(cartList);
        cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getPrice() * cartItem.getQuantity()));

        return cartRepository.save(cart);
    }

    public CartResponse addToCart(String userId, String productId, int quantity) throws ClassNotFoundException, ProductNotFoundException {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);

        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setTotalPrice(0);
        }

        Product product = productService.getProductById(productId);
        List<CartItem> cartItems = cart.getItems();

        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        boolean productExists = false;
        for (CartItem item : cartItems) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * quantity));
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            CartItem cartItem = new CartItem();
            cartItem.setProductId(product.getProductId());
            cartItem.setPrice(product.getPrice());
            cartItem.setProductName(product.getName());
            cartItem.setSellerId(product.getSeller().getEmail());
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart); // Important for bidirectional relationship

            cartItems.add(cartItem);
            cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * quantity));
        }

        cart.setItems(cartItems);
        Cart savedCart = cartRepository.save(cart);
        return CartUtils.cartToResponse(savedCart);
    }

    public CartResponse getCartByUser(String userId) throws CartNotFoundException {
        Cart cart =  cartRepository.findByUserId(userId).orElse(null);
        return CartUtils.cartToResponse(cart);
    }

    @Transactional
    public void removeItem(String userId, String productId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new CartNotFoundException("Cart not found"));
        double sum = cart.getItems().stream().filter(item -> item.getProductId().equals(productId)).mapToDouble(carts -> carts.getPrice()*carts.getQuantity()).sum();
        cart.setTotalPrice(cart.getTotalPrice() - sum);
        cartRepository.save(cart);
        cartItemRepository.deleteProductFromCart(productId, cart.getId());
    }

    @Transactional
    public void clearCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new CartNotFoundException("Cart not found"));
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartItemRepository.deleteProductFromCart(cart.getId());
        cartRepository.save(cart);
    }

    public OrderResponse getOrderPlacedCart(String userId) throws CartNotFoundException {
        CartResponse cart = getCartByUser(userId);
        OrderResponse orderResponse = new OrderResponse();
        cart.setItems(cart.getItems().stream().filter(item -> inventoryClient.isProductInStock(item.getProductId()))
                .map(item -> {
                    CartItemResponse cartUpdated = new CartItemResponse();
                    cartUpdated.setId(item.getId());
                    cartUpdated.setQuantity(item.getQuantity());
                    cartUpdated.setPrice(item.getPrice());
                    cartUpdated.setSellerId(item.getSellerId());
                    cartUpdated.setProductName(item.getProductName());
                    cartUpdated.setProductId(item.getProductId());
                    return cartUpdated;
        }).collect(Collectors.toList()));

        if(ObjectUtils.notEqual(cart, null)) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setUserId(cart.getUserId());
            orderDetails.setTotalAmount(cart.getTotalPrice());
            orderDetails.setOrderItems(cart.getItems().stream().map(item -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setAmount(item.getPrice());
                orderItem.setProductID(item.getProductId());
                orderItem.setProductName(item.getProductName());
                orderItem.setSellerID(item.getSellerId());
                orderItem.setQuantity(item.getQuantity());
                return orderItem;
            }).collect(Collectors.toList()));
            orderResponse =  orderClient.addOrderDetails(orderDetails);
        }

        cart.getItems().forEach(cartItem -> {
            removeItem(cart.getUserId(), String.valueOf(cartItem.getProductId()));
        });

        return orderResponse;
    }
}
