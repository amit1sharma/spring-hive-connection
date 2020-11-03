package com.amt.unb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amt.unb.contant.HiveDataConstants;
import com.amt.unb.dao.hive.ICCSDataDao;
import com.amt.unb.dto.RequestDto;
import com.amt.unb.exception.HiveCustomException;
import com.amt.unb.service.DataService;

@Service("iCCSDataServiceImpl")
public class ICCSDataServiceImpl implements DataService {
	@Autowired
	private ICCSDataDao hiveDataDao;
	
    @Override
    public List<Map<String, Object>> getRecords(RequestDto requestDto) throws HiveCustomException {
    	List<Map<String, Object>> result = new ArrayList<>();
//    	return hiveDataDao.getRecords(requestDto);
    	switch(requestDto.getHeader().getSubRequestType()){
			case HiveDataConstants.SUB_REQUEST_TYPE_OUTWARD:
				result = hiveDataDao.getRecordsOutward(requestDto);
				break;
			case HiveDataConstants.SUB_REQUEST_TYPE_INWARD:
				result = hiveDataDao.getRecordsInward(requestDto);
				break;
			case HiveDataConstants.SUB_REQUEST_TYPE_ONUS:
				result = hiveDataDao.getRecordsOnus(requestDto);
				break;
			case HiveDataConstants.SUB_REQUEST_TYPE_RETURN_MEMO:
				result = hiveDataDao.getRecordsReturnMemo(requestDto);
				break;
			default :
				break;
		}
    	return result;
    }
}
