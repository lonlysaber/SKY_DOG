package com.example.skydog.service;

import com.example.skydog.module.entity.Address;
import com.example.skydog.module.vo.ResultVO;

import java.util.*;


public interface AddressService {

    ResultVO add(Address address);

    ResultVO delete(Integer addressId);

    Address queryId(Integer addressId);

    ResultVO update(Address address);

    ResultVO getMyAddress(Integer userId);

//    void batchDelete(List list);

//    List<Address> queryCondition();

}