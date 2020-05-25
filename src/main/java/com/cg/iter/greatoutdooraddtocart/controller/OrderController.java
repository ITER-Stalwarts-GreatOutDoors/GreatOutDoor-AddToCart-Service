package com.cg.iter.greatoutdooraddtocart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cg.iter.greatoutdooraddtocart.beans.ResponseCartDTO;
import com.cg.iter.greatoutdooraddtocart.dto.OrderDTO;
import com.cg.iter.greatoutdooraddtocart.dto.ProductDTO;
import com.cg.iter.greatoutdooraddtocart.exception.NullParameterException;
import com.cg.iter.greatoutdooraddtocart.service.OrderAndCartService;

import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.apache.log4j.Logger;


@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class OrderController {
	
	private static final Logger logger = Logger.getLogger(OrderController.class);
	
	@Autowired
	OrderAndCartService orderAndCartService;
	
	
	@ApiOperation(
			value = "Add products to cart",
			notes = "Retailer can add product to cart with this API",
			response = String.class
			)
	@PostMapping("/addItemToCart")
	public String addItemToCart(@RequestBody ResponseCartDTO cartDTO) {
		
		if(cartDTO==null || cartDTO.getProductId().trim().length() == 0 || cartDTO.getQuantity()==0 ) { 
			logger.error("Null request, cart details not provided at /addItemToCart");
			throw new NullParameterException("Null request, please provide cart details!");
		}
		
		String status = "Item add successfully!";	
		orderAndCartService.addItemToCart(cartDTO);
			
		return status;
		
		
	}
	
	
	
	@ApiOperation(
			value = "Place an order",
			notes = "Retailer can place an order with this API",
			response = String.class
			)
	@PostMapping("/placeOrder")
	public String placeOrder(@RequestParam String userId, @RequestParam String addressId , @RequestParam double totalCost) {
		
		if(userId==null || addressId==null) {
			logger.error("Null request, userId or addressId not provided at /placeOrder");
			throw new NullParameterException("Null request, please provide userId and addressId!");
		}
		
		String status = "Order placed successfully";
		OrderDTO order = new OrderDTO();
		order.setAddressId(addressId);
		order.setUserId(userId);
		order.setTotalcost(totalCost);
		orderAndCartService.registerOrder(order);


		return status;
	
	}
	
	
	
	@ApiOperation(
			value = "Remove product from cart",
			notes = "Retailer can remove product from cart with this API",
			response = String.class
			)
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
	
	
	
	@ApiOperation(
			value = "Remove product from cart by product and user Id",
			notes = "Retailer can remove product from cart in this API",
			response = String.class
			)
	@DeleteMapping("/removeFromCartById")
	public String removeItemFromCart(@RequestParam String userId , @RequestParam String productId){
		
		
		if(userId==null || userId==null ) { 
			logger.error("Null request, cart details are not provided at /removeFromCart");
			throw new NullParameterException("Null request, please provide cart details to remove iteam from cart!");
		}
		
		String status = "Item removed successfully!";		
		orderAndCartService.removeItemFromCart(userId , productId);
		return status;
		
	}
	
	
	
	@ApiOperation(
			value = "View all products",
			notes = "Retailer can view all products in the cart with this API",
			response = List.class
			)
	@GetMapping("/viewProductsFromCart")
	List<ProductDTO> viewCart(){
		return orderAndCartService.getProductsFromCart();
	}
	
	
	
	@ApiOperation(
			value = "View order products",
			notes = "Retailer can view all products in a perticular order with orderId with this API",
			response = List.class
			)
	@GetMapping("/viewOrderProducts")
	List<ProductDTO> viewOrderProducts(@RequestParam String orderId){
		return orderAndCartService.getOrderProducts(orderId);
	}
	
	
	
	

}
