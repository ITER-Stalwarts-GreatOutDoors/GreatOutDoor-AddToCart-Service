package com.cg.iter.greatoutdooraddtocart.repository;

import org.springframework.data.repository.CrudRepository;

import com.cg.iter.greatoutdooraddtocart.dto.CartDTO;

public interface CartRepository extends CrudRepository<CartDTO, String>{

}
