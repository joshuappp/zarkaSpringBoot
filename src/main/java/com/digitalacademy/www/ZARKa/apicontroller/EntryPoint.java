package com.digitalacademy.www.ZARKa.apicontroller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.digitalacademy.www.ZARKa.dao.SettingsRepo;
import com.digitalacademy.www.ZARKa.dao.UserRepo;
import com.digitalacademy.www.ZARKa.entities.Settings;
import com.digitalacademy.www.ZARKa.entities.User;
import com.digitalacademy.www.ZARKa.services.ServBank;
import com.digitalacademy.www.ZARKa.services.ServReport;
import com.digitalacademy.www.ZARKa.services.ServUser;


@RestController
@RequestMapping("/access")
public class EntryPoint {
	
	@Autowired
	private ServBank servBank;

	@Autowired
	private ServUser servUser;
	
	@Autowired
	private SettingsRepo setRepo;
	
	@Autowired
	private ServReport servReport;
	
	@Autowired
	private UserRepo userRepo;	

	@CrossOrigin  // allow every device access data 
	@GetMapping("/latestdata")
	public ArrayList<Object> getLatestdata() {
		
		return servBank.getLatestData();
		
	}
	
	@CrossOrigin // allow remote devices/nodes to access data 
	@GetMapping("/history")
	public Iterable<Object> getHistoryData() {
		
		return servBank.getHistory();
	}
	
//=================================================================//
	
	//Save User Data, To the Database
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/usersave")
	public User saveUser(@RequestBody User user) throws NotFoundException
	{	
		return servUser.saveUser(user);
	}
	
	
//	//Test This one 
//	@CrossOrigin
//	@RequestMapping(method = RequestMethod.POST, value = "/usersavetwo")
//	public Optional<Object> saveUserTwo(@RequestBody User user) throws NotFoundException
//	{	
//		return servUser.saveUserTwo(user);
//	}	
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value="getuserdata/{id}")
	public Optional<Object> getUserData(@PathVariable ("id") long id )
	{
		return servUser.getUserData(id);
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value="/updatesettings/{id}")
    public Settings updateSettings(@PathVariable Long id, @RequestBody Settings settingsUpdate) throws NotFoundException 
	{
        return servUser.updateSettings(id, settingsUpdate);
	}
  
	//Get specific user(by email) from the database 
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value="/getuserby/{email}")
	public Optional<Object> getUser(@PathVariable ("email") String email)
	{ 
	  return servUser.getUserBy(email);
	}
	
	//========================GET SETTINGS BY=======================///
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value="/getsettingsby/{id}")
	public Optional<Settings> getSettingsById(@PathVariable ("id") Long id)
	{
		return servUser.getSettingsById(id);
	}
	
	//========================TRY QUERY=============================///
	@RequestMapping(method = RequestMethod.GET, value="/getreport/{b_id}/{c_id}")
	public ArrayList<Object> getWeeklyReportBy(@PathVariable ("b_id") int b_id, @PathVariable ("c_id") int c_id)
	{
		
//		String report = servReport.currencyReport(setRepo.findByUserId((long) 5));
//		
//		String reportAllCur = servReport.topThreeCurrenciesReport();
//		
//		System.out.println(report);
//		System.out.println(reportAllCur);
		
		return setRepo.getWeeklyReportSell(b_id, c_id);
	}
	
//	@RequestMapping(method = RequestMethod.GET, value="/notify")
//	public String notifyBasedOnCurrency()
//	{
//		servNotification.notifyBasedOnValue();
//		
//		return "Notification Successfully sent!";
//	}
//	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/getuser/{email}")
	public Optional<User> getUSerByEmail(@PathVariable ("email") String email)
	{
		return servUser.getUserByEmail(email);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getusers")
	public Iterable<User> getDay()
	{
		
		Iterable<User> allUsers = userRepo.findAll();
		
		return allUsers;
	}
	
//	@RequestMapping(method = RequestMethod.GET, value = "/test")
//	public void notifyBasedOnValue() 
//	{
//		servNotification.notifyBasedOnValue();
//	}

}




