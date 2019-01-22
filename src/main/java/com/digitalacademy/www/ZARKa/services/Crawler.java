package com.digitalacademy.www.ZARKa.services;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Crawler {
	
	private static final int BASE_VALUE = 1;
	
	DecimalFormat decFormat = new DecimalFormat("#.0000");
	

	private String c_short=null,c_name=null;
		
	private int countCAD=0,countEUR=0,countGBP=0,countJPY=0,countUSD=0,countCHF=0;
	
	
	private boolean state1 = false,state2 = false,state3 = false,state4 = false,state5 = false,state6 = false;
	
	//ArrayLists that will hold bank data(Currency Code, name, buying and selling value)
	private ArrayList<String> c_shortList =new  ArrayList<String>();
	private ArrayList<String> c_nameList =new  ArrayList<String>();
	private ArrayList<String> buyList =new  ArrayList<String>();
	private ArrayList<String> sellList =new  ArrayList<String>();
	private ArrayList<String> allList =new  ArrayList<String>();
	
	
	////ArrayLists that will hold SORETED bank data(Currency Code, name, buying and selling value)
	private ArrayList<String> sorted_c_shortList =new  ArrayList<String>();
	private ArrayList<String> sorted_c_nameList =new  ArrayList<String>();
	private ArrayList<String> sorted_buyList =new  ArrayList<String>();
	private ArrayList<String> sorted_sellList =new  ArrayList<String>();
	
	//An Array holding the Banks that will be used on our Application.
	protected final String arrayOfBank[] = {"ABSA","NEDBANK","STANDARD BANK","BIDVEST BANK","FNB"};
	
    private Date date = new Date();
    

	public String getC_short() {
		return c_short;
	}

	public void setC_short(String c_short) {
		this.c_short = c_short;
	}

	public String getC_name() {
		return c_name;
	}

	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	public int getCountCAD() {
		return countCAD;
	}

	public void setCountCAD(int countCAD) {
		this.countCAD = countCAD;
	}

	public int getCountEUR() {
		return countEUR;
	}

	public void setCountEUR(int countEUR) {
		this.countEUR = countEUR;
	}

	public int getCountGBP() {
		return countGBP;
	}

	public void setCountGBP(int countGBP) {
		this.countGBP = countGBP;
	}

	public int getCountJPY() {
		return countJPY;
	}

	public void setCountJPY(int countJPY) {
		this.countJPY = countJPY;
	}

	public int getCountUSD() {
		return countUSD;
	}

	public void setCountUSD(int countUSD) {
		this.countUSD = countUSD;
	}

	public int getCountCHF() {
		return countCHF;
	}

	public void setCountCHF(int countCHF) {
		this.countCHF = countCHF;
	}

	public boolean isState1() {
		return state1;
	}

	public void setState1(boolean count1) {
		this.state1 = count1;
	}

	public boolean isState2() {
		return state2;
	}

	public void setState2(boolean count2) {
		this.state2 = count2;
	}

	public boolean isState3() {
		return state3;
	}

	public void setState3(boolean count3) {
		this.state3 = count3;
	}

	public boolean isState4() {
		return state4;
	}

	public void setState4(boolean count4) {
		this.state4 = count4;
	}

	public boolean isState5() {
		return state5;
	}

	public void setState5(boolean count5) {
		this.state5 = count5;
	}

	public boolean isState6() {
		return state6;
	}

	public void setState6(boolean count6) {
		this.state6 = count6;
	}
	
	public ArrayList<String> getSorted_c_shortList() {
		return sorted_c_shortList;
	}

	public void setSorted_c_shortList(String sorted_c_shortList) {
		this.sorted_c_shortList.add(sorted_c_shortList);
	}

	public ArrayList<String> getSorted_c_nameList() {
		return sorted_c_nameList;
	}

	public void setSorted_c_nameList(String sorted_c_nameList) {
		this.sorted_c_nameList.add(sorted_c_nameList);
	}

	public ArrayList<String> getSorted_buyList() {
		return sorted_buyList;
	}

	public void setSorted_buyList(String sorted_buyList) {
		this.sorted_buyList.add(sorted_buyList);
	}

	public ArrayList<String> getSorted_sellList() {
		return sorted_sellList;
	}

	public void setSorted_sellList(String sorted_sellList) {
		this.sorted_sellList.add(sorted_sellList);
	}

	public ArrayList<String> getC_shortList() {
		return c_shortList;
	}

	public void setC_shortList(String shortList) {
		this.c_shortList.add(shortList);
	}

	public ArrayList<String> getC_nameList() {
		return c_nameList;
	}

	public void setC_nameList(String nameList) {
		this.c_nameList.add(nameList);
	}

	public ArrayList<String> getBuyList() {
		return buyList;
	}

	public void setBuyList(String buyList) {
		this.buyList.add(buyList);
	}

	public ArrayList<String> getSellList() {
		return sellList;
	}

	public void setSellList(String sellList) {
		this.sellList.add(sellList);
	}

	public ArrayList<String> getAllList() {
		return allList;
	}

	public void setAllList(String allList) {
		this.allList.add(allList);
	}

	public String[] getArrayOfBank() {
		return arrayOfBank;
	}


	public Date getDate() {
		return date;
	}

	public void setDate() {
		this.date = new Date();
	
	}

	public int getBase() {
		return BASE_VALUE;
	}
	
	public DecimalFormat getDecFormat() {
		return decFormat;
	}
	
}


