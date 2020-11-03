package com.amt.unb.facade;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amt.unb.dto.LogRequestDto;
import com.amt.unb.dto.RequestDto;
import com.amt.unb.exception.HiveCustomException;
import com.amt.unb.factory.HiveDataServiceFactory;
import com.amt.unb.service.AuditLogService;
import com.amt.unb.service.DataService;
import com.amt.unb.service.FileDownloadService;
import com.amt.unb.utils.Utility;

@Component
public class HiveDataServiceFacade {


    @Autowired
    private HiveDataServiceFactory hiveDataServiceFactory;
    
    @Autowired
    private FileDownloadService fileDownloadService;

    public List<Map<String, Object>> getData(RequestDto requestDto) throws HiveCustomException {
        DataService dataService = hiveDataServiceFactory.getDataService(requestDto);
        return dataService.getRecords(requestDto);
    }
    
    public byte[] downloadFile(String filePath) throws ClientProtocolException, IOException, HiveCustomException{
		return fileDownloadService.downloadFile(filePath);
    }
    
    public void saveLog(LogRequestDto dto) throws HiveCustomException{
    	AuditLogService service = hiveDataServiceFactory.getAuditLogService(dto);
    	service.saveLog(dto);
    }
}
