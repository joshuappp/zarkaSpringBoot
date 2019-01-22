package com.digitalacademy.www.ZARKa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;


import org.springframework.stereotype.Component;

@Entity
@Table(name="Bank")
@Component
public class Bank {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String b_name;
	
	private String image;
	
	private byte active;
	
	public Bank() {
		super();
		
	}
	
	public Bank(String name, String image, byte active) {
		super();
		this.b_name = name;
		this.image = image;
		this.active = active;
	}



	public int getB_id() {
		return id;
	}

	public void setB_id(int b_id) {
		this.id = b_id;
	}

	public String getName() {
		return b_name;
	}

	public void setName(String name) {
		this.b_name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public byte getActive() {
		return active;
	}

	public void setActive(byte active) {
		this.active = active;
	}
	

	@Override
	public String toString() {
		return "Bank [b_id=" + id + ", name=" + b_name + ", image=" + image + ", active=" + active + "]";
	}


}

