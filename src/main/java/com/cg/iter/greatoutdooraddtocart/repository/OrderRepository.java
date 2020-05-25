package com.cg.iter.greatoutdooraddtocart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.cg.iter.greatoutdooraddtocart.dto.OrderDTO;



public interface OrderRepository extends CrudRepository<OrderDTO, String>{
	
	@Modifying
	@Query("SELECT order FROM OrderDTO order WHERE order.userId=:userId")
	@Transactional
	List<OrderDTO> getAllOrders(String userId);
}
