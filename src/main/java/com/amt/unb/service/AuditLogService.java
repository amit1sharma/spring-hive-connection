package com.amt.unb.service;

import com.amt.unb.dto.LogRequestDto;

public interface AuditLogService {
	
	public void saveLog(LogRequestDto dto);

}
