package com.example.skydog.dao;

import com.example.skydog.module.entity.Evaluate;
import com.example.skydog.module.vo.ResultVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface EvaluateDao {


    void add(Evaluate evaluate);

    void delete(Integer userId,Integer evaluateId);

    Evaluate queryId(Integer evaluateId);

    List<Evaluate> queryCondition(Evaluate evaluate);

    List<Evaluate> getMyEvaluate(Integer userId);

    List<Evaluate> getProductEvaluate(Integer productId);


//    void batchDelete(List list);


}