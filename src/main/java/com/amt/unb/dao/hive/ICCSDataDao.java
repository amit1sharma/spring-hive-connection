package com.amt.unb.dao.hive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.amt.unb.config.HiveDataSourceConfig;
import com.amt.unb.dto.RequestDto;
import com.amt.unb.exception.HiveCustomException;
import com.amt.unb.utils.Utility;

@Repository
public class ICCSDataDao {
	
	@Autowired
	private HiveDataSourceConfig hiveDataSourceConfig;
	
//	@Autowired
//	private PreparedStatementFactory psf;

	
	@Value("${insuffucient.param.exception.code}")
	private int insufficientParamExceptionCode;
	
	@Value("${insuffucient.param.exception.desc}")
	private String insufficientParamExceptionDescription;
	
	@Autowired
	@Qualifier("hiveJdbcNamedTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${iccs.query.outward}")
	private String iccsQueryOutward;
	
	@Value("${iccs.query.inward}")
	private String iccsQueryInward;
	
	@Value("${iccs.query.onus}")
	private String iccsQueryOnus;
	
	@Value("${iccs.query.returnmemo}")
	private String iccsQueryReturnMemo;
	
	/**
	 * this method to direct make connection from driver without need of creating jtemplate
	 * @param dto
	 * @return
	 */
	public List getRecords(RequestDto dto){
		List lst = new ArrayList<>();
		try(Connection con = hiveDataSourceConfig.getHiveConnection();
			PreparedStatement ps = getStatementForInward(con, dto);
			ResultSet rs = ps.executeQuery()) {
			if(rs!=null){
				 while(rs.next()) {
			    	  System.out.println(rs.getString("image1"));
			      }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	public PreparedStatement getStatementForInward(Connection con, RequestDto dto) throws SQLException{
		PreparedStatement ps = con.prepareStatement(iccsQueryInward);
		ps.setString(1, dto.getBody().getItemDin());
	    return ps;
	}
	

	//WHERE TRXTYPE = D and BUSINESSDATE = ? and ITEMDIN = ? and CREDITACCOUNT=? and AMOUNT = ? AND CHQNO=? AND ROUTNO = ? DEPOSIT_ACCOUNT_ID = ?
	public List<Map<String, Object>> getRecordsOutward(RequestDto dto) throws HiveCustomException{
		String iccsQueryOutward_tmp = iccsQueryOutward;
//		boolean atleastOne = false;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		iccsQueryOutward_tmp += Utility.addWhereCondition( "BUSINESSDATE", dto.getBody().getBusinessDate(), paramSource);
		iccsQueryOutward_tmp += Utility.addWhereCondition( "ITEMDIN", dto.getBody().getItemDin(), paramSource);
		iccsQueryOutward_tmp += Utility.addWhereCondition( "CREDITACCOUNT", dto.getBody().getCreditAccount(), paramSource);
		iccsQueryOutward_tmp += Utility.addWhereCondition( "AMOUNT", dto.getBody().getAmount(), paramSource);
		iccsQueryOutward_tmp += Utility.addWhereCondition( "CHQNO", dto.getBody().getChequeNumber(), paramSource);
		iccsQueryOutward_tmp += Utility.addWhereCondition( "ROUTNO", dto.getBody().getRouteNumber(), paramSource);
		iccsQueryOutward_tmp += Utility.addWhereCondition( "DEPOSIT_ACCOUNT_ID", dto.getBody().getDepositAccountId(), paramSource);
		
		/*if(StringUtils.isNotBlank(dto.getBody().getBusinessDate())){
			iccsQueryOutward_tmp += " and BUSINESSDATE =:BUSINESSDATE";
			paramSource.addValue("BUSINESSDATE", dto.getBody().getBusinessDate());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getItemDin())){
			iccsQueryOutward_tmp += " and ITEMDIN =:ITEMDIN";
			paramSource.addValue("ITEMDIN", dto.getBody().getItemDin());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getCreditAccount())){
			iccsQueryOutward_tmp += " and CREDITACCOUNT =:CREDITACCOUNT";
			paramSource.addValue("CREDITACCOUNT", dto.getBody().getCreditAccount());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getAmount())){
			iccsQueryOutward_tmp += " and AMOUNT =:AMOUNT";
			paramSource.addValue("AMOUNT", dto.getBody().getAmount());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getChequeNumber())){
			iccsQueryOutward_tmp += " and CHQNO =:CHQNO";
			paramSource.addValue("CHQNO", dto.getBody().getChequeNumber());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getRouteNumber())){
			iccsQueryOutward_tmp += " and ROUTNO =:ROUTNO";
			paramSource.addValue("ROUTNO", dto.getBody().getRouteNumber());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getDepositAccountId())){
			iccsQueryOutward_tmp += " and DEPOSIT_ACCOUNT_ID =:DEPOSIT_ACCOUNT_ID";
			paramSource.addValue("DEPOSIT_ACCOUNT_ID", dto.getBody().getDepositAccountId());
			atleastOne = true;
		}*/
		if(paramSource==null ||paramSource.getValues().isEmpty()){
			throw new HiveCustomException(insufficientParamExceptionCode, insufficientParamExceptionDescription);
		}
		iccsQueryOutward_tmp = Utility.addLimitOffset(iccsQueryOutward_tmp, dto.getHeader().getPageNo(), dto.getHeader().getNoOfRecordsInPage());
		return namedParameterJdbcTemplate.queryForList(iccsQueryOutward_tmp, paramSource);
	}

	//BUSINESSDATE = ? and ITEMDIN = ? and NEWCHQNO=? and NEWCHQACC=? and amount = ? and NEWCHQRT =? and PAYER_IBAN =?
	public List<Map<String, Object>> getRecordsInward(RequestDto dto) throws HiveCustomException{
		String iccsQueryInward_tmp = iccsQueryInward;
//		boolean atleastOne = false;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		iccsQueryInward_tmp += Utility.addWhereCondition( "BUSINESSDATE", dto.getBody().getBusinessDate(), paramSource);
		iccsQueryInward_tmp += Utility.addWhereCondition( "ITEMDIN", dto.getBody().getItemDin(), paramSource);
		iccsQueryInward_tmp += Utility.addWhereCondition( "NEWCHQNO", dto.getBody().getNewChequeNumber(), paramSource);
		iccsQueryInward_tmp += Utility.addWhereCondition( "NEWCHQACC", dto.getBody().getNewChequeAccount(), paramSource);
		iccsQueryInward_tmp += Utility.addWhereCondition( "amount", dto.getBody().getAmount(), paramSource);
		iccsQueryInward_tmp += Utility.addWhereCondition( "NEWCHQRT", dto.getBody().getNewChequeRT(), paramSource);
		iccsQueryInward_tmp += Utility.addWhereCondition( "PAYER_IBAN", dto.getBody().getPayerIban(), paramSource);
		
		/*if(StringUtils.isNotBlank(dto.getBody().getBusinessDate())){
			iccsQueryInward_tmp += " and BUSINESSDATE =:BUSINESSDATE";
			paramSource.addValue("BUSINESSDATE", dto.getBody().getBusinessDate());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getItemDin())){
			iccsQueryInward_tmp += " and ITEMDIN =:ITEMDIN";
			paramSource.addValue("ITEMDIN", dto.getBody().getItemDin());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getNewChequeNumber())){
			iccsQueryInward_tmp += " and NEWCHQNO =:NEWCHQNO";
			paramSource.addValue("NEWCHQNO", dto.getBody().getNewChequeNumber());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getNewChequeAccount())){
			iccsQueryInward_tmp += " and NEWCHQACC =:NEWCHQACC";
			paramSource.addValue("NEWCHQACC", dto.getBody().getNewChequeAccount());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getAmount())){
			iccsQueryInward_tmp += " and amount =:amount";
			paramSource.addValue("amount", dto.getBody().getAmount());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getNewChequeRT())){
			iccsQueryInward_tmp += " and NEWCHQRT =:NEWCHQRT";
			paramSource.addValue("NEWCHQRT", dto.getBody().getNewChequeRT());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getPayerIban())){
			iccsQueryInward_tmp += " and PAYER_IBAN =:PAYER_IBAN";
			paramSource.addValue("PAYER_IBAN", dto.getBody().getPayerIban());
			atleastOne = true;
		}*/
		if(paramSource==null ||paramSource.getValues().isEmpty()){
			throw new HiveCustomException(insufficientParamExceptionCode, insufficientParamExceptionDescription);
		}
		iccsQueryInward_tmp = Utility.addLimitOffset(iccsQueryInward_tmp, dto.getHeader().getPageNo(), dto.getHeader().getNoOfRecordsInPage());
		return namedParameterJdbcTemplate.queryForList(iccsQueryInward_tmp, paramSource);
	}
	
	//	BUSINESSDATE = [INPUT] ITEMDIN = [INPUT] CREDITACCOUNT = [INPUT] AMOUNT = [INPUT] CHQNO = [INPUT]  ROUTNO = [INPUT] DEPOSIT_ACCOUNT_ID = [INPUT]	
	public List<Map<String, Object>> getRecordsOnus(RequestDto dto) throws HiveCustomException{
		String iccsQueryOnus_tmp = iccsQueryOnus;
		boolean atleastOne = false;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		
		iccsQueryOnus_tmp += Utility.addWhereCondition( "BUSINESSDATE", dto.getBody().getBusinessDate(), paramSource);
		iccsQueryOnus_tmp += Utility.addWhereCondition( "ITEMDIN", dto.getBody().getItemDin(), paramSource);
		iccsQueryOnus_tmp += Utility.addWhereCondition( "CREDITACCOUNT", dto.getBody().getCreditAccount(), paramSource);
		iccsQueryOnus_tmp += Utility.addWhereCondition( "AMOUNT", dto.getBody().getAmount(), paramSource);
		iccsQueryOnus_tmp += Utility.addWhereCondition( "CHQNO", dto.getBody().getChequeNumber(), paramSource);
		iccsQueryOnus_tmp += Utility.addWhereCondition( "ROUTNO", dto.getBody().getRouteNumber(), paramSource);
		iccsQueryOnus_tmp += Utility.addWhereCondition( "DEPOSIT_ACCOUNT_ID", dto.getBody().getDepositAccountId(), paramSource);

		/*if(StringUtils.isNotBlank(dto.getBody().getBusinessDate())){
			iccsQueryOnus_tmp += " and BUSINESSDATE =:BUSINESSDATE";
			paramSource.addValue("BUSINESSDATE", dto.getBody().getBusinessDate());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getItemDin())){
			iccsQueryOnus_tmp += " and ITEMDIN =:ITEMDIN";
			paramSource.addValue("ITEMDIN", dto.getBody().getItemDin());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getCreditAccount())){
			iccsQueryOnus_tmp += " and CREDITACCOUNT =:CREDITACCOUNT";
			paramSource.addValue("CREDITACCOUNT", dto.getBody().getCreditAccount());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getAmount())){
			iccsQueryOnus_tmp += " and AMOUNT =:AMOUNT";
			paramSource.addValue("AMOUNT", dto.getBody().getAmount());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getChequeNumber())){
			iccsQueryOnus_tmp += " and CHQNO =:CHQNO";
			paramSource.addValue("CHQNO", dto.getBody().getChequeNumber());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getRouteNumber())){
			iccsQueryOnus_tmp += " and ROUTNO =:ROUTNO";
			paramSource.addValue("ROUTNO", dto.getBody().getRouteNumber());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getDepositAccountId())){
			iccsQueryOnus_tmp += " and DEPOSIT_ACCOUNT_ID =:DEPOSIT_ACCOUNT_ID";
			paramSource.addValue("DEPOSIT_ACCOUNT_ID", dto.getBody().getDepositAccountId());
			atleastOne = true;
		}*/
		if(paramSource==null ||paramSource.getValues().isEmpty()){
			throw new HiveCustomException(insufficientParamExceptionCode, insufficientParamExceptionDescription);
		}
		iccsQueryOnus_tmp = Utility.addLimitOffset(iccsQueryOnus_tmp, dto.getHeader().getPageNo(), dto.getHeader().getNoOfRecordsInPage());
		return namedParameterJdbcTemplate.queryForList(iccsQueryOnus_tmp, paramSource);
	}
	
	
	//	AND o.CHQNO = [Input] AND o.DRAWACC = [Input] AND o.BUSINESSDATE = [Input]
	public List<Map<String, Object>> getRecordsReturnMemo(RequestDto dto) throws HiveCustomException{
		String iccsQueryReturnMemo_tmp = iccsQueryReturnMemo;
//		boolean atleastOne = false;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		iccsQueryReturnMemo_tmp += Utility.addWhereCondition( "CHQNO", dto.getBody().getChequeNumber(), paramSource);
		iccsQueryReturnMemo_tmp += Utility.addWhereCondition( "DRAWACC", dto.getBody().getDrawAccount(), paramSource);
		iccsQueryReturnMemo_tmp += Utility.addWhereCondition( "BUSINESSDATE", dto.getBody().getBusinessDate(), paramSource);
		

		/*
		if(StringUtils.isNotBlank(dto.getBody().getChequeNumber())){
			iccsQueryReturnMemo_tmp += " and CHQNO =:CHQNO";
			paramSource.addValue("CHQNO", dto.getBody().getChequeNumber());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getDrawAccount())){
			iccsQueryReturnMemo_tmp += " and DRAWACC =:DRAWACC";
			paramSource.addValue("DRAWACC", dto.getBody().getDrawAccount());
			atleastOne = true;
		}
		if(StringUtils.isNotBlank(dto.getBody().getBusinessDate())){
			iccsQueryReturnMemo_tmp += " and BUSINESSDATE =:BUSINESSDATE";
			paramSource.addValue("BUSINESSDATE", dto.getBody().getBusinessDate());
			atleastOne = true;
		}*/		
		if(paramSource==null ||paramSource.getValues().isEmpty()){
			throw new HiveCustomException(insufficientParamExceptionCode, insufficientParamExceptionDescription);
		}
		iccsQueryReturnMemo_tmp = Utility.addLimitOffset(iccsQueryReturnMemo_tmp, dto.getHeader().getPageNo(), dto.getHeader().getNoOfRecordsInPage());
		return namedParameterJdbcTemplate.queryForList(iccsQueryReturnMemo_tmp, paramSource);
	}

}
