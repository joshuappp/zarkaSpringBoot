package com.digitalacademy.www.ZARKa.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitalacademy.www.ZARKa.entities.CurrencyValues;

@Service
@Transactional
@Component
public interface CurrencyValuesRepo extends CrudRepository<CurrencyValues,Integer>{

	// getting data from the database to the API controller
	@Query(value="CALL getLatestData", nativeQuery=true)
	ArrayList<Object> findByB_Id();
	
	
	// getting All banks data from the database to the API controller
	@Query(value="CALL getHistory", nativeQuery=true)
	ArrayList<Object> findByCv_Id();
	
}

