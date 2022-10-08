package com.example.skydog.dao;

import com.example.skydog.module.entity.Admin;
import org.apache.ibatis.annotations.Mapper;


import java.util.*;

@Mapper
public interface AdminDao {

   void add(Admin admin);

   void delete(Integer id);

   void update(Admin admin);

   Admin queryId(Integer id);

   List<Admin> queryCondition(Admin admin);

}