package com.gea.portal.eny.service.imp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gea.portal.eny.dto.MoneyPoolDto;
import com.gea.portal.eny.enumeration.SortName;
import com.gea.portal.eny.page.PageDatas;
import com.gea.portal.eny.repository.JpaUtil;
import com.gea.portal.eny.rest.RestfulResponse;
import com.gea.portal.eny.service.MoneyPoolService;


@Service
@Transactional
public class MoneyPoolServiceImpl implements MoneyPoolService {

    @Autowired
    private JpaUtil jpaUtil;
    @Override
    public RestfulResponse<PageDatas<MoneyPoolDto>> getMoneyPool(MoneyPoolDto moneyPoolDtos) {
        RestfulResponse<PageDatas<MoneyPoolDto>> response = new RestfulResponse<>();
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<>();


        String hqlBase = "select CASE m.ACTION WHEN 'CHARGE' THEN m.CHANGE_RESERVATION ELSE m.CHANGE_BALANCE END AS amount,m.ACTION as transactionType,m.SERVICE_ID as serviceId," +
                " m.REMARK as remark,m.MP_CF_REF_NO as geaMoneyTransferId,m.LAST_TOTAL_BALANCE as afterTotalBalance," +
                " ep.REF_ID as ewRefId,mp.REF_ID as mpRefId, m.PREVIOUS_TOTAL_BALANCE as beforeTotalBalance, " +
                "m.CREATE_DATETIME as transactionDateTime ,mp.CURRENCY_TYPE as currency ,m.ref_no as refNo"
                + " from money_pool_cash_flow m "
                + "left join money_pool mp on m.MONEY_POOL_ID=mp.id "
                + "left join ewallet_participant ep on m.EWALLET_PARTICIPANT_ID=ep.id ";

        sql.append("select * from( "+hqlBase+" )as m where 1=1 ");

        String moneyPoolRefIds = getValue(moneyPoolDtos.getMoneyPoolId());

        if(moneyPoolDtos.getMoneyPoolId()!=null  && !moneyPoolDtos.getMoneyPoolId().equals("")){
            sql.append(" and m.mpRefId  in ("+moneyPoolRefIds+") ");
        }

        if(moneyPoolDtos.getGeaMoneyTransferId()!=null  && !moneyPoolDtos.getGeaMoneyTransferId().equals("")){
        	String geaMoneyTransferId = moneyPoolDtos.getGeaMoneyTransferId();
            sql.append(" and m.geaMoneyTransferId  like ? ");
            params.add("%"+geaMoneyTransferId+"%");
        }
        if(moneyPoolDtos.getServiceId()!=null && !moneyPoolDtos.getServiceId().equals("")){
            sql.append(" and m.serviceId  = ? ");
            params.add(moneyPoolDtos.getServiceId());
        }
        String transactionType;
        if(moneyPoolDtos.getTransactionType()!=null && !moneyPoolDtos.getTransactionType().equals("")){
            transactionType = moneyPoolDtos.getTransactionType();
            sql.append(" and m.transactionType  = ? ");
            params.add(transactionType);
        }
        if(moneyPoolDtos.getTransactionDateTime()!=null && !moneyPoolDtos.getTransactionDateTime().equals("")){
            sql.append(" and DATE_FORMAT(m.transactionDateTime,'%Y-%m-%d')  = ? ");
            params.add(moneyPoolDtos.getTransactionDateTime());
        }
        String currency;
        if(moneyPoolDtos.getCurrency()!=null && !moneyPoolDtos.getCurrency().equals("")){
            currency = moneyPoolDtos.getCurrency();
            sql.append(" and m.currency  like ? ");
            params.add("%"+currency+"%");
        }
        BigDecimal amount;
        if(moneyPoolDtos.getAmount()!=null){
            amount = moneyPoolDtos.getAmount();
            sql.append(" and m.amount  = ? ");
            params.add(amount);
        }
        BigDecimal before;
        if(moneyPoolDtos.getBeforeTotalBalance()!=null){
            before = moneyPoolDtos.getBeforeTotalBalance();
            sql.append(" and m.beforeTotalBalance  = ? ");
            params.add(before);
        }
        BigDecimal after;
        if(moneyPoolDtos.getAfterTotalBalance()!=null){
            after = moneyPoolDtos.getAfterTotalBalance();
            sql.append(" and m.afterTotalBalance  = ? ");
            params.add(after);
        }
        if(StringUtils.isNotBlank(moneyPoolDtos.getRemark())){
        	sql.append(" and m.remark  like ? ");
        	params.add("%"+moneyPoolDtos.getRemark()+"%");
        }
        //sort
        String sortName;
        if(StringUtils.isBlank(moneyPoolDtos.getSortBy()) || SortName.GEA_USER_ID.getValue().equals(moneyPoolDtos.getSortBy())){
            sortName = "m."+SortName.CREATE_DATETIME.getValue();
        }else{
            sortName="m."+moneyPoolDtos.getSortBy();
        }
        String sortType = Sort.Direction.DESC.toString();
        if(moneyPoolDtos.getIsAscending()!=null && moneyPoolDtos.getIsAscending()){
            sortType = Sort.Direction.ASC.toString();
        }
        sql.append(" order by "+sortName+" "+sortType+" ");
        
        PageDatas<MoneyPoolDto> pageData = new PageDatas<>(moneyPoolDtos.getPageNo(),moneyPoolDtos.getPageSize());
        Page<Map<String, Object>> moneyPoolCashFlow;
        moneyPoolCashFlow = jpaUtil.pageBySql(moneyPoolDtos.getInstance(),sql.toString(),pageData.pageRequest(),params.toArray());
        
        List<MoneyPoolDto> listDto = new ArrayList<>();
        for(Map<String, Object> item : moneyPoolCashFlow.getContent()){
            MoneyPoolDto dto = new MoneyPoolDto();
            dto.setGeaMoneyTransferId((String)item.get("geaMoneyTransferId"));
            dto.setCurrency((String)item.get("currency"));
            dto.setAmount((BigDecimal)item.get("amount"));
            dto.setMoneyPoolId((String)item.get("mpRefId"));
            dto.setParticipantId((String)item.get("ewRefId"));
            dto.setBeforeTotalBalance((BigDecimal)item.get("beforeTotalBalance"));
            dto.setAfterTotalBalance((BigDecimal)item.get("afterTotalBalance"));
            dto.setTransactionDateTime(item.get("transactionDateTime").toString());
            dto.setTransactionType((String)item.get("transactionType"));
            dto.setRemark((String)item.get("remark"));
            dto.setServiceId((String)item.get("serviceId"));
            dto.setRefNo((String)item.get("refNo"));
            listDto.add(dto);
        }

        pageData.setList(listDto);
        pageData.initDatas(listDto, moneyPoolCashFlow.getTotalElements(), moneyPoolCashFlow.getTotalPages());
        response.setData(pageData);
        return response;
    }

    private String getValue(String s){
        if(null != s){
            if (s.endsWith(",")) {
                s= s.substring(0,s.length()-1);
            }
            StringBuffer sb = new StringBuffer();
            String [] strArray = s.split(",");
            for(String ss : strArray){
                if(StringUtils.isNotEmpty(ss)){
                    sb.append("'"+ss+"',");
                }
            }
            String sl = null;
            if(sb.length()>0){
                sl = sb.substring(0,sb.length()-1);
            }
            return sl;
        }
        return null;
    }
}
