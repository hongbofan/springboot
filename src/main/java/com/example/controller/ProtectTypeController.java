package com.example.controller;

import com.example.util.Result;
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
    @RequestMapping(value = "findAll",method = RequestMethod.GET)
    public Result findAll(){
        return protectTypeService.findAll();
    }
}
