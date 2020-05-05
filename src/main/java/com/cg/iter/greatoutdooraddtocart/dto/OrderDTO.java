package com.cg.iter.greatoutdooraddtocart.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "order_table")
public class OrderDTO {

	@Id
	@Column(name = "order_id" ,nullable = false)
	private String orderId;
	@Column(name = "retailer_id" ,nullable = false)
	private String userId;
	@Column(name = "address_id" ,nullable = false)
	private String addressId;
	@Column(name = "order_dispatch_status" ,nullable = false)
	private byte orderDispatchStatus;
	@Column(name = "order_initiate_time")
	private Date orderInitiateTime;
	@Column(name = "order_dispatch_time")
	private Date orderDispatchTime;
	
	public OrderDTO() {}

	public OrderDTO(String orderId, String userId, String addressId, byte orderDispatchStatus, Date orderInitiateTime,
			Date orderDispatchTime) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.addressId = addressId;
		this.orderDispatchStatus = orderDispatchStatus;
		this.orderInitiateTime = orderInitiateTime;
		this.orderDispatchTime = orderDispatchTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public byte getOrderDispatchStatus() {
		return orderDispatchStatus;
	}

	public void setOrderDispatchStatus(byte orderDispatchStatus) {
		this.orderDispatchStatus = orderDispatchStatus;
	}

	public Date getOrderInitiateTime() {
		return orderInitiateTime;
	}

	public void setOrderInitiateTime(Date orderInitiateTime) {
		this.orderInitiateTime = orderInitiateTime;
	}

	public Date getOrderDispatchTime() {
		return orderDispatchTime;
	}

	public void setOrderDispatchTime(Date orderDispatchTime) {
		this.orderDispatchTime = orderDispatchTime;
	}
	
	
	
	
}
