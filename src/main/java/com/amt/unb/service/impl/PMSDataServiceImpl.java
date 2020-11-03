package com.amt.unb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amt.unb.dao.hive.PMSDataDao;
import com.amt.unb.dto.RequestDto;
import com.amt.unb.exception.HiveCustomException;
import com.amt.unb.service.DataService;

@Service("pMSDataServiceImpl")
public class PMSDataServiceImpl implements DataService {
	
	@Autowired
	private PMSDataDao pmsDataDao;
	
    @Override
    public List<Map<String, Object>> getRecords(RequestDto requestDto) throws HiveCustomException {
    	return pmsDataDao.getRecords(requestDto);
    }
    
    
}
