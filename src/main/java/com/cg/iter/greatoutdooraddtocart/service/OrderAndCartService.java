package com.cg.iter.greatoutdooraddtocart.service;



import com.cg.iter.greatoutdooraddtocart.beans.Orders;
import com.cg.iter.greatoutdooraddtocart.beans.ResponseCartDTO;
import com.cg.iter.greatoutdooraddtocart.dto.CartDTO;
import com.cg.iter.greatoutdooraddtocart.dto.OrderDTO;
import com.cg.iter.greatoutdooraddtocart.dto.OrderProductMapDTO;
import com.cg.iter.greatoutdooraddtocart.exception.OrderNotFoundException;


public interface OrderAndCartService {


	
	/*
	 * name - add to cart
	 * description - It will add an item to the cart.
	 */
	boolean addItemToCart(ResponseCartDTO cartItem);
	
	

	/*
	 * name - insert order-product map entity
	 * description - register an item against a particular order
	 */
	boolean insertOrderProductMapEntity(OrderProductMapDTO orderProductMapEntity);
	
	

	/*
	 * name - remove item from the cart
	 * description - it will remove available item from the cart
	 */
	boolean removeItemFromCart(ResponseCartDTO cartItem);
	

	
	/*
	 * name - updateItemQuantity
	 * description - update the amount of existing product
	 */
	boolean updateItemQuantity(CartDTO cartItem);
	
	

	/*
	 * name - registerOrder
	 * description - register a new order
	 */
	boolean registerOrder(OrderDTO order);
	
	

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
	Orders getAllOrdersWithOrderId(String orderId) throws OrderNotFoundException;



	
	/*
	 * name:getAllOrderWithOrderIdProductId
	 * description:Accesing all the orders with a given orderId and productId
	 */

	Orders getAllOrdersWithOrderIdProductId(String orderId, String productId) throws OrderNotFoundException;

    /*
     * name-cancelProduct
     * description:cancel a product of particular order that is placed
     */
	void cancelProduct(String orderId, String productId);
	
	long getOrderTableSize();
	
	long getOrderProductMapTableSize();
}
