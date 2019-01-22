package com.digitalacademy.www.ZARKa.entities;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;


@Component
@Entity
@Table(name="CurrencyValues")
public class CurrencyValues{	 
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int cv_id;
	
	
	private int b_id;
	
	private int c_id;
	
	private double bank_buy;
	
	private double bank_sell;
	
	private Timestamp in_date;
	
	private byte active;
	
	
	public CurrencyValues() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public CurrencyValues(int b_id, int c_id, double bank_buy, double bank_sell, Timestamp insert_date, byte active) {
		super();
		this.b_id = b_id;
		this.c_id = c_id;
		this.bank_buy = bank_buy;
		this.bank_sell = bank_sell;
		this.in_date = insert_date;
		this.active = active;
	}


	public int getB_id() {
		return b_id;
	}

	public void setB_id(int b_id) {
		this.b_id = b_id;
	}

	public int getC_id() {
		return c_id;
	}

	public void setC_id(int c_id) {
		this.c_id = c_id;
	}

	public double getBank_buy() {
		return bank_buy;
	}

	public void setBank_buy(double bank_buy) {
		this.bank_buy = bank_buy;
	}

	public double getBank_sell() {
		return bank_sell;
	}

	public void setBank_sell(double bank_sell) {
		this.bank_sell = bank_sell;
	}

	public Timestamp getInsert_date() {
		return in_date;
	}

	public void setInsert_date(Timestamp insert_date) {
		this.in_date = insert_date;
	}

	public byte getActive() {
		return active;
	}

	public void setActive(byte active) {
		this.active = active;
	}



	@Override
	public String toString() {
		return "CurrencyValues [cv_id=" + cv_id + ", b_id=" + b_id + ", c_id=" + c_id + ", bank_buy=" + bank_buy
				+ ", bank_sell=" + bank_sell + ", in_date=" + in_date + ", active=" + active + "]";
	}


	


}

