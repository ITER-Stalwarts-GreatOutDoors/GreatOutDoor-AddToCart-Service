package com.cg.iter.greatoutdooraddtocart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.iter.greatoutdooraddtocart.dto.CartDTO;
import com.cg.iter.greatoutdooraddtocart.dto.OrderDTO;
import com.cg.iter.greatoutdooraddtocart.exception.OrderException;
import com.cg.iter.greatoutdooraddtocart.service.OrderAndCartService;
import org.apache.log4j.Logger;

//localhost:8150
@RestController
@RequestMapping("/cart")
public class OrderController {
	
	private static final Logger logger = Logger.getLogger(OrderController.class);
	
	@Autowired
	OrderAndCartService orderAndCartService;
	
	
	
	@PostMapping("/addItemToCart")
	public String addItemToCart(@RequestBody CartDTO cartItem) {
		
		String status = "Item add successfully!";
		try {
			
			orderAndCartService.addItemToCart(cartItem);
			
		} catch (OrderException e) {
			
			logger.error(e.getMessage());
			logger.error(e.getStackTrace().toString());
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
			
			logger.error(e.getMessage());
			logger.error(e.getStackTrace().toString());

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
				
				logger.error(e.getMessage());
				logger.error(e.getStackTrace().toString());

				status = e.getMessage();
			}
	
	
		return status;
		
	}
	
	

}
