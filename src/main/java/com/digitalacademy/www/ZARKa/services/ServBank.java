package com.digitalacademy.www.ZARKa.services;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.digitalacademy.www.ZARKa.dao.BankRepo;
import com.digitalacademy.www.ZARKa.dao.CurrencyValuesRepo;
import com.digitalacademy.www.ZARKa.interfaces.BankInt;

@Component
@EnableScheduling
public class ServBank implements BankInt{
	
		@Autowired
		ServBank servBank;
		
		@Autowired
		AbsaCrawler absaCrawler;
		
		@Autowired
		FnbCrawler fnbCrawler;
		
		@Autowired
		NedbankCrawler nedbankCrawler;
		
		@Autowired
		StandardCrawler standardCrawler;
		
		@Autowired
		BidvestCrawler bidvestCrawler;
		
		@Autowired
		Crawler crawler;
		
		@Autowired
		CurrencyValuesRepo currencyValuesRepo;
		
		@Autowired
		BankRepo bankRepo;
	
	@Scheduled(cron="0 05/2 08-18 ? * *")
	public void scheduleScraping()
	{
            crawler.setDate();
            
            try {
            	
                absaCrawler.getAbsaBank();
               
                nedbankCrawler.getNedBank();
                
                standardCrawler.getStandardBank();
                
                bidvestCrawler.getBidvestBank();
                
                fnbCrawler.getFNB();
                
            } catch (IOException | InterruptedException e) {
            	
            	e.printStackTrace();
            	
            	String stacktrace = ExceptionUtils.getStackTrace(e);
               
               //Handling Exception for absaCrawler
                while(stacktrace.contains("AbsaCrawler")) {
                	
	               	try {
							absaCrawler.getAbsaBank();
							nedbankCrawler.getNedBank();
		                    
		                    standardCrawler.getStandardBank();
		                    
		                    bidvestCrawler.getBidvestBank();
		                    
		                    fnbCrawler.getFNB();
		                    
		                    stacktrace = "";
		                    
						} catch (IOException | InterruptedException e1) {
							// TODO Auto-generated catch block
							
							e1.printStackTrace();
							
							stacktrace = "";
							
							stacktrace = ExceptionUtils.getStackTrace(e1);
							
						}
	               	
                  }
                
              //Handling Exception for nedbankCrawler
                 while(stacktrace.contains("NedbankCrawler")) {
                	
	               	try {
							
							nedbankCrawler.getNedBank();
		                    
		                    standardCrawler.getStandardBank();
		                    
		                    bidvestCrawler.getBidvestBank();
		                    
		                    fnbCrawler.getFNB();
		                    
		                    stacktrace = "";
		                    
						} catch (IOException | InterruptedException e2) {
							// TODO Auto-generated catch block
							
                            e2.printStackTrace();
							
							stacktrace = "";
							
							stacktrace = ExceptionUtils.getStackTrace(e2);
							
						}
	               	
                }
                 
               //Handling Exception for standardCrawler
                 while(stacktrace.contains("StandardCrawler")) {
                 	
		               	try {
		               		
		                    standardCrawler.getStandardBank();
		                    
		                    bidvestCrawler.getBidvestBank();
		                    
		                    fnbCrawler.getFNB();
		                    
		                    stacktrace = "";
			                    
						} catch (IOException | InterruptedException e3) {
							
	                        e3.printStackTrace();
							
							stacktrace = "";
							
							stacktrace = ExceptionUtils.getStackTrace(e3);
							
						}
		    
                 }
                 
               //Handling Exception for bidvestCrawler
                 while(stacktrace.contains("BidvestCrawler")) {
                  	
		               	try {
								
			                    bidvestCrawler.getBidvestBank();
			                    
			                    fnbCrawler.getFNB();
			                    
			                    stacktrace = "";
			                    
							} catch (IOException | InterruptedException e4) {
								
                            e4.printStackTrace();
							
							stacktrace = "";
							
							stacktrace = ExceptionUtils.getStackTrace(e4);
								
							}
		               	
                  }
                 
               //Handling Exception for fnbCrawler
                 while(stacktrace.contains("FnbCrawler") || stacktrace.contains(" org.openqa.selenium.SessionNotCreatedException")) {
                   	
		               	try {
			                    
			                    fnbCrawler.getFNB();
			                    
			                    stacktrace = "";
			                    
							} catch (IOException | InterruptedException e5) {
								
                            e5.printStackTrace();
							
							stacktrace = "";
							
							stacktrace = ExceptionUtils.getStackTrace(e5);
								
							}
		               	
                   }
  
           }
            
	}
	
	@Override
	public ArrayList<Object> getLatestData(){
		
		return currencyValuesRepo.findByB_Id();
		
	}
	
	@Override
	public Iterable<Object> getHistory() {
		
		return currencyValuesRepo.findByCv_Id();
	}
	
}

