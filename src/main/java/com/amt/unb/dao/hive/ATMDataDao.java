package com.amt.unb.dao.hive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.amt.unb.dto.RequestDto;
import com.amt.unb.exception.HiveCustomException;
import com.amt.unb.utils.Utility;

@Repository
public class ATMDataDao {
	
	@Value("${insuffucient.param.exception.code}")
	private int insufficientParamExceptionCode;
	
	@Value("${insuffucient.param.exception.desc}")
	private String insufficientParamExceptionDescription;
	
	@Autowired
	@Qualifier("hiveJdbcNamedTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${atm.query.atm.list}")
	private String atmQueryAtmList;
	
	@Value("${atm.query.ejour.ddl}")
	private String atmQueryEjourDdl;
	
	@Value("${atm.query.ejour.list}")
	private String atmQueryEjourList;
	

	public List<Map<String, Object>> getATMList(RequestDto dto) throws HiveCustomException{
		String atmQueryAtmList_tmp = atmQueryAtmList;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		return namedParameterJdbcTemplate.queryForList(atmQueryAtmList_tmp, paramSource);
	}
	
	public List<Map<String, Object>> getEjournalDdl(RequestDto dto) throws HiveCustomException{
		String atmQueryEjourDdl_tmp = atmQueryEjourDdl;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		return namedParameterJdbcTemplate.queryForList(atmQueryEjourDdl_tmp, paramSource);
	}

	public List<Map<String, Object>> getEJournalList(RequestDto dto) throws HiveCustomException{
		String atmQueryEjourList_tmp = atmQueryEjourList;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
//		Map<String, String> paramSource = new HashMap<String, String>();
		
		
		atmQueryEjourList_tmp += Utility.addWhereCondition( "TERMINAL_ID", dto.getBody().getTerminalId(), paramSource);
		atmQueryEjourList_tmp += Utility.addWhereCondition( "FILE_DATE", dto.getBody().getFileDate(), paramSource);
		
		if(paramSource==null ||paramSource.getValues().isEmpty()){
			throw new HiveCustomException(insufficientParamExceptionCode, insufficientParamExceptionDescription);
		}
		atmQueryEjourList_tmp = Utility.addLimitOffset(atmQueryEjourList_tmp, dto.getHeader().getPageNo(), dto.getHeader().getNoOfRecordsInPage());
		return namedParameterJdbcTemplate.queryForList(atmQueryEjourList_tmp, paramSource);
	}
	

}
