package com.cg.iter.greatoutdooraddtocart.beans;

import java.util.Date;
import java.util.List;

import com.cg.iter.greatoutdooraddtocart.dto.OrderProductMapDTO;


public class InvoiceResponse {

	private List<OrderProductMapDTO> orderList;
	private String addressId;
	private Date orderInitiateTime;
	private double totalcost;
	private String userId;
	
	public InvoiceResponse() {}

	public InvoiceResponse(List<OrderProductMapDTO> orderList, String addressId, Date orderInitiateTime,
			double totalcost, String userId) {
		super();
		this.orderList = orderList;
		this.addressId = addressId;
		this.orderInitiateTime = orderInitiateTime;
		this.totalcost = totalcost;
		this.userId = userId;
	}

	public List<OrderProductMapDTO> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderProductMapDTO> orderList) {
		this.orderList = orderList;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public Date getOrderInitiateTime() {
		return orderInitiateTime;
	}

	public void setOrderInitiateTime(Date orderInitiateTime) {
		this.orderInitiateTime = orderInitiateTime;
	}

	public double getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(double totalcost) {
		this.totalcost = totalcost;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	
	
	
	
	
	
}
