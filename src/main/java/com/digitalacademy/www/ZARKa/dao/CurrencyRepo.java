package com.digitalacademy.www.ZARKa.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitalacademy.www.ZARKa.entities.Currency;


@Service
@Transactional
@Component
public interface CurrencyRepo extends CrudRepository<Currency,Integer>{
	
	Currency findById(int i);
	

}
