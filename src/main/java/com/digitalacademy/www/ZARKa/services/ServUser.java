package com.digitalacademy.www.ZARKa.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.digitalacademy.www.ZARKa.dao.SettingsRepo;
import com.digitalacademy.www.ZARKa.dao.UserRepo;
import com.digitalacademy.www.ZARKa.entities.Settings;
import com.digitalacademy.www.ZARKa.entities.User;


@Service
public class ServUser {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private SettingsRepo settingsRepo;
	
	

	public User saveUser(User user) throws NotFoundException
	{
		if(userRepo.findByEmail(user.getEmail()) == null) 
		{
			userRepo.save(user);
			
			if(!settingsRepo.findById(user.getId()).isPresent())
			{
				saveSettings(user.getId(), new Settings());
			}
			return user;
		}
		else
		{
			return null;
		}
		
	}
	
	
//	//Test This one
//	public Optional<Object> saveUserTwo(User user) throws NotFoundException
//	{
//		if(userRepo.findByEmail(user.getEmail()) == null) 
//		{
//			userRepo.save(user);
//			
//			if(!settingsRepo.findById(user.getId()).isPresent())
//			{
//				saveSettings(user.getId(), new Settings());
//			}
//			return settingsRepo.getUserData(user.getId());
//		}
//		else
//		{
//			return null;
//		}
//		
//	}


	public Settings saveSettings(Long id, Settings settings) throws NotFoundException 
	{
		   return userRepo.findById(id).map(user -> {
		       settings.setUser(user);
		       return settingsRepo.save(settings);
		   }).orElseThrow(() -> new NotFoundException());
	}
	
	 public Optional<Object> getUserData(long id) 
	 {
		 
			if(settingsRepo.getUserData(id).isPresent())
			{
				return settingsRepo.getUserData(id);
			}
			else
			{
				return null;
			}
		
	 }
	 
    public Settings updateSettings(Long id, Settings settingsUpdate) throws NotFoundException 
    {
        return settingsRepo.findById(id)
                .map(settings -> {
                    settings.setBank(settingsUpdate.getBank());
                    settings.setCurrency(settingsUpdate.getCurrency());
                    settings.setVia(settingsUpdate.getVia());
                    settings.setNotify(settingsUpdate.isNotify());
                    settings.setSellAmountNotify(settingsUpdate.getSellAmountNotify());
                    settings.setBuyAmountNotify(settingsUpdate.getBuyAmountNotify());
                    settings.setSellOrBuy(settingsUpdate.getSellOrBuy());
                    settings.setReportDay(settingsUpdate.getReportDay());
                    settings.setReportTime(settingsUpdate.getReportTime());
                    return settingsRepo.save(settings);
                }).orElseThrow(() -> new NotFoundException());
    }
    
    public Optional<Object> getUserBy(String email)
    {
    	return userRepo.getUserBy(email);
    }
    
	public Optional<User> getUser(long id)
	{
		return userRepo.findById(id);
	}
	
	//Settings byb id
	public Optional<Settings> getSettingsById(Long id)
	{
		return settingsRepo.findById(id);
	}
	
	
	public Settings getSettings(Long id)
	{
		return settingsRepo.findByUserId(id);
	}
    
	public Optional<User> getUserByEmail(String email)
	{
		
		User localUser = userRepo.findByEmail(email);

		return userRepo.findById(localUser.getId());
	}
}
