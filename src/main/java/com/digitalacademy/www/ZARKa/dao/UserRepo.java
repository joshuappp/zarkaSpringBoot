package com.digitalacademy.www.ZARKa.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitalacademy.www.ZARKa.entities.User;

@Service
@Transactional
@Component
public interface UserRepo extends CrudRepository<User,Long>{

		@Query(value="CALL getUser(?)", nativeQuery=true)
		Optional<Object> getUserBy(String email);
		
		User findByEmail(String email);
		
}
