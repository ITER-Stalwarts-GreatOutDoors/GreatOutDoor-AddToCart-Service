package com.cg.iter.greatoutdooraddtocart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cg.iter.greatoutdooraddtocart.dto.CartDTO;
import com.cg.iter.greatoutdooraddtocart.dto.OrderDTO;
import com.cg.iter.greatoutdooraddtocart.exception.OrderException;
import com.cg.iter.greatoutdooraddtocart.repository.CartRepository;
import com.cg.iter.greatoutdooraddtocart.service.OrderAndCartService;

//localhost:8150
@RestController
@RequestMapping("/cart")
public class OrderController {
	
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	OrderAndCartService orderAndCartService;
	
	
	
	@PostMapping("/addItemToCart")
	public String addItemToCart(@RequestBody CartDTO cartItem) {
		
		String status = "Item add successfully!";
		try {
			
			orderAndCartService.addItemToCart(cartItem);
			
		} catch (OrderException e) {
			
			e.printStackTrace();
			status = e.getMessage();
		}

		return status;
		
		
	}
	

	@PostMapping("/placeOrder")
	public String placeOrder(@RequestParam String userId, @RequestParam String addressId) {
		
		String status = "Order placed successfully";
		OrderDTO order = new OrderDTO();
		order.setAddressId(addressId);
		order.setUserId(userId);
		
		try {
			orderAndCartService.registerOrder(order);
			
		} catch (OrderException e) {
			
			e.printStackTrace();
			status = e.getMessage();
			
		}

		return status;
	
	}
	
	
	@PostMapping("/removeFromCart")
	public String removeItemFromCart(@RequestBody CartDTO cartItem) {
		
		String status = "Item removed successfully!";
	
			try {
				orderAndCartService.removeItemFromCart(cartItem);
			} catch (OrderException e) {
				e.printStackTrace();
				status = e.getMessage();
			}
	
	
		return status;
		
	}

}
