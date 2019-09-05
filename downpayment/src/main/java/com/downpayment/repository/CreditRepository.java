package com.downpayment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.downpayment.domain.Credit;
import com.downpayment.domain.Currency;
import com.downpayment.domain.User;



public interface CreditRepository extends CrudRepository<Credit, Long> {
	List<Credit>findAll();
	
	@Query(value="SELECT * FROM downpayment.credit c where c.user_id=:userId",nativeQuery=true)
	public Optional<Credit> findById(@Param("userId")Long userId);
}
