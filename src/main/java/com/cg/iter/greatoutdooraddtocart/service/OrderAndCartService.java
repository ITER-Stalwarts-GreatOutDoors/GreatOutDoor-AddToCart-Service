package com.cg.iter.greatoutdooraddtocart.service;



import com.cg.iter.greatoutdooraddtocart.beans.Orders;
import com.cg.iter.greatoutdooraddtocart.dto.CartDTO;
import com.cg.iter.greatoutdooraddtocart.dto.OrderDTO;
import com.cg.iter.greatoutdooraddtocart.dto.OrderProductMapDTO;
import com.cg.iter.greatoutdooraddtocart.exception.OrderException;

public interface OrderAndCartService {


	
	/*
	 * name - add to cart
	 * description - It will add an item to the cart.
	 */
	boolean addItemToCart(CartDTO cartItem) throws OrderException;
	
	

	/*
	 * name - insert order-product map entity
	 * description - register an item against a particular order
	 */
	boolean insertOrderProductMapEntity(OrderProductMapDTO orderProductMapEntity) throws OrderException;
	
	

	/*
	 * name - remove item from the cart
	 * description - it will remove available item from the cart
	 */
	boolean removeItemFromCart(CartDTO cartItem) throws OrderException;
	

	
	/*
	 * name - updateItemQuantity
	 * description - update the amount of existing product
	 */
	boolean updateItemQuantity(CartDTO cartItem) throws OrderException;
	
	

	/*
	 * name - registerOrder
	 * description - register a new order
	 */
	boolean registerOrder(OrderDTO order) throws OrderException;
	
	

	/*
	 * name - delete order-product map entity
	 * description - delete an item against a particular order
	 */
	boolean deleteOrderProductMapEntity(OrderProductMapDTO orderProductMapEntity);
	

	
	/*
	 * name-deleteOrder
	 * description:delete the order
	 */
	boolean deleteOrder(OrderDTO order);


	
     /*
      * name-cancelOrder
      * description: Cancel a order that is placed
      */
	void cancelOrder(String orderId);


	
	/*
	 * name-getAllOrdersWithOrderId
	 * description:Accesing all the orders with a given order id
	 */ 
	Orders getAllOrdersWithOrderId(String orderId);



	
	/*
	 * name:getAllOrderWithOrderIdProductId
	 * description:Accesing all the orders with a given orderId and productId
	 */

	Orders getAllOrdersWithOrderIdProductId(String orderId, String productId);



    /*
     * name-cancelProduct
     * description:cancel a product of particular order that is placed
     */
	void cancelProduct(String orderId, String productId);
	
	long getOrderTableSize();
	
	long getOrderProductMapTableSize();
}
