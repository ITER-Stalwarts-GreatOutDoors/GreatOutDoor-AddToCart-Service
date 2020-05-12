package com.cg.iter.greatoutdooraddtocart.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.cg.iter.greatoutdooraddtocart.beans.Orders;
import com.cg.iter.greatoutdooraddtocart.service.OrderAndCartService;

@RestController
@RequestMapping("/order")
public class OrderProductCancelController {

	
	@Autowired
	OrderAndCartService orderAndCartService;
	
	@GetMapping("/getOrders/byOrderId")
	Orders getAllOrdersWithOrderId(@RequestParam String orderId){
		return orderAndCartService.getAllOrdersWithOrderId(orderId);
	}
	
	@PostMapping("/cancelOrder")
	public String cancelOrder(@RequestParam String orderId ) {
		orderAndCartService.cancelOrder(orderId);
		return "successfully removed";
	}
	
	@GetMapping("/getOrders/byOrderIdProductId")
	Orders getAllOrdersWithOrderIdProductId(@RequestParam String orderId , @RequestParam String productId){
		return orderAndCartService.getAllOrdersWithOrderIdProductId(orderId , productId);
	}
	
	
	@PostMapping("/cancelProduct")
	public String cancelOrder(@RequestParam String orderId , @RequestParam String productId ) {
		orderAndCartService.cancelProduct(orderId , productId);
		return "successfully removed";
	}
	
	
}
