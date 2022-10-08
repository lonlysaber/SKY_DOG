package com.example.skydog.dao;

import com.example.skydog.module.entity.Address;
import com.example.skydog.module.vo.ResultVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface AddressDao {

   void add(Address address);

   void delete(Integer addressId);

   Address queryId(Integer addressId);

   void update(Address address);

   List<Address> getMyAddress(Integer userId);

//   void batchDelete(List list);


}