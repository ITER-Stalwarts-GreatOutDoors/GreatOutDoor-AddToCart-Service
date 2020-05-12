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

@RestController
@RequestMapping("/order")
public class OrderProductCancelController {

	private static final Logger logger = Logger.getLogger(OrderProductCancelController.class);
	@Autowired
	OrderAndCartService orderAndCartService;
	
	@GetMapping("/getOrders/byOrderId")
	public Orders getAllOrdersWithOrderId(@RequestParam String orderId){
		
		if(orderId==null) {
			logger.error("Null request, orderId not provided for /getOrders/byOrderId");
			throw new NullParameterException("Null request, please provide orderId!");
		}
		return orderAndCartService.getAllOrdersWithOrderId(orderId);
	}
	
	@PostMapping("/cancelOrder")
	public String cancelOrder(@RequestParam String orderId ) {
		if(orderId==null) {
			logger.error("Null request, orderId not provided for /cancelOrder");
			throw new NullParameterException("Null request, please provide orderId!");
		}
		orderAndCartService.cancelOrder(orderId);
		return "successfully removed";
	}
	
	@GetMapping("/getOrders/byOrderIdProductId")
	public Orders getAllOrdersWithOrderIdProductId(@RequestParam String orderId , @RequestParam String productId){
		if(orderId==null || productId==null) {
			logger.error("Null request, orderId or productId not provided for /getOrders/byOrderIdProductId");
			throw new NullParameterException("Null request, please provide  orderId and productId!");
		}
		return orderAndCartService.getAllOrdersWithOrderIdProductId(orderId , productId);
	}
	
	
	@PostMapping("/cancelProduct")
	public String cancelOrder(@RequestParam String orderId , @RequestParam String productId ) {
		if(orderId==null || productId==null) {
			logger.error("Null request, orderId or productId not provided for /cancelProduct");
			throw new NullParameterException("Null request, please provide  orderId and productId!");
		}
		orderAndCartService.cancelProduct(orderId , productId);
		return "successfully removed";
	}
	
	
}
