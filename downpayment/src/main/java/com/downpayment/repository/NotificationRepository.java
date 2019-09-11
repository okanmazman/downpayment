package com.downpayment.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;



import com.downpayment.domain.Notification;


public interface NotificationRepository extends CrudRepository<Notification, Long> {
 
	Notification findById(int id);

	@Query(value="SELECT * FROM downpayment.notification n where n.user_id=:userId",nativeQuery=true)
	public Set<Notification> findByUserId(@Param("userId")Long userId);
}
