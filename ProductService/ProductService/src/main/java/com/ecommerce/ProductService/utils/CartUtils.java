package com.ecommerce.ProductService.utils;

import com.ecommerce.ProductService.entity.Cart;
import com.ecommerce.ProductService.exception.CartNotFoundException;
import com.ecommerce.ProductService.model.CartItemResponse;
import com.ecommerce.ProductService.model.CartResponse;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class CartUtils {

    public static CartResponse cartToResponse(Cart cart) throws CartNotFoundException {
        if(ObjectUtils.isEmpty(cart)){
            throw new CartNotFoundException("Cart Not Found");
        }
        CartResponse cartResponse = new CartResponse();
        cartResponse.setUserId(cart.getUserId());
        cartResponse.setTotalPrice(cart.getTotalPrice());

        List<CartItemResponse> cartItemResponseList = new ArrayList<>();

        cart.getItems().stream().forEach(cartItem1 -> {
            CartItemResponse cartItem = new CartItemResponse();
            cartItem.setPrice(cartItem1.getPrice());
            cartItem.setQuantity(cartItem1.getQuantity());
            cartItem.setSellerId(cartItem1.getSellerId());
            cartItem.setProductId(cartItem1.getProductId());
            cartItem.setProductName(cartItem1.getProductName());
            cartItemResponseList.add(cartItem);
        });

        cartResponse.setItems(cartItemResponseList);
        return cartResponse;
    }
}
