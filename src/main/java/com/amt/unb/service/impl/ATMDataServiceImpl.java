package com.amt.unb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amt.unb.contant.HiveDataConstants;
import com.amt.unb.dao.hive.ATMDataDao;
import com.amt.unb.dto.RequestDto;
import com.amt.unb.exception.HiveCustomException;
import com.amt.unb.service.DataService;

@Service("aTMDataServiceImpl")
public class ATMDataServiceImpl implements DataService {
	
	@Autowired
	private ATMDataDao atmDataDao;
	
	 @Override
	    public List<Map<String, Object>> getRecords(RequestDto requestDto) throws HiveCustomException {
	    	List<Map<String, Object>> result = new ArrayList<>();
	    	switch(requestDto.getHeader().getSubRequestType()){
				case HiveDataConstants.SUB_REQUEST_TYPE_ATM:
					result = atmDataDao.getATMList(requestDto);
					break;
				case HiveDataConstants.SUB_REQUEST_TYPE_EJOUR_DDL:
					result = atmDataDao.getEjournalDdl(requestDto);
					break;
				case HiveDataConstants.SUB_REQUEST_TYPE_EJOUR:
					result = atmDataDao.getEJournalList(requestDto);
					break;
				default :
					break;
			}
	    	return result;
	    }
    
    
}
