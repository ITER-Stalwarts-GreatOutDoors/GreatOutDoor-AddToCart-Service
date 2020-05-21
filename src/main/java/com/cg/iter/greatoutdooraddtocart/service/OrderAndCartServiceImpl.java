package com.cg.iter.greatoutdooraddtocart.service;


import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.client.RestTemplate;

import com.cg.iter.greatoutdooraddtocart.beans.Orders;
import com.cg.iter.greatoutdooraddtocart.beans.ResponseCartDTO;
import com.cg.iter.greatoutdooraddtocart.dto.CartDTO;
import com.cg.iter.greatoutdooraddtocart.dto.OrderDTO;
import com.cg.iter.greatoutdooraddtocart.dto.OrderProductMapDTO;
import com.cg.iter.greatoutdooraddtocart.dto.ProductDTO;
import com.cg.iter.greatoutdooraddtocart.exception.CrudException;
import com.cg.iter.greatoutdooraddtocart.exception.OrderNotFoundException;
import com.cg.iter.greatoutdooraddtocart.repository.CartRepository;
import com.cg.iter.greatoutdooraddtocart.repository.OrderProductMapRepository;
import com.cg.iter.greatoutdooraddtocart.repository.OrderRepository;
import com.cg.iter.greatoutdooraddtocart.util.GenerateID;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;



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
	@Autowired
	RestTemplate restTemplate;
	
	private String productURL = "http://product-ms/product";
	private String dataAccessException = "distributed transaction exception!";
	private String scriptException = "Not well-formed script or error SQL command exception!";
	private String transientDataAccessException = "database timeout! exception!";
	
	
	
	/*
	 * name - add to cart
	 * description - It will add an item to the cart.
	 */
	@Override
	public boolean addItemToCart(ResponseCartDTO cart) {
		
		
		
		try {
			CartDTO cartItem = new CartDTO(cart.getUserId(), cart.getProductId(), cart.getQuantity());
			cartRepository.save(cartItem);
			
			
		} catch (RecoverableDataAccessException  e) {
			
			throw new CrudException(dataAccessException);
		} catch (ScriptException  e) {
			
			throw new CrudException(scriptException);
			
		} catch (TransientDataAccessException e) {
			
			throw new CrudException(transientDataAccessException);
		}
		
		return true;
	}

	
	/*
	 * name - insert order-product map entity
	 * description - register an item against a particular order
	 */
	@Override
	public boolean insertOrderProductMapEntity(OrderProductMapDTO orderProductMapEntity) {
		
		try {
			
			orderProductMapRepository.save(orderProductMapEntity);
			
		} catch (RecoverableDataAccessException  e) {
			
			throw new CrudException(dataAccessException);
			
		} catch (ScriptException  e) {
			
			throw new CrudException(scriptException);
			
		} catch (TransientDataAccessException e) {
			
			throw new CrudException(transientDataAccessException);
			
		}
		
		
		return true;
	}

	
	
	/*
	 * name - remove item from the cart
	 * description - it will remove available item from the cart
	 */
	@Override
	public boolean removeItemFromCart(ResponseCartDTO cart) {
		
		
		try {
			CartDTO cartItem = new CartDTO(cart.getUserId(), cart.getProductId(), cart.getQuantity());
			cartRepository.delete(cartItem);
			 
			
		} catch (RecoverableDataAccessException  e) {
			
			throw new CrudException(dataAccessException);
			
		} catch (ScriptException  e) {
			
			throw new CrudException(scriptException);
			
		} catch (TransientDataAccessException e) {
			
			throw new CrudException(transientDataAccessException);
			
		}
		
		
		return true ;
	}

	
	
	
	/*
	 * name - updateItemQuantity
	 * description - update the amount of existing product
	 */
	@Override
	public boolean updateItemQuantity(CartDTO cartItem){
		
		Optional<CartDTO> checkItem = cartRepository.findById(cartItem.getUserId());
		
		
		try {
			
			if(checkItem.isEmpty()) throw new Exception();
			cartRepository.save(cartItem);
			
		} catch (RecoverableDataAccessException  e) {
			
			throw new CrudException(dataAccessException);
			
		} catch (ScriptException  e) {
			
			throw new CrudException(scriptException);
			
		} catch (TransientDataAccessException e) {
			
			throw new CrudException(transientDataAccessException);
			
		} catch (Exception e) {
			
			throw new CrudException("Item not present in the cart!");
		}
		
		return true;
	}

	
	
	
	/*
	 * name - registerOrder
	 * description - register a new order
	 */
	@Override
	public boolean registerOrder(OrderDTO order){
		
		////////////////////////////////////////////////
		
		//insert the item from the cart to order product map table
		if(cartRepository.count()==0) throw new CrudException("Please item add to cart to place an order!");
		List<CartDTO> cartItems = (List<CartDTO>) cartRepository.findAll();
		Iterator<CartDTO> itr = cartItems.iterator();
		int index = 0;
		String orderId = generate.generateOrderId(order.getUserId());
		
		while (itr.hasNext()) {
			
			OrderProductMapDTO orderProductMap = new OrderProductMapDTO(generate.generateProductUIN(),
			orderId, cartItems.get(index).getProductId(), 1, 0);
			insertOrderProductMapEntity(orderProductMap);
			index++;
			itr.next();
		}
		
		
		///////////////////////////////////////////////
		
		
		long millis = System.currentTimeMillis();
		Date orderInitiationTime = new Date(millis);
		
		OrderDTO newOrder = new OrderDTO(orderId,order.getUserId()
				,order.getAddressId(),(byte) 0,orderInitiationTime,null );
		
		
		try {
			
			orderRepository.save(newOrder);
			
		} catch (RecoverableDataAccessException  e) {
			
			throw new CrudException(dataAccessException);
			
		} catch (ScriptException  e) {
			
			throw new CrudException(scriptException);
			
		} catch (TransientDataAccessException e) {
			
			throw new CrudException(transientDataAccessException);
			
		}
		
		return true;
	}

	
	
	
	/*
	 * name - delete order-product map entity
	 * description - delete an item against a particular order
	 */
	@Override
	public boolean deleteOrderProductMapEntity(OrderProductMapDTO orderProductMapEntity) {
		orderProductMapRepository.deleteOrders(orderProductMapEntity.getOrderId(), orderProductMapEntity.getProductId());
		return true;
	}

	
	
	/*
	 * name-deleteOrder
	 * description:delete the order
	 */
	@Override
	public boolean deleteOrder(OrderDTO order) {
		orderRepository.delete(order);
		return true;
	}

	
	
	
	/*
     * name-cancelOrder
     * description: Cancel a order that is placed
     */
	@Override
	public void cancelOrder(String orderId) {
		orderProductMapRepository.deleteOrderByOrderId(orderId);
		orderRepository.deleteById(orderId);
	}


	
	
	
	/*
	 * name-getAllOrdersWithOrderId
	 * description:Accesing all the orders with a given order id
	 */
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


	
	
	
	
	/*
	 * name:getAllOrderWithOrderIdProductId
	 * description:Accesing all the orders with a given orderId and productId
	 */
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

	
	
	
	
	/*
     * name-cancelProduct
     * description:cancel a product of particular order that is placed
     */
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

	
	
	/*
     * getProductsFromCart
     * description:retailer can get all products in the cart
     */
	@HystrixCommand(fallbackMethod = "getFallbackProducts",
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
			})
	@Override
	public List<ProductDTO> getProductsFromCart() {
		List<CartDTO> listCartItems = (List<CartDTO>) cartRepository.findAll();
		List<ProductDTO> listProducts = new ArrayList<>();
		
		Iterator<CartDTO> itr = listCartItems.iterator();
		int index = 0;
		
		while (itr.hasNext()) {
			ProductDTO product = restTemplate.getForObject(productURL+"/getProductById?productId="+listCartItems.get(index).getProductId(),
					ProductDTO.class);
			product.setQuantity(listCartItems.get(index).getQuantity());
			listProducts.add(product);
			index++;
			itr.next();
		}
		return listProducts;
	}

	
	/*
     * removeItemFromCart
     * description:retailer can remove a product in the cart
     */
	@Override
	public void removeItemFromCart(String userId, String productId) {
		cartRepository.removeItemFromCart(userId, productId);
		
	}

	
	/*
     * getFallbackProducts
     * description:fallback method for get products from cart.
     */
	public List<ProductDTO>getFallbackProducts(){
		return Arrays.asList(
				new ProductDTO("fallback productId", 
						00,"fallback colour","fallback dimension", "fallback specification","fallback manufacture", 0,0 ,"fallback productName", "fallback.com/fallback.jpg")
				
				);
	}

}
