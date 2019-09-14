package com.downpayment.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;



import com.downpayment.domain.Notification;
import com.downpayment.domain.Product;


public interface ProductRepository extends CrudRepository<Product, Long> {
 
	Product findById(long id);

	@Query(value="SELECT * FROM downpayment.product n where n.user_id=:userId",nativeQuery=true)
	public Set<Product> findByUserId(@Param("userId")Long userId);
}
