package com.hung.controller;

import com.hung.model.Cart;
import com.hung.model.CartItem;
import com.hung.model.User;
import com.hung.request.AddCartItemRequest;
import com.hung.request.UpdateCartItemRequest;
import com.hung.service.CartService;
import com.hung.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest req,
                                                  @RequestHeader("Authorization") String jwt
                                                  ) throws Exception {
        CartItem item = cartService.addItemToCart(req, jwt);

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }
    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateItemQuantity(@RequestBody UpdateCartItemRequest req,
                                                  @RequestHeader("Authorization") String jwt
                                                  ) throws Exception {
        CartItem item = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());

        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    @DeleteMapping("/cart-item/{id}/delete")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long id,
                                               @RequestHeader("Authorization") String jwt
    ) throws Exception {
        Cart item = cartService.removeItemFromCart(id, jwt);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> removeCartItem(
                                               @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart item = cartService.clearCart(user.getId());

        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    @GetMapping("/cart")
    public ResponseEntity<Cart> getCartByUserId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart item = cartService.findCartByUserId(user.getId());

        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
