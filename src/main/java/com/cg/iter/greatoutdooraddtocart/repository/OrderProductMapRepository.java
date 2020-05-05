package com.cg.iter.greatoutdooraddtocart.repository;



import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cg.iter.greatoutdooraddtocart.dto.OrderProductMapDTO;

public interface OrderProductMapRepository extends CrudRepository<OrderProductMapDTO, String>{
	
	@Modifying
	@Query("DELETE FROM OrderProductMapDTO opm WHERE opm.orderId=:orderId and opm.productId=:productId")
	@Transactional
	void deleteOrders(@Param("orderId") String orderId, @Param("productId") String productId);

}
