package com.cg.iter.greatoutdooraddtocart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cg.iter.greatoutdooraddtocart.dto.CartDTO;
import com.cg.iter.greatoutdooraddtocart.dto.OrderDTO;

public interface CartRepository extends CrudRepository<CartDTO, String>{

	@Modifying
	@Query("DELETE FROM CartDTO opm WHERE opm.userId=:userId and opm.productId=:productId")
	@Transactional
	void removeItemFromCart(@Param("userId") String userId, @Param("productId") String productId);
	
	@Modifying
	@Query("UPDATE CartDTO opm SET opm.quantity=:quantity WHERE opm.userId=:userId and opm.productId=:productId")
	@Transactional
	void updateItemInCart(@Param("userId") String userId, @Param("productId") String productId , @Param("quantity") int quantity);
	
	
	@Modifying
	@Query("SELECT products FROM CartDTO products WHERE products.userId=:userId")
	@Transactional
	List<CartDTO> getAllProducts(@Param("userId") String userId);
}
