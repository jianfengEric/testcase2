package com.tng.portal.ana.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tng.portal.ana.entity.AnaAccount;
import com.tng.portal.ana.repository.AnaAccountAccessTokenRepository;
import com.tng.portal.ana.repository.AnaAccountRepository;
import com.tng.portal.common.util.PropertiesUtil;

/**
 * Created by Dell on 2018/10/11.
 */
@RestController
@RequestMapping("remote_delete_token/v1")
public class RemoteDeleteTokenController {
    @Autowired
    private AnaAccountAccessTokenRepository anaAccountAccessTokenRepository;
    
    @Autowired
    private AnaAccountRepository anaAccountRepository;
    

    @GetMapping("delete-ana-token")
    @ResponseBody
    public void deleteToken(){
        String expiresMinus = PropertiesUtil.getPropertyValueByKey("token.expires.mins");
        int minus = Integer.parseInt(expiresMinus);
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE,-minus);
        date=calendar.getTime();
        anaAccountAccessTokenRepository.deleteByExpriedTime(date);
    }

    @GetMapping("delete-ana-token-by-user")
    @ResponseBody
    public void deleteTokenByUser(@RequestParam String account){
    	List<AnaAccount> list = anaAccountRepository.findByAccount(account);
    	for(AnaAccount item : list){
    		anaAccountAccessTokenRepository.deleteByAccount(item.getId());
    	}
    }
    
}
