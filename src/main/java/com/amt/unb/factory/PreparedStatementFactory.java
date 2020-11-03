package com.amt.unb.factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amt.unb.contant.HiveDataConstants;
import com.amt.unb.dto.RequestDto;

@Component
public class PreparedStatementFactory {
	
	@Value("${iccs.query.outward}")
	private String iccsQueryOutward;
	
	@Value("${iccs.query.inward}")
	private String iccsOueryInward;
	
	@Value("${iccs.query.onus}")
	private String iccsOueryOnus;
	
	@Value("${iccs.query.returnmemo}")
	private String iccsOueryReturnMemo;
	
	public PreparedStatement getStatement(Connection con, RequestDto dto, String subRequestType) throws SQLException{
		PreparedStatement ps = null;
		switch(subRequestType){
			case HiveDataConstants.SUB_REQUEST_TYPE_OUTWARD:
				ps = getStatementForOutward(con, dto);
				break;
			case HiveDataConstants.SUB_REQUEST_TYPE_INWARD:
				ps = getStatementForInward(con, dto);
				break;
			case HiveDataConstants.SUB_REQUEST_TYPE_ONUS:
				ps = getStatementForOnus(con, dto);
				break;
			case HiveDataConstants.SUB_REQUEST_TYPE_RETURN_MEMO:
				ps = getStatementForReturnMemo(con, dto);
				break;
			default :
				break;
		}
		return ps;
	}
	

	public PreparedStatement getStatementForOutward(Connection con, RequestDto dto) throws SQLException{
		PreparedStatement ps = con.prepareStatement(iccsQueryOutward);
		ps.setString(1, dto.getBody().getBusinessDate());
		ps.setString(2, dto.getBody().getItemDin());
		ps.setString(3, dto.getBody().getCreditAccount());
		ps.setString(4, dto.getBody().getAmount());
		ps.setString(5, dto.getBody().getChequeNumber());
		ps.setString(6, dto.getBody().getRouteNumber());
		ps.setString(7, dto.getBody().getDepositAccountId());
	    return ps;
	}
	
	public PreparedStatement getStatementForInward(Connection con, RequestDto dto) throws SQLException{
		PreparedStatement ps = con.prepareStatement(iccsOueryInward);
		ps.setString(1, dto.getBody().getBusinessDate());
		ps.setString(2, dto.getBody().getItemDin());
		ps.setString(3, dto.getBody().getNewChequeNumber());
		ps.setString(4, dto.getBody().getNewChequeAccount());
		ps.setString(5, dto.getBody().getAmount());
		ps.setString(6, dto.getBody().getNewChequeRT());
		ps.setString(7, dto.getBody().getPayerIban());
	    return ps;
	}
	
	public PreparedStatement getStatementForOnus(Connection con, RequestDto dto) throws SQLException{
		PreparedStatement ps = con.prepareStatement(iccsOueryOnus);
		ps.setString(1, dto.getBody().getBusinessDate());
		ps.setString(2, dto.getBody().getItemDin());
		ps.setString(3, dto.getBody().getCreditAccount());
		ps.setString(4, dto.getBody().getAmount());
		ps.setString(5, dto.getBody().getChequeNumber());
		ps.setString(6, dto.getBody().getRouteNumber());
		ps.setString(7, dto.getBody().getDepositAccountId());
	    return ps;
	}
	
	public PreparedStatement getStatementForReturnMemo(Connection con, RequestDto dto) throws SQLException{
		
		if(StringUtils.isNotBlank(dto.getBody().getBusinessDate())){
			iccsOueryReturnMemo += " and o.BUSINESSDATE =:BUSINESSDATE ";
		}
		if(StringUtils.isNotBlank(dto.getBody().getDrawAccount())){
			iccsOueryReturnMemo += " and o.DRAWACC  =:DRAWACC  ";
		}
		if(StringUtils.isNotBlank(dto.getBody().getChequeNumber())){
			iccsOueryReturnMemo += " and o.CHQNO  =:CHQNO  ";
		}
		PreparedStatement ps = con.prepareStatement(iccsOueryReturnMemo);
		if(StringUtils.isNotBlank(dto.getBody().getBusinessDate())){
			ps.setString(1, dto.getBody().getBusinessDate());
		}
		if(StringUtils.isNotBlank(dto.getBody().getDrawAccount())){
			ps.setString(2, dto.getBody().getDrawAccount());
		}
		if(StringUtils.isNotBlank(dto.getBody().getChequeNumber())){
			ps.setString(3, dto.getBody().getChequeNumber());
		}
		
	    return ps;
	}
}
