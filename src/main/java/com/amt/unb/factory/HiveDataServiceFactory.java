package com.amt.unb.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.amt.unb.contant.HiveDataConstants;
import com.amt.unb.dto.LogRequestDto;
import com.amt.unb.dto.RequestDto;
import com.amt.unb.exception.HiveCustomException;
import com.amt.unb.service.AuditLogService;
import com.amt.unb.service.DataService;

@Component
public class HiveDataServiceFactory {
    @Autowired
    @Qualifier("iCCSDataServiceImpl")
    private DataService iccsDataService;
    
    @Autowired
    @Qualifier("dDSDataServiceImpl")
    private DataService dDSDataService;
    
    @Autowired
    @Qualifier("pMSDataServiceImpl")
    private DataService pMsDataService;
    
    
    @Autowired
    @Qualifier("aTMDataServiceImpl")
    private DataService aTMDataServiceImpl;
    
    
    @Autowired
    @Qualifier("iCCSAuditLogService")
    private AuditLogService iccsAuditLogService;

    public DataService getDataService(RequestDto requestDto) throws HiveCustomException{
        if(HiveDataConstants.REQUEST_TYPE_ICCS.equalsIgnoreCase(requestDto.getHeader().getRequestType())){
            return iccsDataService;
        } else if(HiveDataConstants.REQUEST_TYPE_DDS.equalsIgnoreCase(requestDto.getHeader().getRequestType())){
        	return dDSDataService;
        } else if(HiveDataConstants.REQUEST_TYPE_PMS.equalsIgnoreCase(requestDto.getHeader().getRequestType())){
        	return pMsDataService;
        } else if(HiveDataConstants.REQUEST_TYPE_ATM.equalsIgnoreCase(requestDto.getHeader().getRequestType())){
        	return aTMDataServiceImpl;
        } else {
            throw new HiveCustomException(2, "No DataService found for request type : " + requestDto.getHeader().getRequestType());
        }
    }
    
    public AuditLogService getAuditLogService(LogRequestDto dto) throws HiveCustomException{
    	if(HiveDataConstants.REQUEST_TYPE_ICCS.equals(dto.getRequestType())){
    		return iccsAuditLogService;
    	} else{
    		throw new HiveCustomException(2, "No AuditLogService found for request type : " + dto.getRequestType());
    	}
    }
    
    
}
