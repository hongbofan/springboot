package com.example.controller;

import com.example.Util.RedisUtil;
import com.example.Util.Result;
import com.example.entity.ProtectType;
import com.example.service.ProtectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yfmacmini001 on 2017/3/2.
 */
@RestController
@RequestMapping("protectType")
public class ProtectTypeController {
    @Autowired
    private ProtectTypeService protectTypeService;
    @Autowired
    private RedisUtil redisUtil;
    @RequestMapping(value = "findAll",method = RequestMethod.GET)
    public Result findAll(){
        redisUtil.set("1",new ProtectType());
        return protectTypeService.findAll();
    }
}
