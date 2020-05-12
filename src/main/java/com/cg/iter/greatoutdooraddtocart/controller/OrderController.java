package com.cg.iter.greatoutdooraddtocart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.iter.greatoutdooraddtocart.beans.ResponseCartDTO;
import com.cg.iter.greatoutdooraddtocart.dto.CartDTO;
import com.cg.iter.greatoutdooraddtocart.dto.OrderDTO;
import com.cg.iter.greatoutdooraddtocart.exception.NullParameterException;
import com.cg.iter.greatoutdooraddtocart.service.OrderAndCartService;
import org.apache.log4j.Logger;


@RestController
@RequestMapping("/cart")
public class OrderController {
	
	private static final Logger logger = Logger.getLogger(OrderController.class);
	
	@Autowired
	OrderAndCartService orderAndCartService;
	
	
	@PostMapping("/addItemToCart")
	public String addItemToCart(@RequestBody ResponseCartDTO cartDTO) {
		
		if(cartDTO==null || cartDTO.getProductId()==null || cartDTO.getQuantity()==0 || cartDTO.getUserId()==null) { 
			logger.error("Null request, cart details not provided at /addItemToCart");
			throw new NullParameterException("Null request, please provide cart details!");
		}
		
		String status = "Item add successfully!";	
		orderAndCartService.addItemToCart(cartDTO);
			
		return status;
		
		
	}
	
	@PostMapping("/placeOrder")
	public String placeOrder(@RequestParam String userId, @RequestParam String addressId) {
		
		if(userId==null || addressId==null) {
			logger.error("Null request, userId or addressId not provided at /placeOrder");
			throw new NullParameterException("Null request, please provide userId and addressId!");
		}
		
		String status = "Order placed successfully";
		OrderDTO order = new OrderDTO();
		order.setAddressId(addressId);
		order.setUserId(userId);
		orderAndCartService.registerOrder(order);


		return status;
	
	}
	
	
	@PostMapping("/removeFromCart")
	public String removeItemFromCart(@RequestBody ResponseCartDTO cartDTO){
		
		
		if(cartDTO==null || cartDTO.getProductId()==null || cartDTO.getQuantity()==0 || cartDTO.getUserId()==null) { 
			logger.error("Null request, cart details are not provided at /removeFromCart");
			throw new NullParameterException("Null request, please provide cart details to remove iteam from cart!");
		}
		
		String status = "Item removed successfully!";		
		orderAndCartService.removeItemFromCart(cartDTO);
		return status;
		
	}
	
	

}
