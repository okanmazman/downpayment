package com.downpayment.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.downpayment.domain.Deposit;
import com.downpayment.domain.DepositRequest;
import com.downpayment.domain.Notification;
import com.downpayment.domain.User;

public interface DepositRequestRepository extends CrudRepository<DepositRequest, Long> {
 
	DepositRequest findById(long id);
	@Query(value="SELECT * FROM downpayment.deposit_request dr,downpayment.deposit d where dr.related_deposit_id=d.id and d.sent_to_user_id=:userId",nativeQuery=true)
	public List<DepositRequest> findByUserId(@Param("userId")Long userId);
	Set<DepositRequest>findAll();
}
