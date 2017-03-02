package com.example.service;

import com.example.Util.Result;
import com.example.repositories.ProtectTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yfmacmini001 on 2017/3/2.
 */
@Service
public class ProtectTypeService {
    @Autowired
    private ProtectTypeRepository protectTypeRepository;

    public Result findAll(){
        return Result.success(protectTypeRepository.findAll());
    }
}
