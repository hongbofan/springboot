package com.example.controller;

import com.example.util.Result;
import com.example.service.AccountInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yfmacmini001 on 2017/3/2.
 */

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private AccountInfoService accountInfoService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = "findByShopId",method = RequestMethod.GET)
    public Result findByShopId(@RequestParam("shopId")String shopId){
        return accountInfoService.findByShopId(shopId);
    }
}
