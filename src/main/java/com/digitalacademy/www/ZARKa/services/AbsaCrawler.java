package com.digitalacademy.www.ZARKa.services;

import java.io.IOException;
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
public class AbsaCrawler extends Crawler{
	
	@Autowired
	Crawler crawler;
		
	@Autowired
	CurrencyValues currencyValues;
	
	//Get DataRepo object
	@Autowired
	BankRepo bankRepo;
	
	@Autowired
	CurrencyRepo currencyRepo;
	
	@Autowired
	CurrencyValuesRepo currencyValuesRepo;
	
	
	private String imagePath = "\"./assets/img/currency_images/";
	
	private String image[] = {"canada_usd.png","swiss.png","euro.png","british.png","yen.png","usd.png"};
	
//Scraping data from Absa Foreign exchange rates web site page
public  void getAbsaBank() throws IOException, InterruptedException {
	
	   
		Thread.sleep(500);
	   //Connecting to Absa webisite using the Jsoup Java Library
        Document dc = Jsoup.connect("https://www.absa.co.za/indices/exchange-rates/").timeout(6000).get();
		
        //try to connect an Wait for 3 seconds before accessing data
	    Thread.sleep(3000);
        
       //Getting the Specific Web contents on the Absa exchange rates Page.
		Elements body = dc.select("div.dataFeeds");
		
		for(Element step: body.select("tbody td")) {
			
			crawler.setAllList(step.text());
			
	    }

			
	for(Element step: body.select("tbody td")) {
	    	 
	    	 String temp=step.text();
	    	 
		    	 switch(temp) 
		    	 {
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
			    	 case "Canadian Dollar":
			    		 crawler.setC_name(temp);
			    		 break;
			    	 case "European Euro":
			    		 crawler.setC_name(temp);
			    		 break;
			    	 case "British Sterling":
			    		 crawler.setC_name(temp);
			    		 break;
			    	 case "Japanese Yen":
			    		 crawler.setC_name(temp);
			    		 break;
			    	 case "US Dollar":
			    		 crawler.setC_name(temp);
			    		 break;
			    	 case "Swiss Franc":
			    		crawler.setC_name(temp);
			    		 break;	
		    	 } //End of switch
		    	 
		       //Adding values to c_short and c_name
	    	   if(crawler.getC_short()!=null) {
	    		   crawler.setC_shortList(crawler.getC_short());
	    		   
	    		   
	    	   }
	    	   
	    	   if(crawler.getC_name()!=null)
	    	   {
	    		   crawler.setC_nameList(crawler.getC_name());
	    	   }
	    	   
	    	  // Getting Corresponding Buy_Values
	    	   if(crawler.isState1()){
	    		     
	    		   	 crawler.setBuyList(crawler.getAllList().get(crawler.getCountCAD()+5));  
	    	   }
	    	   if(crawler.isState2()){
	    		     
		    		 crawler.setBuyList(crawler.getAllList().get(crawler.getCountEUR()+5)); 
		    	  }
	    	   if(crawler.isState3()){
	    		     
		    		 crawler.setBuyList(crawler.getAllList().get(crawler.getCountGBP()+5));    
		    	  }
	    	   if(crawler.isState4()){
	    		     
		    		 crawler.setBuyList(crawler.getAllList().get(crawler.getCountJPY()+5));    
		    	  }
	    	   if(crawler.isState5()){
	    		     
		    		 crawler.setBuyList(crawler.getAllList().get(crawler.getCountUSD()+5));   
		    	  }
	    	   if(crawler.isState6()){
	    		     
		    		 crawler.setBuyList(crawler.getAllList().get(crawler.getCountCHF()+5));
		    	}
	    	   
		       //Getting Corresponding values for Sell_Values
	    	   if(crawler.isState1()){
	    		     
		    		crawler.setSellList(crawler.getAllList().get(crawler.getCountCAD()+6));
		    		crawler.setState1(false);
	    	  }
	    	   if(crawler.isState2()){
	    		   	 crawler.setSellList(crawler.getAllList().get(crawler.getCountEUR()+6)); 
		    		 crawler.setState2(false);   
		    	  }
	    	   if(crawler.isState3()){
	    		   	crawler.setSellList(crawler.getAllList().get(crawler.getCountGBP()+6));
		    		crawler.setState3(false);
		    		   
		    	  }
	    	   if(crawler.isState4()){
	    		     crawler.setSellList(crawler.getAllList().get(crawler.getCountJPY()+6));
		    		 crawler.setState4(false);   
		    	  }
	    	   if(crawler.isState5()){
	    		     crawler.setSellList(crawler.getAllList().get(crawler.getCountUSD()+6));
		    		 crawler.setState5(false);   
		    	  }
	    	   if(crawler.isState6()){
	    		     crawler.setSellList(crawler.getAllList().get(crawler.getCountCHF()+6));
		    		 crawler.setState6(false);  
		    	  }
	    	   
	    	 //Clearing the value of c_short and c_name
	    	   crawler.setC_short(null);
         	   crawler.setC_name(null);
	    	   
		} //end of for loop
	     
	    //Checking if there is data that already exists on the database tables
	    //To avoid duplicated data
	    Bank existBank = bankRepo.findById(1);
	    Currency existCurrency = currencyRepo.findById(1);
	    
	    //check if there is no data that exists on the table Bank, then insert bank data.
	    if(existBank==null) {
	    	
	    	for(int i=0; i<arrayOfBank.length; i++) {
		    	 
	    		bankRepo.save(new Bank(crawler.arrayOfBank[i],"path1",(byte)1));
		    	 
		     }
	    }
	    
	    //check if there is no data that exists on the table Currency then insert currency data.	
	    if(existCurrency==null) {	
	    	
	    	 for(int i=0; i<crawler.getC_shortList().size(); i++) {
		    	 
	    		currencyRepo.save(new Currency(crawler.getC_nameList().get(i),crawler.getC_shortList().get(i),imagePath+image[i],(byte)1));
		    	 
		     }
	    }
	    
		//Saving data to the CurrencyValues table.   
	    for(int j=1; j<=crawler.getBuyList().size(); j++) {
	    		
	    	if(j == 1 || j == 2)
	    	{
	    		currencyValuesRepo.save(new CurrencyValues(1, j, Double.parseDouble(crawler.getDecFormat().format((crawler.getBase()/ (Double.parseDouble(crawler.getBuyList().get(j-1)))))),
	    		Double.parseDouble(crawler.getDecFormat().format((crawler.getBase()/ Double.parseDouble(crawler.getSellList().get(j-1))))), new Timestamp(crawler.getDate().getTime()),(byte)1));
	    	}
	    	else
	    	{
	    		 currencyValuesRepo.save(new CurrencyValues(1, j, Double.parseDouble(crawler.getBuyList().get(j-1)), Double.parseDouble(crawler.getSellList().get(j-1)), new Timestamp(crawler.getDate().getTime()),(byte)1));
	    	}
	    		
	    }
		    
		  //Clearing Absa Bank Data on the Lists to store Data for Bidvest Bank
		    crawler.getC_nameList().clear();
			crawler.getC_shortList().clear();
		    crawler.getBuyList().clear();
		    crawler.getSellList().clear();
		    crawler.getAllList().clear();;
		    
	   } //end of getAbsaBank Method
	
} //end of the class

