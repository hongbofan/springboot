package com.example.controller;

import com.example.Util.Result;
import com.example.service.AccountInfoService;
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

    @RequestMapping(value = "findByShopId",method = RequestMethod.GET)
    public Result findByShopId(@RequestParam("shopId")String shopId){
        return accountInfoService.findByShopId(shopId);
    }
}
