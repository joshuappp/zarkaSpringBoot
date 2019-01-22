package com.digitalacademy.www.ZARKa.services;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.digitalacademy.www.ZARKa.config.EmailConfiguration;
import com.digitalacademy.www.ZARKa.dao.SettingsRepo;
import com.digitalacademy.www.ZARKa.dao.UserRepo;
import com.digitalacademy.www.ZARKa.entities.Settings;
import com.digitalacademy.www.ZARKa.entities.User;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;



@Service
public class ServNotification implements ApplicationListener<ApplicationReadyEvent>{
	
	
	@Autowired
	private SettingsRepo settingRepo;
	
	@Autowired
	private ServReport servReport;
	
	@Autowired
	private UserRepo userRepo;
	

	private EmailConfiguration emailConfig;
	
		
	//Method to prepare and format email structure for weekly currency report
	public void sendMailNotification() throws MailException, InterruptedException, DocumentException, MessagingException
	{
		//==================||Email Properties Configuration||=================//
		JavaMailSenderImpl email = new JavaMailSenderImpl();
		
		emailConfig = new EmailConfiguration("mail.waggy.co.za", 587, "testemail@waggy.co.za", "Zarka@100#");
	
		email.setHost(emailConfig.getHost());
		email.setPort(emailConfig.getPort());
		email.setUsername(emailConfig.getUsername());
		email.setPassword(emailConfig.getPassword());

		MimeMessage message = email.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		Iterable<User> allUsers = userRepo.findAll();

		Settings currentUserSettings = null;
		
		for(User currentUser: allUsers)
		{
			//Delete report if in case it's available.
			ReportPDF.deleteDocument(ReportPDF.getFILE());
			
			//Get Settings For Current User
			currentUserSettings = settingRepo.findByUserId(currentUser.getId());
			
			
			//Check If Notify is true or false and via is equal to Email or (APP, All)
			if(currentUserSettings.isNotify() == true && currentUserSettings.getVia().equals("Email") || currentUserSettings.getVia().equals("Both"))
			{
		        //Get The Minutes Only, From The User Time In The Database and Minutes From The Current Time(now) For Comparisons
		        int getMinutesFromUserTime = Integer.parseInt(currentUserSettings.getReportTime().substring(currentUserSettings.getReportTime().indexOf(":") + 1, currentUserSettings.getReportTime().length()));
		        int getMinutesFromCurrentTime = Integer.parseInt(servReport.currentTimeFormatter().substring(servReport.currentTimeFormatter().indexOf(":") + 1, servReport.currentTimeFormatter().length()));
		        
		        
		       //Thread.sleep(500);
		        int getHourFromUserTime = Integer.parseInt(currentUserSettings.getReportTime().substring(0, currentUserSettings.getReportTime().indexOf(":")));
		        int getHoursFromCurrentTime = Integer.parseInt(servReport.currentTimeFormatter().substring(0,servReport.currentTimeFormatter().indexOf(":")));
		       
		        
		        //Check If user Report Day is equal to the current day e.g: Wednesday 
				if(currentUserSettings.getReportDay().equals(servReport.currentDay()))
				{
					//Check if report time is equal or in between the current time plus 10 minutes
					if((getMinutesFromUserTime == getMinutesFromCurrentTime) && (getHourFromUserTime == getHoursFromCurrentTime))
					{
						//Setting the email properties 
						helper.setTo(currentUser.getEmail());
						helper.setFrom("zarka@team2external.thedigitalacademy.co.za");		//
						helper.setSubject("Weekly Currency Report");
						helper.setText("Your weekly Report");
						
						if(currentUserSettings.getCurrency().equals("All"))
						{
							servReport.topThreeCurrenciesReport(currentUserSettings);
							FileSystemResource file = new FileSystemResource(new File(ReportPDF.getFILE()));  //Get current report location
							helper.addAttachment("report.pdf", file);		//Attach report to email
							
							System.out.println("Test Email Sending");
							
						}
						else
						{
							servReport.currencyReport(currentUserSettings);	
							FileSystemResource file = new FileSystemResource(new File(ReportPDF.getFILE()));
							helper.addAttachment("report.pdf", file);
						}
					
						email.send(message);	//Send email.
						
						ReportPDF.deleteDocument(ReportPDF.getFILE());  //delete the current report.
						ReportPDF.setDocument(new Document());			//get a new instance of the Document. to be able to create a new report
						
						//Thread.sleep(5000);
					}
					else
					{
						continue;
					}
					
				}
				else
				{
					continue;
				}
				
			}
			else
			{
				System.out.println("Notify might be false(off) OR The User Is Only notified by App");
				continue;
			}
			
		}
		
	}
	
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		while(true)
		{
			try {
				
				sendMailNotification();
				System.out.print("Test");
				Thread.sleep(60000);
				System.out.println("Emails Sent");
				
			} catch (MailException | InterruptedException | DocumentException | MessagingException  e) {
				
				e.printStackTrace();
			}
			
			
		}
		
	}
	
}
	
