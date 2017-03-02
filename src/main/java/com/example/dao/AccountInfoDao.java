package com.example.dao;

import com.example.entity.AccountInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;


@Component
public interface AccountInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(AccountInfo record);

    int insertSelective(AccountInfo record);

    AccountInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountInfo record);

    int updateByPrimaryKey(AccountInfo record);

    AccountInfo selectByShopId(@Param("shopId")String shopId);

}