package com.digitalacademy.www.ZARKa.dao;

import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.digitalacademy.www.ZARKa.entities.Settings;


@Service
@Transactional
@Component
public interface SettingsRepo extends CrudRepository<Settings, Long>{
	
	@Query(value="CALL getUserData(?)", nativeQuery=true)
	Optional<Object> getUserData(long id);
	
	
	Settings findByUserId(Long id);
	
	Optional<Settings> findById(Long id);
	
	
	@Query(value="CALL getWeeklyReportAll(?,?)",nativeQuery=true)
	ArrayList<Object> getWeeklyReportAll(int b_id, int c_id);
	
	@Query(value="CALL getWeeklyReportBuy(?,?)",nativeQuery=true)
	ArrayList<Object> getWeeklyReportBuy(int b_id, int c_id);
	
	@Query(value="CALL getWeeklyReportSell(?,?)",nativeQuery=true)
	ArrayList<Object> getWeeklyReportSell(int b_id, int c_id);
	
	@Query(value="CALL getWeeklyReportAllBC()",nativeQuery=true)
	ArrayList<Object> getWeeklyReportAllBC();
	
	@Query(value="CALL getWRBuyFromAllBanks(?)",nativeQuery=true)
	ArrayList<Object> getWRBuyFromAllBanks(int c_id);
	
	@Query(value="CALL getWRSellFromAllBanks(?)",nativeQuery=true)
	ArrayList<Object> getWRSellFromAllBanks(int c_id);
	
	@Query(value="CALL getWRBuySellFromAllBanks(?)",nativeQuery=true)
	ArrayList<Object> getWRBuySellFromAllBanks(int c_id);

}
