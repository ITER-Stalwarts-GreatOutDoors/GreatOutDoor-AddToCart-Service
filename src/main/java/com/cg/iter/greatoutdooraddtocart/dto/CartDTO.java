package com.cg.iter.greatoutdooraddtocart.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class CartDTO {

	@Id
	@Column(name = "retailer_id")
	private String userId;
	@Column(name = "product_id" , nullable = false )
	private String productId;
	@Column(name = "quantity" , nullable = false )
	private int quantity;
	
	public CartDTO() {}

	public CartDTO(String userId, String productId, int quantity) {
		super();
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
}
