package com.example.service;

import com.example.Util.Result;
import com.example.dao.AccountInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by yfmacmini001 on 2017/3/2.
 */
@Service
public class AccountInfoService {
    @Autowired
    private AccountInfoDao accountInfoDao;

    public Result findByShopId(String shopId){
        return Result.success(accountInfoDao.selectByShopId(shopId));
    }
}
