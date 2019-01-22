package com.digitalacademy.www.ZARKa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;


@Component
@Entity
@Table(name="Currency")
public class Currency {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String c_name;
	
	private String c_short;
	
	private String image;
	
	private byte active;
	
	public Currency() {
		super();
		
	}
	public Currency(String name, String c_short,String image ,byte active) {
		super();
		this.c_name = name;
		this.c_short = c_short;
		this.image = image;
		this.active = active;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	public int getC_id() {
		return id;
	}
	public void setC_id(int c_id) {
		this.id = c_id;
	}
	public String getName() {
		return c_name;
	}
	public void setName(String name) {
		this.c_name = name;
	}
	public String getC_short() {
		return c_short;
	}
	public void setC_short(String c_short) {
		this.c_short = c_short;
	}
	public byte getActive() {
		return active;
	}
	public void setActive(byte active) {
		this.active = active;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Currency [c_id=" + id + ", name=" + c_name + ", c_short=" + c_short + ", image=" + image + ", active="
				+ active + "]";
	}
	
		

}

