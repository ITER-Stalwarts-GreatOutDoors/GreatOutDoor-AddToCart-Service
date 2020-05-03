package com.cg.iter.greatoutdooraddtocart.repository;

import org.springframework.data.repository.CrudRepository;

import com.cg.iter.greatoutdooraddtocart.dto.OrderDTO;


public interface OrderRepository extends CrudRepository<OrderDTO, String>{

}
