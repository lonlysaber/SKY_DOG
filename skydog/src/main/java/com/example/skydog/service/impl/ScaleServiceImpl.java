package com.example.skydog.service.impl;

import com.example.skydog.dao.ScaleDao;
import com.example.skydog.module.entity.Scale;
import com.example.skydog.service.ScaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class ScaleServiceImpl implements ScaleService {

   @Autowired
   ScaleDao scaledao;
   public void add(Scale scale) {
      // TODO: implement
       scaledao.add(scale);
   }


   public void delete(Integer scaleId) {
      // TODO: implement
      scaledao.delete(scaleId);
   }


   public void update(Scale scale) {
      // TODO: implement
     scaledao.update(scale);
   }

   public Scale queryId(Integer scaleId) {
      // TODO: implement
     return scaledao.queryId(scaleId);
   }

   public List<Scale> queryCondition(Scale scale) {
      // TODO: implement
     return scaledao.queryCondition(scale);
   }

}