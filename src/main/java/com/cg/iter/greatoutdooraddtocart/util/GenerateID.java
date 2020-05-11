package com.cg.iter.greatoutdooraddtocart.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.cg.iter.greatoutdooraddtocart.service.OrderAndCartService;



public class GenerateID {

	@Autowired
	OrderAndCartService service;
	
	public String generateOrderId() {
		String orderId = "";
		if(service.getOrderTableSize()==0) {
			orderId = "ORD0";
		}
		else {
			orderId = "ORD"+service.getOrderTableSize();
		}
		return orderId;
	}
	
	public String generateProductUIN() {
		return "UIN"+service.getOrderProductMapTableSize();
	}
}
