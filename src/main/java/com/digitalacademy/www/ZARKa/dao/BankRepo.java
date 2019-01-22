package com.digitalacademy.www.ZARKa.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitalacademy.www.ZARKa.entities.Bank;


@Service
@Transactional
@Component
public interface BankRepo extends CrudRepository<Bank,Integer>{

	Bank findById(int i);

	
}
