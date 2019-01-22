package com.digitalacademy.www.ZARKa.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Component
@Table(name="settings")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Settings  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private boolean notify = false;

	private String via = "Undefined";
	
	private String bank = "Undefined";

	private String sellOrBuy = "Undefined";
	
	private String currency = "Undefined";
	
	private double buyAmountNotify;			//Buy Amount to notify the user based on. e.g: 15.40
	
	private double sellAmountNotify;		//Sell Amount to notify the user based on. e.g: 15.20
	
	private String reportDay = "Undefined";				//Date to send report.  e.g: Friday
	
	private String reportTime = "17:00";				//Default Time to send reports.  e.g: 17:00
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false, unique= true)
	private User user;

	public Settings()
	{
		super();
	}
	

	public Settings(Long id, boolean notify, String via, String bank, String sellOrBuy,
			String currency, double buyAmountNotify, double sellAmountNotify, String reportDay, String reportTime,
			User user) 
	{
		super();
		this.id = id;
		this.notify = notify;
		this.via = via;
		this.bank = bank;
		this.sellOrBuy = sellOrBuy;
		this.currency = currency;
		this.buyAmountNotify = buyAmountNotify;
		this.sellAmountNotify = sellAmountNotify;
		this.reportDay = reportDay;
		this.reportTime = reportTime;
		this.user = user;
	}





	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public boolean isNotify() {
		return notify;
	}


	public void setNotify(boolean notify) {
		this.notify = notify;
	}


	public String getVia() {
		return via;
	}


	public void setVia(String via) {
		this.via = via;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getSellOrBuy() {
		return sellOrBuy;
	}

	public void setSellOrBuy(String sellOrBuy) {
		this.sellOrBuy = sellOrBuy;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getBuyAmountNotify() {
		return buyAmountNotify;
	}

	public void setBuyAmountNotify(double buyAmountNotify) {
		this.buyAmountNotify = buyAmountNotify;
	}

	public double getSellAmountNotify() {
		return sellAmountNotify;
	}

	public void setSellAmountNotify(double sellAmountNotify) {
		this.sellAmountNotify = sellAmountNotify;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getReportDay() {
		return reportDay;
	}


	public void setReportDay(String reportDay) {
		this.reportDay = reportDay;
	}


	public String getReportTime() {
		return reportTime;
	}


	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}


	@Override
	public String toString() {
		return "Settings [id=" + id + ", notify=" + notify + ", via=" + via + ", bank=" + bank + ", sellOrBuy=" + sellOrBuy 
				+ ", currency=" + currency + ", buyAmountNotify=" + buyAmountNotify + ", sellAmountNotify=" + sellAmountNotify 
				+ ", reportDay=" + reportDay + ", reportTime=" + reportTime + ", user=" + user + "]";
	}

}
