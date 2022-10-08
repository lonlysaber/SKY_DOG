package com.example.skydog.service;


import com.example.skydog.module.entity.Evaluate;
import com.example.skydog.module.vo.ResultVO;

import java.util.*;


public interface EvaluateService {

    ResultVO add(Evaluate evaluate);

    ResultVO delete(Integer userId,Integer evaluateId);

    Evaluate queryId(Integer evaluateId);

    List<Evaluate> queryCondition(Evaluate evaluate);

    ResultVO getMyEvaluate(Integer userId);

    ResultVO getProductEvaluate(Integer productId);

//    void batchDelete(List list);

    List<Evaluate> queryByGrade(String grade);


}