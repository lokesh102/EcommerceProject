package com.ecommerce.ProductService.controller;


import com.ecommerce.ProductService.exception.CartNotFoundException;
import com.ecommerce.ProductService.exception.ProductNotFoundException;
import com.ecommerce.ProductService.model.CartResponse;
import com.ecommerce.ProductService.model.OrderResponse;
import com.ecommerce.ProductService.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Add item to cart", description = "Adds a cart item to the user's cart")
    @ApiResponse(responseCode = "200", description = "Successfully added item to cart")
    @PostMapping("/add/{userId}/{productId}/{quantity}")
    public CartResponse addToCart(@Parameter(description = "User ID") @PathVariable String userId,
                                  @PathVariable String productId, @PathVariable Integer quantity) throws ClassNotFoundException, ProductNotFoundException {
        return cartService.addToCart(userId, productId, quantity);
    }

    @Operation(summary = "Get user cart", description = "Fetches the cart details of a specific user")
    @ApiResponse(responseCode = "200", description = "Successfully fetched cart details")
    @GetMapping("/{userId}")
    public CartResponse getCart(@Parameter(description = "User ID") @PathVariable String userId) throws CartNotFoundException {
        return cartService.getCartByUser(userId);
    }

    @Operation(summary = "Place an order", description = "Places an order based on the user's cart")
    @ApiResponse(responseCode = "200", description = "Successfully placed the order")
    @PostMapping("/place/{userId}")
    public OrderResponse getOrderPlaced(@Parameter(description = "User ID") @PathVariable String userId) throws CartNotFoundException {
        return cartService.getOrderPlacedCart(userId);
    }

    @Operation(summary = "Remove item from cart", description = "Removes a specific item from the user's cart")
    @ApiResponse(responseCode = "200", description = "Successfully removed item from cart")
    @DeleteMapping("/remove/{userId}/{productId}")
    public String removeItem(@Parameter(description = "User ID") @PathVariable String userId,
                             @Parameter(description = "Item ID to remove") @PathVariable String productId) {
        cartService.removeItem(userId, productId);
        return "Item removed successfully!";
    }

    @Operation(summary = "Clear the cart", description = "Clears all items from the user's cart")
    @ApiResponse(responseCode = "200", description = "Successfully cleared the cart")
    @DeleteMapping("/clear/{userId}")
    public String clearCart(@Parameter(description = "User ID") @PathVariable String userId) {
        cartService.clearCart(userId);
        return "Cart cleared!";
    }
}