package com.example.skydog.service;

import com.example.skydog.module.entity.Admin;
import com.example.skydog.module.vo.ResultVO;

import java.util.*;


public interface AdminService {

   void add(Admin admin);

   ResultVO delete(Integer id);

   void update(Admin admin);

   Admin queryId(Integer id);

   List<Admin> queryCodition(Admin admin);

   ResultVO login(Admin admin);

}