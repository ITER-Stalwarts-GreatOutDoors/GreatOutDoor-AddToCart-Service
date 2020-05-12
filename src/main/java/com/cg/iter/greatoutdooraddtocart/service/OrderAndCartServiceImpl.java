package com.cg.iter.greatoutdooraddtocart.service;


import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.stereotype.Service;

import com.cg.iter.greatoutdooraddtocart.beans.Orders;
import com.cg.iter.greatoutdooraddtocart.dto.CartDTO;
import com.cg.iter.greatoutdooraddtocart.dto.OrderDTO;
import com.cg.iter.greatoutdooraddtocart.dto.OrderProductMapDTO;
import com.cg.iter.greatoutdooraddtocart.exception.OrderNotFoundException;
import com.cg.iter.greatoutdooraddtocart.repository.CartRepository;
import com.cg.iter.greatoutdooraddtocart.repository.OrderProductMapRepository;
import com.cg.iter.greatoutdooraddtocart.repository.OrderRepository;
import com.cg.iter.greatoutdooraddtocart.util.GenerateID;


@Service
public class OrderAndCartServiceImpl implements OrderAndCartService{

	private Logger logger = Logger.getLogger(OrderAndCartServiceImpl.class);
	
	@Autowired
	CartRepository cartRepository;
	@Autowired
	OrderProductMapRepository orderProductMapRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	GenerateID generate;
	
	@Override
	public boolean addItemToCart(CartDTO cartItem) throws Exception {
		
		
		
		try {
	
			cartRepository.save(cartItem);
			
			
		} catch (RecoverableDataAccessException  e) {
			
			throw new Exception("distributed transaction exception!");
		} catch (ScriptException  e) {
			
			throw new Exception("Not well-formed script or error SQL command exception!");
			
		} catch (TransientDataAccessException e) {
			
			throw new Exception("database timeout! exception!");
		}
		
		return true;
	}

	@Override
	public boolean insertOrderProductMapEntity(OrderProductMapDTO orderProductMapEntity) throws Exception {
		
		try {
			
			orderProductMapRepository.save(orderProductMapEntity);
			
		} catch (RecoverableDataAccessException  e) {
			
			throw new Exception("distributed transaction exception!");
			
		} catch (ScriptException  e) {
			
			throw new Exception("Not well-formed script or error SQL command exception!");
			
		} catch (TransientDataAccessException e) {
			
			throw new Exception("database timeout! exception!");
			
		}
		
		
		return true;
	}

	@Override
	public boolean removeItemFromCart(CartDTO cartItem) throws Exception {
		
		try {
			
			cartRepository.delete(cartItem);
			 
			
		} catch (RecoverableDataAccessException  e) {
			
			throw new Exception("distributed transaction exception!");
			
		} catch (ScriptException  e) {
			
			throw new Exception("Not well-formed script or error SQL command exception!");
			
		} catch (TransientDataAccessException e) {
			
			throw new Exception("database timeout! exception!");
			
		}
		
		
		return true ;
	}

	@Override
	public boolean updateItemQuantity(CartDTO cartItem) throws Exception {
		
		Optional<CartDTO> checkItem = cartRepository.findById(cartItem.getUserId());
		
		
		try {
			
			if(checkItem.isEmpty()) throw new Exception();
			cartRepository.save(cartItem);
			
		} catch (RecoverableDataAccessException  e) {
			
			throw new Exception("distributed transaction exception!");
			
		} catch (ScriptException  e) {
			
			throw new Exception("Not well-formed script or error SQL command exception!");
			
		} catch (TransientDataAccessException e) {
			
			throw new Exception("database timeout! exception!");
			
		} catch (Exception e) {
			
			throw new Exception("Item not present in the cart!");
		}
		
		return true;
	}

	@Override
	public boolean registerOrder(OrderDTO order) throws Exception {
		
		////////////////////////////////////////////////
		
		//insert the item from the cart to order product map table
		
		List<CartDTO> cartItems = (List<CartDTO>) cartRepository.findAll();
		Iterator<CartDTO> itr = cartItems.iterator();
		int index = 0;
		
		while (itr.hasNext()) {
			
			OrderProductMapDTO orderProductMap = new OrderProductMapDTO(generate.generateProductUIN(),
			generate.generateOrderId(), cartItems.get(index).getProductId(), 1, 0);
			insertOrderProductMapEntity(orderProductMap);
			index++;
			itr.next();
		}
		
		
		///////////////////////////////////////////////
		
		
		long millis = System.currentTimeMillis();
		Date orderInitiationTime = new Date(millis);
		
		OrderDTO newOrder = new OrderDTO(generate.generateOrderId(),order.getUserId()
				,order.getAddressId(),(byte) 0,orderInitiationTime,null );
		
		
		try {
			
			orderRepository.save(newOrder);
			
		} catch (RecoverableDataAccessException  e) {
			
			throw new Exception("distributed transaction exception!");
			
		} catch (ScriptException  e) {
			
			throw new Exception("Not well-formed script or error SQL command exception!");
			
		} catch (TransientDataAccessException e) {
			
			throw new Exception("database timeout! exception!");
			
		}
		
		return true;
	}

	@Override
	public boolean deleteOrderProductMapEntity(OrderProductMapDTO orderProductMapEntity) {
		orderProductMapRepository.deleteOrders(orderProductMapEntity.getOrderId(), orderProductMapEntity.getProductId());
		return true;
	}

	@Override
	public boolean deleteOrder(OrderDTO order) {
		orderRepository.delete(order);
		return true;
	}

	@Override
	public void cancelOrder(String orderId) {
		orderProductMapRepository.deleteOrderByOrderId(orderId);
		orderRepository.deleteById(orderId);
	}


	@Override
	public Orders getAllOrdersWithOrderId(String orderId) {
		Orders orders = new Orders();
		orders.setOrders(orderProductMapRepository.getAllOrdersById(orderId));
		if(orders.getOrders().isEmpty()) {
			logger.error("No orders with this orderId!");
			throw new OrderNotFoundException("No orders with orderId "+orderId+" is available!");
		}
		return orders;
	}


	@Override
	public Orders getAllOrdersWithOrderIdProductId(String orderId, String productId) {
		Orders orders = new Orders();
		orders.setOrders(orderProductMapRepository.getAllOrdersByOrderIdProductId(orderId,productId));
		if(orders.getOrders().isEmpty()) {
			logger.error("No orders with this orderId is available!");
			throw new OrderNotFoundException("No orders with orderId "+orderId+" and productId "+productId+" is available");
		}
		return orders;
	}

	@Override
	public void cancelProduct(String orderId, String productId) {
		orderProductMapRepository.deleteOrders(orderId, productId);
	}

	@Override
	public long getOrderTableSize() {
		return orderRepository.count();
	}

	@Override
	public long getOrderProductMapTableSize() {
		return orderProductMapRepository.count();
	}



}
