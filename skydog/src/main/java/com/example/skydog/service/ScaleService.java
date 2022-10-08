package com.example.skydog.service;


import com.example.skydog.module.entity.Scale;

import java.util.*;


public interface ScaleService {

   void add(Scale scale);

   void delete(Integer scaleId);

   void update(Scale scale);

   Scale queryId(Integer scaleId);

   List<Scale> queryCondition(Scale scale);

}