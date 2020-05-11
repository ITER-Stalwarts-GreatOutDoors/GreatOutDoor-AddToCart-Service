package com.cg.iter.greatoutdooraddtocart.beans;

import java.util.List;

import com.cg.iter.greatoutdooraddtocart.dto.OrderProductMapDTO;

public class Orders {

	private List<OrderProductMapDTO> orders;

	public Orders() {}

	public Orders(List<OrderProductMapDTO> orders) {
		super();
		this.orders = orders;
	}

	public List<OrderProductMapDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderProductMapDTO> orders) {
		this.orders = orders;
	}
	
}
