package com.amt.unb.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amt.unb.dao.phub.ICCSAuditLogRepository;
import com.amt.unb.dto.LogRequestDto;
import com.amt.unb.entity.phub.ICCSAuditLogModel;
import com.amt.unb.service.AuditLogService;

@Service("iCCSAuditLogService")
public class ICCSAuditLogService implements AuditLogService{
	
	@Autowired
	private ICCSAuditLogRepository auditLogRepository;

	@Override
	public void saveLog(LogRequestDto dto) {
		// TODO Auto-generated method stub
		ICCSAuditLogModel model = new ICCSAuditLogModel();
		model.setMediaName(dto.getMediaName());
		model.setRequestorModule(dto.getRequesterModule());
		model.setRequestTimeStamp(new Date());
		model.setRequestType(dto.getRequestType());
		model.setSubRequestType(dto.getSubRequestType());
		model.setUniqueIdentifier(dto.getUniqueIdentifier());
		auditLogRepository.save(model);
	}

}
