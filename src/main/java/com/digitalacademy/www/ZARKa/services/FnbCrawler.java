package com.digitalacademy.www.ZARKa.services;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.digitalacademy.www.ZARKa.dao.CurrencyValuesRepo;
import com.digitalacademy.www.ZARKa.entities.CurrencyValues;

@Component
public class FnbCrawler extends Crawler{
	
	@Autowired
	Crawler crawler;
	
	@Autowired
	CurrencyValuesRepo currencyValuesRepo;
	
	DecimalFormat dFormat = new DecimalFormat("#.0000");
	
//Scraping data from FNB Foreign exchange rates web site page
public synchronized void getFNB() throws InterruptedException, IOException {
			
	        	
			System.setProperty("webdriver.chrome.driver", "/Users/academy_intern/Downloads/realBackEnd/ZARKa/src/main/resources/chromedriver");
			
			WebDriver driver = new ChromeDriver();
			
			
			//Connecting to FNB bank webisite using the Jsoup Java Library
			Thread.sleep(500);
			driver.get("https://www.fnb.co.za/rates/ForeignExchangeRates.html");
			
			//Wait 3 seconds before executing the next line of code(driver.findElement)
			driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
			
			
			//driver.findElement(By.linkText("View rates")).click();
			driver.findElement(By.xpath("//*[@id=\"slide1\"]/div/div/div/div[3]/div/div/div/div/div/ul/li[1]/a")).click();
			
			driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
			WebElement element = driver.findElement(By.xpath("//*[@id=\"forexRatesTable\"]/table[1]"));
			
			
			driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
			String data = element.getText();
			
			String theData =  data.replaceAll("\\s+","");
			 
				
				crawler.setC_nameList("Euro");
				crawler.setC_shortList("EUR");
				crawler.setSellList(theData.substring(theData.indexOf("YEUR")+4, theData.indexOf("YEUR")+11));
				crawler.setBuyList(theData.substring(theData.indexOf("YEUR")+18, theData.indexOf("YEUR")+25));
				 
				 
				crawler.setC_nameList("British Sterling");
				crawler.setC_shortList("GBP");
				crawler.setSellList(theData.substring(theData.indexOf("GBP")+3, theData.indexOf("GBP")+10));
				crawler.setBuyList(theData.substring(theData.indexOf("GBP")+17, theData.indexOf("GBP")+24));
				 
				
				crawler.setC_nameList("US Dollar");
				crawler.setC_shortList("USD");
				crawler.setSellList(theData.substring(theData.indexOf("SUSD")+4, theData.indexOf("SUSD")+11));
				crawler.setBuyList(theData.substring(theData.indexOf("SUSD")+18, theData.indexOf("SUSD")+25));
				 
			  

				WebElement element2 = driver.findElement(By.xpath("//*[@id=\"forexRatesTable\"]/table[2]"));
				
				String data2 = element2.getText();
				
					
				String theData2 = data2.replaceAll("\\s+","");
				 
				crawler.setC_nameList("Canadian Dollar");
				crawler.setC_shortList("CAD");
				crawler.setSellList(theData2.substring(theData2.indexOf("CAD")+3, theData2.indexOf("CAD")+9));
				crawler.setBuyList(theData2.substring(theData2.indexOf("CAD")+15, theData2.indexOf("CAD")+21));
				 
				crawler.setC_nameList("Swiss Franc");
				crawler.setC_shortList("CHF");
				crawler.setSellList(theData2.substring(theData2.indexOf("CHF")+3, theData2.indexOf("CHF")+9));
				crawler.setBuyList(theData2.substring(theData2.indexOf("CHF")+15, theData2.indexOf("CHF")+21));
				 
				crawler.setC_nameList("Japanese Yen");
				crawler.setC_shortList("JPY");
				crawler.setSellList(theData2.substring(theData2.indexOf("JPY")+3, theData2.indexOf("JPY")+9));
				crawler.setBuyList(theData2.substring(theData2.indexOf("JPY")+15, theData2.indexOf("JPY")+21));
				 
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
		   		crawler.setSorted_buyList(crawler.getBuyList().get(0));
		   		crawler.setSorted_buyList(crawler.getBuyList().get(1));
		   		crawler.setSorted_buyList(crawler.getBuyList().get(5));
		   		crawler.setSorted_buyList(crawler.getBuyList().get(2));
		   		
		   		crawler.setSorted_sellList(crawler.getSellList().get(3));
		   		crawler.setSorted_sellList(crawler.getSellList().get(4));
		   		crawler.setSorted_sellList(crawler.getSellList().get(0));
		   		crawler.setSorted_sellList(crawler.getSellList().get(1));
		   		crawler.setSorted_sellList(crawler.getSellList().get(5));
		   		crawler.setSorted_sellList(crawler.getSellList().get(2));
		   		
		   	
		   		//[=========Storing FNB Bank Currency values to the Database===========]
		   		for(int j=1; j<=crawler.getBuyList().size(); j++) {
		    		
		   			if(j == 1 || j == 2)
			    	{
			    		currencyValuesRepo.save(new CurrencyValues(5, j, Double.parseDouble(crawler.getDecFormat().format((crawler.getBase()/ (Double.parseDouble(crawler.getSorted_buyList().get(j-1)))))),
			    		Double.parseDouble(crawler.getDecFormat().format((crawler.getBase()/ Double.parseDouble(crawler.getSorted_sellList().get(j-1))))), new Timestamp(crawler.getDate().getTime()),(byte)1));
			    	}
			    	else
			    	{
			    		 currencyValuesRepo.save(new CurrencyValues(5, j, Double.parseDouble(crawler.getSorted_buyList().get(j-1)), Double.parseDouble(crawler.getSorted_sellList().get(j-1)), new Timestamp(crawler.getDate().getTime()),(byte)1));
			    	}
				    		
				 } 
		   		
		   		//Clearing FNB Bank Data in the Lists, to store Data for Another Bank
		    	crawler.getC_nameList().clear();
				crawler.getC_shortList().clear();
			    crawler.getBuyList().clear();
			    crawler.getSellList().clear();
			    crawler.getAllList().clear();
			    
			    //Clearing Sorted FNB Bank Data in the Lists, to store Data for Another Bank
			    crawler.getSorted_c_nameList().clear();
				crawler.getSorted_c_shortList().clear();
				crawler.getSorted_buyList().clear();
				crawler.getSorted_sellList().clear();
		   		
			//Wait 3 Seconds before closing and killing the driver
		   	Thread.sleep(3000);
		   	driver.close();
		   	driver.quit();
		   		  
			} //end of getFNB() Method.
			
} //end of class

