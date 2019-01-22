package com.digitalacademy.www.ZARKa.interfaces;
import com.digitalacademy.www.ZARKa.entities.Settings;
import com.itextpdf.text.DocumentException;


public interface ReportInt {


	String currencyReport(Settings settings) throws DocumentException;

	String topThreeCurrenciesReport(Settings settings) throws DocumentException;
	
}
