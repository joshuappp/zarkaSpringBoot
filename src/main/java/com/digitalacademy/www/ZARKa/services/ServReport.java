package com.digitalacademy.www.ZARKa.services;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.digitalacademy.www.ZARKa.dao.BankRepo;
import com.digitalacademy.www.ZARKa.dao.CurrencyRepo;
import com.digitalacademy.www.ZARKa.dao.SettingsRepo;
import com.digitalacademy.www.ZARKa.entities.Settings;
import com.digitalacademy.www.ZARKa.interfaces.ReportInt;
import com.itextpdf.text.DocumentException;

@Service
public class ServReport implements ReportInt {
	
	@Autowired
	SettingsRepo settingsRepo;
	
	@Autowired
	CurrencyRepo currencyRepo;
	
	@Autowired
	BankRepo bankRepo;
	
	private int trackBank = 1;
	
	private int trackCurrency = 1;
	
	private String reportHeader;
	
	private final String currencyName[] = {"Canadian Dollar(CAD)","Swiss Franc(CHF)","European Euro(EUR)", "British Sterling(GBP)", "Japanese Yen(JPY)","US Dollar(USD)"};
	
	private final String bankName[] = {"Absa Group Limited","Nedbank","Standard Bank", "Bidvest Bank", "First National Bank"};
	
	private final String note = "\n*PLEASE NOTE: the values(Rates) on the report are provided based on daily calculated average.";
  

	@Override
	public String currencyReport(Settings settings) throws DocumentException {
	
		switch(settings.getBank())
		{
		case "ABSA":
			trackBank = 1;
			break;
		case "NEDBANK":
			trackBank = 2;
			break;
		case "STANDARD":
			trackBank = 3;
			break;
		case "BIDVEST":
			trackBank = 4;
			break;
		case "FNB":
			trackBank = 5;
			break;
		case "All":
			trackBank = 6;
			break;
		default:
			break;
		}
		
		switch(settings.getCurrency())
		{
		case "CAD":
			trackCurrency = 1;
			break;
		case "CHF":
			trackCurrency = 2;
			break;
		case "EUR":
			trackCurrency = 3;
			break;
		case "GBP":
			trackCurrency = 4;
			break;
		case "JPY":
			trackCurrency = 5;
			break;
		case "USD":
			trackCurrency = 6;
			break;
		default:
			break;
		}
		
		ReportPDF.initiatePDf();
		
		String report ="";
		
		if(trackBank == 6)
		{
			reportHeader = "Weekly Report For " + currencyName[trackCurrency-1] + " at the following banks: ";
			ReportPDF.addSubtitle(ReportPDF.getDocument(), reportHeader);
			
			
			reportHeader = "";
			reportHeader = reportHeader + bankName[0] + ", " + bankName[1]+ ", " + bankName[2]+ ", " + bankName[3]+ " and " + bankName[4] + ".";
			ReportPDF.addContent(ReportPDF.getDocument(), reportHeader, ReportPDF.getAltFont());
		}
		else
		{
			reportHeader = "";
			reportHeader = "Weekly Report For " + currencyName[trackCurrency-1] + " at " + bankName[trackBank-1];
			ReportPDF.addContent(ReportPDF.getDocument(), reportHeader, ReportPDF.getAltFont());
		}
		
		report = "\n";
		ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
		
		if(!settings.getBank().equals("All"))
		{
			switch(settings.getSellOrBuy())
			{
			case "Both":
				
				ArrayList<Object> dataAll = settingsRepo.getWeeklyReportAll(trackBank, trackCurrency);
				
				report = "";
				
				report = report + "-------------------------------------------------------------\n";
				report = report + "DATE                " + "BANK-BUY          " + "BANK-SELL";
				report = report + "\n-------------------------------------------------------------\n";
				ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
				
				for(Object reportData: dataAll)
				{
					report = "";
					
					String allData = reportData.toString();
					
					report = report + allData.substring(0,allData.indexOf("|")) + "            ";
					report = report + allData.substring(allData.indexOf("|")+1, allData.indexOf(":")) + "                   ";
					report = report + allData.substring(allData.indexOf(":")+1, allData.length());
					ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
					
					allData = null;
				}
				
				ReportPDF.addContent(ReportPDF.getDocument(), note, ReportPDF.getRedFont());
				ReportPDF.closeDocument();
				break;
				
			case "Buy":
				
				ArrayList<Object> dataBuy = settingsRepo.getWeeklyReportBuy(trackBank, trackCurrency);
				
				report = "";
				
				report = report + "-------------------------------------------------------------\n";
				report = report + "DATE                                         " + "BANK-BUY";
				report = report + "\n-------------------------------------------------------------\n";
				ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
				
				
				for(Object reportData: dataBuy)
				{
					report = "";
					
					String allData = reportData.toString();
					
					report = report + allData.substring(0,allData.indexOf("|")) + "                                     ";
					report = report + allData.substring(allData.indexOf("|")+1, allData.length());
					ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
					
					allData = null;
				}
				
				ReportPDF.addContent(ReportPDF.getDocument(), note, ReportPDF.getRedFont());
				ReportPDF.closeDocument();
				break;
			case "Sell":
				
				ArrayList<Object> dataSell = settingsRepo.getWeeklyReportSell(trackBank, trackCurrency);
				
				report = "";
				
				report = report + "-------------------------------------------------------------\n";
				report = report + "DATE                                         " + "BANK-SELL";
				report = report + "\n-------------------------------------------------------------\n";
				ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
				
				for(Object reportData: dataSell)
				{
					report = "";
					
					String allData = reportData.toString();
					
					report = report + allData.substring(0,allData.indexOf("|")) + "                                     ";
					report = report + allData.substring(allData.indexOf("|")+1,allData.length());
					ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
					
					allData = null;
				}
				
				ReportPDF.addContent(ReportPDF.getDocument(), note, ReportPDF.getRedFont());
				ReportPDF.closeDocument();
				break;
			default:
				
				break;
			}
		}
		else
		{
			switch (settings.getSellOrBuy()) 
			{
				case "Buy":
				
					report = "";
					
					ArrayList<Object> dataBuy = settingsRepo.getWRBuyFromAllBanks(trackCurrency);
					
					report = report + "-------------------------------------------------------------------------\n";
					report = report + "DATE                " + "BANK-BUY             " + "BANK";
					report = report + "\n-------------------------------------------------------------------------\n";
					ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
					
					int breakData = 0;
					
					for(Object reportData: dataBuy)
					{
						report = "";
						
						String allData = reportData.toString();
						
						report = report + allData.substring(0, allData.indexOf("|"))+ "            ";
						report = report + allData.substring(allData.indexOf("|")+1, allData.indexOf("/"))+ "                   ";
						report = report + allData.substring(allData.indexOf("/")+1, allData.length());
						ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
						
						if(breakData == 6 || breakData == 13 || breakData == 20 || breakData == 27)
						{
							report = "" ;
							report = report + "-------------------------------------------------------------------------\n";
							report = report + "DATE                " + "BANK-BUY             " + "BANK";
							report = report + "\n-------------------------------------------------------------------------\n";
							ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
						}
						
						allData = null;
						breakData++;
						
					}
	
					ReportPDF.addContent(ReportPDF.getDocument(), note, ReportPDF.getRedFont());
					ReportPDF.closeDocument();
					break;
				case "Sell":
					
					report = "";
					
					ArrayList<Object> dataSell = settingsRepo.getWRSellFromAllBanks(trackCurrency);
					
					report = report + "-------------------------------------------------------------------------\n";
					report = report + "DATE                " + "BANK-SELL             " + "BANK";
					report = report + "\n-------------------------------------------------------------------------\n";
					ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
					
					breakData = 0;
					
					for(Object reportData: dataSell)
					{
						report = "";
						
						String allData = reportData.toString();
						
						report = report + allData.substring(0, allData.indexOf("|"))+ "            ";
						report = report + allData.substring(allData.indexOf("|")+1, allData.indexOf("/"))+ "                   ";
						report = report + allData.substring(allData.indexOf("/")+1, allData.length());
						ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
						
						if(breakData == 6 || breakData == 13 || breakData == 20 || breakData == 27)
						{
							report = "" ;
							report = report + "-------------------------------------------------------------------------\n";
							report = report + "DATE                " + "BANK-BUY             " + "BANK";
							report = report + "\n-------------------------------------------------------------------------\n";
							ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
						}
						
					
						allData = null;
						breakData++;
					}
					
					ReportPDF.addContent(ReportPDF.getDocument(), note, ReportPDF.getRedFont());
					ReportPDF.closeDocument();
					break;
				case "Both":
					
					report = "";
					
					ArrayList<Object> dataSellBuy = settingsRepo.getWRBuySellFromAllBanks(trackCurrency);
					
					report = report + "-------------------------------------------------------------------------------------------------\n";
					report = report + "DATE               " + "  BANK-SELL          " + "BANK-BUY          " + " BANK";
					report = report + "\n-------------------------------------------------------------------------------------------------\n";
					ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
					
					breakData = 0;
					
					for(Object reportData: dataSellBuy)
					{
						report = "";
						
						String allData = reportData.toString();
						
						report = report + allData.substring(0, allData.indexOf("|")) + "              ";
						report = report + allData.substring(allData.indexOf("|")+1, allData.indexOf(":"))+ "                   ";
						report = report + allData.substring(allData.indexOf(":")+1, allData.indexOf("/"))+ "                 ";
						report = report + allData.substring(allData.indexOf("/")+1, allData.length());
						ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
						
						if(breakData == 6 || breakData == 13 || breakData == 20 || breakData == 27)
						{
							report = "";
							report = report + "-------------------------------------------------------------------------------------------------\n";
							report = report + "DATE               " + "  BANK-SELL          " + "BANK-BUY          " + " BANK";
							report = report + "\n-------------------------------------------------------------------------------------------------\n";
							ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
						}
						
					
						allData = null;
						breakData++;
					}
					ReportPDF.addContent(ReportPDF.getDocument(), note, ReportPDF.getRedFont());
					ReportPDF.closeDocument();
					break;
				default:
					break;
			}
		}
		
		
		report = report + "\n";
		
		
		return report;
	}

	@Override
	public String topThreeCurrenciesReport(Settings settings) throws DocumentException 
	{
		ReportPDF.initiatePDf();
		
		String report = ""; 
		
		switch (settings.getSellOrBuy()) {
		
		case "Both":
			
			ArrayList<Object> dataAll = settingsRepo.getWeeklyReportAllBC();

			report = ""; 
			
			reportHeader = "";
			reportHeader = "Weekly Report For All Currencies From the following banks(Buying and Selling Rates):";
			ReportPDF.addSubtitle(ReportPDF.getDocument(), reportHeader);
			
			reportHeader = "";
			reportHeader = reportHeader + bankName[0] + ", " + bankName[1]+ ", " + bankName[2]+ ", " + bankName[3]+ " and " + bankName[4] + ".";
			ReportPDF.addContent(ReportPDF.getDocument(), reportHeader, ReportPDF.getAltFont());
			reportHeader = "\n";
			
			ReportPDF.addContent(ReportPDF.getDocument(), reportHeader, ReportPDF.getNormalFont());
			
			report = report + "---------------------------------------------------------------------------------------------------------------------\n";
			report = report + "DATE               " + "CURRENCY          BANK-BUY          BANK-SELL          BANK";
			report = report + "\n---------------------------------------------------------------------------------------------------------------------\n";
			ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
			
			int breakData = 0;
			
			for(Object reportData: dataAll)
			{
				report = "" ;
				
				String allData = reportData.toString();
				
				report = report + allData.substring(0, allData.indexOf("|")) + "               ";
				report = report + allData.substring(allData.indexOf("|")+ 1, allData.indexOf(":"))   + "                     ";
				report = report + allData.substring(allData.indexOf(":")+ 1, allData.indexOf(";")) + "                       ";
				report = report + allData.substring(allData.indexOf(";")+ 1, allData.indexOf("/")) + "                  ";
				report = report + allData.substring(allData.indexOf("/")+ 1, allData.length());
				ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
				
				if(breakData == 6 || breakData == 13 || breakData == 20 || breakData == 27 ||breakData == 34 ||breakData == 41 || breakData == 48 ||breakData == 55 ||breakData == 62 || breakData == 69 || breakData == 76 || breakData == 83 || breakData == 90 || breakData == 97)
				{
					report = "" ;
					report = report + "\n";
					report = report + "---------------------------------------------------------------------------------------------------------------------\n";
					report = report + "DATE               " + "CURRENCY          BANK-BUY          BANK-SELL          BANK";
					report = report + "\n---------------------------------------------------------------------------------------------------------------------\n";
					ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
				}
		
				
				allData = null;
				breakData++;
			}
			
			ReportPDF.addContent(ReportPDF.getDocument(), note, ReportPDF.getRedFont());
			ReportPDF.closeDocument();
			break;
		case "Sell":
			
			ReportPDF.initiatePDf();
			
			ArrayList<Object> dataSell = settingsRepo.getWeeklyReportAllBC();

			report = ""; 
			
			reportHeader = "";
			reportHeader = "\n";
			reportHeader = "Weekly Report For All Currencies From the following banks(Selling Rates): ";
			ReportPDF.addSubtitle(ReportPDF.getDocument(), reportHeader);
			
			reportHeader = "";
			reportHeader = reportHeader + bankName[0] + ", " + bankName[1]+ ", " + bankName[2]+ ", " + bankName[3]+ " and " + bankName[4] + ".";
			ReportPDF.addContent(ReportPDF.getDocument(), reportHeader, ReportPDF.getAltFont());
			reportHeader = "\n";
			
			ReportPDF.addContent(ReportPDF.getDocument(), reportHeader, ReportPDF.getNormalFont());
			
			report = report + "---------------------------------------------------------------------------------------------------\n";
			report = report + "DATE               " + "CURRENCY          BANK-SELL          BANK";
			report = report + "\n---------------------------------------------------------------------------------------------------\n";
			ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
			
			breakData = 0;
			
			for(Object reportData: dataSell)
			{
				report = "" ;
				
				String allData = reportData.toString();
				
				report = report + allData.substring(0, allData.indexOf("|")) + "               ";
				report = report + allData.substring(allData.indexOf("|")+ 1, allData.indexOf(":"))  + "                     ";
				report = report + allData.substring(allData.indexOf(";")+ 1, allData.indexOf("/")) + "                 ";
				report = report + allData.substring(allData.indexOf("/")+ 1, allData.length());
				ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
				
				
				if(breakData == 6 || breakData == 13 || breakData == 20 || breakData == 27 ||breakData == 34 ||breakData == 41 || breakData == 48 ||breakData == 55 ||breakData == 62 || breakData == 69 || breakData == 76 || breakData == 83 || breakData == 90 || breakData == 97)
				{
					report = "" ;
					report = report + "\n";
					report = report + "---------------------------------------------------------------------------------------------------\n";
					report = report + "DATE               " + "CURRENCY          BANK-SELL          BANK";
					report = report + "\n---------------------------------------------------------------------------------------------------\n";
					ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
				}
		
				allData = null;
				breakData++;
			}
			
			ReportPDF.addContent(ReportPDF.getDocument(), note, ReportPDF.getRedFont());
			ReportPDF.closeDocument();
			
			break;
		case "Buy": 
			
			ReportPDF.initiatePDf();
		
			ArrayList<Object> dataBuy = settingsRepo.getWeeklyReportAllBC();

			report = ""; 
			
			reportHeader = "";
			reportHeader = "\n";
			reportHeader = "Weekly Report For All Currencies From the following banks(Buying Rates):";
			ReportPDF.addSubtitle(ReportPDF.getDocument(), reportHeader);
		
			reportHeader = "";
			reportHeader = reportHeader + bankName[0] + ", " + bankName[1]+ ", " + bankName[2]+ ", " + bankName[3]+ " and " + bankName[4] + ".";
			ReportPDF.addContent(ReportPDF.getDocument(), reportHeader, ReportPDF.getAltFont());
			reportHeader = "\n";
			
			ReportPDF.addContent(ReportPDF.getDocument(), reportHeader, ReportPDF.getNormalFont());
			
			report = report + "---------------------------------------------------------------------------------------------------\n";
			report = report + "DATE               " + "CURRENCY          BANK-BUY          BANK";
			report = report + "\n---------------------------------------------------------------------------------------------------\n";
			ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
		
			
			breakData = 0;
			
			for(Object reportData: dataBuy)
			{
				
				report = "" ;
				
				
				String allData = reportData.toString();
				
				report = report + allData.substring(0, allData.indexOf("|")) + "               ";
				report = report + allData.substring(allData.indexOf("|")+ 1, allData.indexOf(":")) + "                     ";
				report = report + allData.substring(allData.indexOf(":")+ 1, allData.indexOf(";")) + "                 ";
				report = report + allData.substring(allData.indexOf("/")+ 1, allData.length());
				
				ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
				
				

				if(breakData == 6 || breakData == 13 || breakData == 20 || breakData == 27 ||breakData == 34 ||breakData == 41 || breakData == 48 ||breakData == 55 ||breakData == 62 || breakData == 69 || breakData == 76 || breakData == 83 || breakData == 90 || breakData == 97)
				{
					report = "" ;
					report = report + "\n";
					report = report + "---------------------------------------------------------------------------------------------------\n";
					report = report + "DATE               " + "CURRENCY          BANK-BUY          BANK";
					report = report + "\n---------------------------------------------------------------------------------------------------\n";
					ReportPDF.addContent(ReportPDF.getDocument(), report, ReportPDF.getNormalFont());
				}
				
				
				allData = null;
				breakData++;
			}
			
			ReportPDF.addContent(ReportPDF.getDocument(), note, ReportPDF.getRedFont());
			ReportPDF.closeDocument();
			
			break;
		default:
			break;
		}
		
		return report;
	}

	
	
	public String currentTimeFormatter()
	{
		DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern("HH:mm");
	    LocalDateTime now = LocalDateTime.now();
	    
	    String currentTime = dateTimeformatter.format(now).toString();
	    
	    return currentTime;
	}
	
	public String currentDay()
	{
		Date today = new Date();
		 
        SimpleDateFormat dateFormatter = new SimpleDateFormat();
 
        dateFormatter = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
        
		
		return dateFormatter.format(today).toString();
	}

}
