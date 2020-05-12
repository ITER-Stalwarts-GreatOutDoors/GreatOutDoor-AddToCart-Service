package com.cg.iter.greatoutdooraddtocart.beans;

import java.util.List;

import com.cg.iter.greatoutdooraddtocart.dto.OrderProductMapDTO;

public class Orders {

	private List<OrderProductMapDTO> orderList;

	public Orders() {}

	public Orders(List<OrderProductMapDTO> orderList) {
		super();
		this.orderList = orderList;
	}

	public List<OrderProductMapDTO> getOrders() {
		return orderList;
	}

	public void setOrders(List<OrderProductMapDTO> orderList) {
		this.orderList = orderList;
	}
	
}
