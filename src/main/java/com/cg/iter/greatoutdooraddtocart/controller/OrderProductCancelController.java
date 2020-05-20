package com.cg.iter.greatoutdooraddtocart.controller;




import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.cg.iter.greatoutdooraddtocart.beans.Orders;
import com.cg.iter.greatoutdooraddtocart.exception.NullParameterException;
import com.cg.iter.greatoutdooraddtocart.service.OrderAndCartService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/order")
public class OrderProductCancelController {

	private static final Logger logger = Logger.getLogger(OrderProductCancelController.class);
	@Autowired
	OrderAndCartService orderAndCartService;
	
	@ApiOperation(
			value = "Get all orders with orderId",
			notes = "Cancel management API can get all placed order list with this API",
			response = Orders.class
			)
	@GetMapping("/getOrders/byOrderId")
	public Orders getAllOrdersWithOrderId(@RequestParam String orderId){
		
		if(orderId==null) {
			logger.error("Null request, orderId not provided for /getOrders/byOrderId");
			throw new NullParameterException("Null request, please provide orderId!");
		}
		return orderAndCartService.getAllOrdersWithOrderId(orderId);
	}
	
	
	
	@ApiOperation(
			value = "Cancel order",
			notes = "Retailer can cancel placed order with this API",
			response = String.class
			)
	@PostMapping("/cancelOrder")
	public String cancelOrder(@RequestParam String orderId ) {
		if(orderId==null) {
			logger.error("Null request, orderId not provided for /cancelOrder");
			throw new NullParameterException("Null request, please provide orderId!");
		}
		orderAndCartService.cancelOrder(orderId);
		return "successfully removed";
	}
	
	
	@ApiOperation(
			value = "Get all orders with orderId and productId",
			notes = "Cancel management API can get all placed order list orderId and productId in this API",
			response = Orders.class
			)
	@GetMapping("/getOrders/byOrderIdProductId")
	public Orders getAllOrdersWithOrderIdProductId(@RequestParam String orderId , @RequestParam String productId){
		if(orderId==null || productId==null) {
			logger.error("Null request, orderId or productId not provided for /getOrders/byOrderIdProductId");
			throw new NullParameterException("Null request, please provide  orderId and productId!");
		}
		return orderAndCartService.getAllOrdersWithOrderIdProductId(orderId , productId);
	}
	
	
	
	@ApiOperation(
			value = "Cancel order product",
			notes = "Retailer can cancel a product in placed order with this API",
			response = String.class
			)
	@PostMapping("/cancelProduct")
	public String cancelOrderProduct(@RequestParam String orderId , @RequestParam String productId ) {
		if(orderId==null || productId==null) {
			logger.error("Null request, orderId or productId not provided for /cancelProduct");
			throw new NullParameterException("Null request, please provide  orderId and productId!");
		}
		orderAndCartService.cancelProduct(orderId , productId);
		return "successfully removed";
	}
	
	
}
