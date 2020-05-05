package com.cg.iter.greatoutdooraddtocart.service;




import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.stereotype.Service;

import com.cg.iter.greatoutdooraddtocart.dto.CartDTO;
import com.cg.iter.greatoutdooraddtocart.dto.OrderDTO;
import com.cg.iter.greatoutdooraddtocart.dto.OrderProductMapDTO;
import com.cg.iter.greatoutdooraddtocart.exception.OrderException;
import com.cg.iter.greatoutdooraddtocart.repository.CartRepository;
import com.cg.iter.greatoutdooraddtocart.repository.OrderProductMapRepository;
import com.cg.iter.greatoutdooraddtocart.repository.OrderRepository;

@Service
public class OrderAndCartServiceImpl implements OrderAndCartService{

	//private Logger logger = Logger.getLogger(OrderAndCartServiceImpl.class);
	
	@Autowired
	CartRepository cartRepository;
	@Autowired
	OrderProductMapRepository orderProductMapRepository;
	@Autowired
	OrderRepository orderRepository;
	
	@Override
	public boolean addItemToCart(CartDTO cartItem) throws OrderException {
		
		String orderId = "";
		if(orderRepository.count()==0) {
			orderId = "ORD0";
		}
		else {
			orderId = "ORD"+orderRepository.count();
		}
		
		OrderProductMapDTO orderProductMap = new OrderProductMapDTO("UIN"+orderProductMapRepository.count(),
				orderId, cartItem.getProductId(), 1, 0);
		
		try {
	
			cartRepository.save(cartItem);
			
			
		} catch (RecoverableDataAccessException  e) {
			
			throw new OrderException("distributed transaction exception!");
		} catch (ScriptException  e) {
			
			throw new OrderException("Not well-formed script or error SQL command exception!");
			
		} catch (TransientDataAccessException e) {
			
			throw new OrderException("database timeout! exception!");
		}
		
		return true && insertOrderProductMapEntity(orderProductMap);
	}

	@Override
	public boolean insertOrderProductMapEntity(OrderProductMapDTO orderProductMapEntity) throws OrderException {
		
		try {
			
			orderProductMapRepository.save(orderProductMapEntity);
			
		} catch (RecoverableDataAccessException  e) {
			
			throw new OrderException("distributed transaction exception!");
			
		} catch (ScriptException  e) {
			
			throw new OrderException("Not well-formed script or error SQL command exception!");
			
		} catch (TransientDataAccessException e) {
			
			throw new OrderException("database timeout! exception!");
			
		}
		
		
		return true;
	}

	@Override
	public boolean removeItemFromCart(CartDTO cartItem) throws OrderException {
		
		String orderId = "";
		if(orderRepository.count()==0) {
			orderId = "ORD0";
		}
		else {
			orderId = "ORD"+orderRepository.count();
		}
		
		OrderProductMapDTO orderProduct = new OrderProductMapDTO();
		orderProduct.setOrderId(orderId);
		orderProduct.setProductId(cartItem.getProductId());
		
		try {
			
			
			cartRepository.delete(cartItem);
			 
			
		} catch (RecoverableDataAccessException  e) {
			
			throw new OrderException("distributed transaction exception!");
			
		} catch (ScriptException  e) {
			
			throw new OrderException("Not well-formed script or error SQL command exception!");
			
		} catch (TransientDataAccessException e) {
			
			throw new OrderException("database timeout! exception!");
			
		}
		
		
		return true && deleteOrderProductMapEntity(orderProduct);
	}

	@Override
	public boolean updateItemQuantity(CartDTO cartItem) throws OrderException {
		
		Optional<CartDTO> checkItem = cartRepository.findById(cartItem.getUserId());
		
		
		try {
			
			if(checkItem.isEmpty()) throw new Exception();
			cartRepository.save(cartItem);
			
		} catch (RecoverableDataAccessException  e) {
			
			throw new OrderException("distributed transaction exception!");
			
		} catch (ScriptException  e) {
			
			throw new OrderException("Not well-formed script or error SQL command exception!");
			
		} catch (TransientDataAccessException e) {
			
			throw new OrderException("database timeout! exception!");
			
		} catch (Exception e) {
			
			throw new OrderException("Item not present in the cart!");
		}
		
		return true;
	}

	@Override
	public boolean registerOrder(OrderDTO order) throws OrderException {
		
		String orderId = "ORD"+orderRepository.count();
		long millis = System.currentTimeMillis();
		java.sql.Date orderInitiationTime = new java.sql.Date(millis);
		OrderDTO newOrder = new OrderDTO(orderId,order.getUserId(),order.getAddressId(),(byte) 0,orderInitiationTime,null );
		
		try {
			
			orderRepository.save(newOrder);
			
		} catch (RecoverableDataAccessException  e) {
			
			throw new OrderException("distributed transaction exception!");
			
		} catch (ScriptException  e) {
			
			throw new OrderException("Not well-formed script or error SQL command exception!");
			
		} catch (TransientDataAccessException e) {
			
			throw new OrderException("database timeout! exception!");
			
		}
		
		return true;
	}

	@Override
	public boolean deleteOrderProductMapEntity(OrderProductMapDTO orderProductMapEntity) {
		orderProductMapRepository.deleteOrders(orderProductMapEntity.getOrderId(), orderProductMapEntity.getProductId());
		return true;
	}



}
