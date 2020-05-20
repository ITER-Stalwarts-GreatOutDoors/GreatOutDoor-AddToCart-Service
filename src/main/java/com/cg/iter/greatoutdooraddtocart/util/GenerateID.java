package com.cg.iter.greatoutdooraddtocart.util;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.iter.greatoutdooraddtocart.service.OrderAndCartService;


@Component
public class GenerateID {

	@Autowired
	OrderAndCartService service;
	
	public String generateOrderId(String userId) {
		String orderId = "";
		Random rand = new Random();
		int selected = rand.nextInt(1000);
		orderId = "ORD"+service.getOrderTableSize()+userId+selected;
		return orderId;
	}
	
	public String generateProductUIN() {
		Random rand = new Random();
		int selected = rand.nextInt(1000);
		return "UIN"+selected+service.getOrderProductMapTableSize();
	}
}
