package com.digitalacademy.www.ZARKa.services;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;

import com.digitalacademy.www.ZARKa.dao.BankRepo;
import com.digitalacademy.www.ZARKa.dao.CurrencyRepo;
import com.digitalacademy.www.ZARKa.dao.CurrencyValuesRepo;
import com.digitalacademy.www.ZARKa.entities.Bank;
import com.digitalacademy.www.ZARKa.entities.Currency;
import com.digitalacademy.www.ZARKa.entities.CurrencyValues;

@Component
public class StandardCrawler extends Crawler{
	
	
		@Autowired
		Crawler crawler;
	
		//Get bank object
		@Autowired
		Bank bank;
		@Autowired
		Currency currency;
		@Autowired
		CurrencyValues currencyValues;
		
		//Get DataRepo object
		@Autowired
		BankRepo bankRepo;
		
		@Autowired
		CurrencyRepo currencyRepo;
		
		@Autowired
		CurrencyValuesRepo currencyValuesRepo;
		
		private int count = 1;
		private int countTwo = 1;
	
		
		private int existArray[] = {65,73,81,89,97,105,113,121,129,137,145,153,161,169,177,185,193,201,209,217,225,233,241,249,257,265,273,281,289,
			297,305,313,321,329,337,345,353,361,369,377,385,393,401,409,417,425,433,441,449,457,465,473,481,489,497,505,513};
	
		private List<int[]> existList =Arrays.asList(existArray);
		
//Scraping data from Standard bank Foreign exchange rates web site page	
@SuppressWarnings("unlikely-arg-type")
public synchronized void  getStandardBank() throws IOException, InterruptedException {
		
	   Thread.sleep(500);
	   //Connecting to Standard bank webisite using the Jsoup Java Library
       Document dc = Jsoup.connect("http://ws15.standardbank.co.za/finSnapShot/GetforexServlet").timeout(6000).get();
		
       //try to connect an Wait for 3 seconds before accessing data
	   Thread.sleep(3000);
        
		Elements body = dc.select("tbody");
		
		for(Element step: body.select("td")) {
			
			Double value = (double) countTwo+7;
			
			if(countTwo>26 && !existList.contains(value)  ) {
				
				if(countTwo != 51) {
					crawler.getAllList().add(step.text());
				}	
			  }
			countTwo++;
		}
		
		
		countTwo=1;
		for(Element step: body.select("td")) {
			
			Double value = (double) countTwo+7;
			
			if(countTwo>26 && !existList.contains(value)) {
				
					if(count != 51) {
						
						 String temp=step.text();
				    	 
				    	 switch(temp) {
				    	 
				    	 case "CAD":
				    		 crawler.setC_short(temp);
				    		 crawler.setCountCAD(crawler.getAllList().indexOf("CAD"));
				    		 crawler.setState1(true);
				    		 break;
				    	 case "EUR":
				    		 crawler.setC_short(temp);
				    		 crawler.setCountEUR(crawler.getAllList().indexOf("EUR"));
				    		 crawler.setState2(true);
				    		 break;
				    	 case "GBP":
				    		 crawler.setC_short(temp);
				    		 crawler.setCountGBP(crawler.getAllList().indexOf("GBP"));
				    		 crawler.setState3(true);
				    		 break;
				    	 case "JPY":
				    		 crawler.setC_short(temp);
				    		 crawler.setCountJPY(crawler.getAllList().indexOf("JPY"));
				    		 crawler.setState4(true);
				    		 break;
				    	 case "USD":
				    		 crawler.setC_short(temp);
				    		 crawler.setCountUSD(crawler.getAllList().indexOf("USD"));
				    		 crawler.setState5(true);
				    		 break;
				    	 case "CHF":
				    		 crawler.setC_short(temp);
				    		 crawler.setCountCHF(crawler.getAllList().indexOf("CHF"));
				    		 crawler.setState6(true);
				    		 break;
				    		 
				    	 //Getting Names	  
				    	 case "CANADIAN DOLLAR":
				    		 crawler.setC_name(temp);
				    		 break;
				    	 case "EURO":
				    		 crawler.setC_name(temp);
				    		 break;
				    	 case "BRITSH POUND":
				    		 crawler.setC_name(temp);
				    		 break;
				    	 case "JAPANESE YEN":
				    		 crawler.setC_name(temp);
				    		 break;
				    	 case "UNITED STATES DOL":
				    		 crawler.setC_name(temp);
				    		 break;
				    	 case "SWISS FRANC":
				    		 crawler.setC_name(temp);
				    		 break;
				    		 
				    	 }
				    	 
				       //Adding values to c_short and c_name
			    	   if(crawler.getC_short()!=null) {
			    		   crawler.setC_shortList(crawler.getC_short());
			    	   }
			    	   
			    	   if(crawler.getC_name()!=null)
			    	   {
			    		   crawler.setC_nameList(crawler.getC_name());
			    	   }
			    	   
			    	  // Getting Corresponding Buy_Values
			    	   if(crawler.isState1())
			    	   {
			    		   crawler.setBuyList(crawler.getAllList().get(crawler.getCountCAD()+3));
			    		   
			    	   }
			    	   if(crawler.isState2())
			    	   {
				    		
				    		crawler.setBuyList(crawler.getAllList().get(crawler.getCountEUR()+3));
				    		 
				    	  }
			    	   if(crawler.isState3())
			    	   {
			    		 
			    		   crawler.setBuyList(crawler.getAllList().get(crawler.getCountGBP()+3));
				    		 
				    	  }
			    	   if(crawler.isState4())
			    	   { 
				    		crawler.setBuyList(crawler.getAllList().get(crawler.getCountJPY()+3));
				    		   
				    	  }
			    	   if(crawler.isState5())
			    	   {
				 
				    		crawler.setBuyList(crawler.getAllList().get(crawler.getCountUSD()+3));
				    		   
				    	  }
			    	   if(crawler.isState6())
			    	   {
				    		 crawler.setBuyList(crawler.getAllList().get(crawler.getCountCHF()+3));
				    		 
				    	}
			    	   
			   
				       //Getting Corresponding values for Sell_Values
			    	   if(crawler.isState1()){
			    		     
			    		   crawler.setSellList(crawler.getAllList().get(crawler.getCountCAD()+5));
			    		   crawler.setState1(false);
			    		   
			    	  }
			    	   if(crawler.isState2()){
			    		     
				    		crawler.setSellList(crawler.getAllList().get(crawler.getCountEUR()+5));
				    		 crawler.setState2(false);
				    		   
				    	  }
			    	   if(crawler.isState3()){
			    		     
				    		 
				    		 crawler.setSellList(crawler.getAllList().get(crawler.getCountGBP()+5));
				    		 crawler.setState3(false);
				    		   
				    	  }
			    	   if(crawler.isState4()){
			    		     
				    		  
				    		 crawler.setSellList(crawler.getAllList().get(crawler.getCountJPY()+5));
				    		 crawler.setState4(false);
				    		   
				    	  }
			    	   if(crawler.isState5()){
			    		     
				    		 
				    		 crawler.setSellList(crawler.getAllList().get(crawler.getCountUSD()+5));
				    		 crawler.setState5(false);
				    		   
				    	  }
			    	   if(crawler.isState6()){
			    		     
				    		 
				    		 crawler.setSellList(crawler.getAllList().get(crawler.getCountCHF()+5));
				    		 crawler.setState6(false);
				    		   
				    	  }
			    	   
			    	 //Clearing the value of c_short and c_name
			    	  
			    	  crawler.setC_short(null);
		         	  crawler.setC_name(null);
			   	   	
					}
								
			}
			
			count++;
			
			if(count == 435) {
				break;
			}
			
			countTwo++;
		}
   		
   		crawler.setSorted_c_shortList("CAD");  		
   		crawler.setSorted_c_shortList("CHF");  		
   		crawler.setSorted_c_shortList("EUR");  		
   		crawler.setSorted_c_shortList("GBP");   		
   		crawler.setSorted_c_shortList("JPY");
   		crawler.setSorted_c_shortList("USD");
   	
   		crawler.setSorted_c_nameList(crawler.getC_nameList().get(3));
   		crawler.setSorted_c_nameList(crawler.getC_nameList().get(4));
   		crawler.setSorted_c_nameList(crawler.getC_nameList().get(0));
   		crawler.setSorted_c_nameList(crawler.getC_nameList().get(1));
   		crawler.setSorted_c_nameList(crawler.getC_nameList().get(5));
   		crawler.setSorted_c_nameList(crawler.getC_nameList().get(2));
   		
   		crawler.setSorted_buyList(crawler.getBuyList().get(3));
   		crawler.setSorted_buyList(crawler.getBuyList().get(4));
   		crawler.setSorted_buyList(crawler.getBuyList().get(1));
   		crawler.setSorted_buyList(crawler.getBuyList().get(0));
   		crawler.setSorted_buyList(crawler.getBuyList().get(5));
   		crawler.setSorted_buyList(crawler.getBuyList().get(2));
   		
   		crawler.setSorted_sellList(crawler.getSellList().get(3));
   		crawler.setSorted_sellList(crawler.getSellList().get(4));
   		crawler.setSorted_sellList(crawler.getSellList().get(1));
   		crawler.setSorted_sellList(crawler.getSellList().get(0));
   		crawler.setSorted_sellList(crawler.getSellList().get(5));
   		crawler.setSorted_sellList(crawler.getSellList().get(2));
	    
   	
   	    //[=========Storing Standard Bank Currency values to the Database===========]
		for(int j=1; j<=crawler.getBuyList().size(); j++) {
			
			if(j == 1 || j == 2)
	    	{
	    		currencyValuesRepo.save(new CurrencyValues(3, j, Double.parseDouble(crawler.getDecFormat().format((crawler.getBase()/ (Double.parseDouble(crawler.getSorted_buyList().get(j-1)))))),
	    		Double.parseDouble(crawler.getDecFormat().format((crawler.getBase()/ Double.parseDouble(crawler.getSorted_sellList().get(j-1))))), new Timestamp(crawler.getDate().getTime()),(byte)1));
	    	}
	    	else
	    	{
	    		 currencyValuesRepo.save(new CurrencyValues(3, j, Double.parseDouble(crawler.getSorted_buyList().get(j-1)), Double.parseDouble(crawler.getSorted_sellList().get(j-1)), new Timestamp(crawler.getDate().getTime()),(byte)1));
	    	}
				
		} 
		
		//Clearing Bidvest Bank Data in the Lists, to store Data for Another Bank
    	crawler.getC_nameList().clear();
		crawler.getC_shortList().clear();
	    crawler.getBuyList().clear();
	    crawler.getSellList().clear();
	    crawler.getAllList().clear();;
	    
	  //Clearing Sorted Bidvest Bank Data in the Lists, to store Data for Another Bank
	    crawler.getSorted_c_nameList().clear();
		crawler.getSorted_c_shortList().clear();
		crawler.getSorted_buyList().clear();
		crawler.getSorted_sellList().clear();
		   
	} //end of getStandardBank() Method
	
}//end of class
