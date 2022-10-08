package com.example.skydog.service.impl;

import com.example.skydog.dao.AddressDao;
import com.example.skydog.enums.ResultEnum;
import com.example.skydog.module.entity.Address;
import com.example.skydog.module.vo.ResultVO;
import com.example.skydog.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {


    @Autowired
    private AddressDao addressDao;


    /**
     * 添加地址
     */
    @Override
    public ResultVO add(Address address) {
        //判断地址是否存在

        addressDao.add(address);
        return new ResultVO(ResultEnum.ADD_SUCCESS);
    }

    /**
     * 删除操作
     */
    @Override
    public ResultVO delete(Integer addressId) {
        if (addressDao.queryId(addressId) == null) {
            return new ResultVO(ResultEnum.DELETE_FAIL, "该地址不存在");
        } else {
            addressDao.delete(addressId);
            return new ResultVO(ResultEnum.DELETE_SUCCESS);
        }
    }

    /**
     * Id查询
     */
    @Override
    public Address queryId(Integer addressId) {
        return addressDao.queryId(addressId);
    }

    /**
     * 更新地址
     */
    @Override
    public ResultVO update(Address address) {
//        Address address1 = new Address();
//        address1.setAddressId(address.getAddressId());
//        if (addressDao.queryId(address.getAddressId()) == null) {
//            return new ResultVO(ResultEnum.UPDATE_FAIL, "该地址不存在");
//        } else {
//            addressDao.update(address);
//            return new ResultVO(ResultEnum.UPDATE_SUCCESS);
//        }
        addressDao.update(address);
        return new ResultVO(ResultEnum.UPDATE_SUCCESS);
    }

    /**
     * 获取用户所有地址
     */
    @Override
    public ResultVO getMyAddress(Integer userId) {
        return new ResultVO(addressDao.getMyAddress(userId));
    }

//    public void batchDelete(List list) {
//        // TODO: implement
//    }
//
//
//    public List<Address> queryCondition() {
//        // TODO: implement
//        return null;
//    }

}