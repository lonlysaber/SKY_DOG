package com.example.skydog.service.impl;

import com.example.skydog.dao.EvaluateDao;
import com.example.skydog.enums.ResultEnum;
import com.example.skydog.module.entity.Evaluate;
import com.example.skydog.module.vo.ResultVO;
import com.example.skydog.service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class EvaluateServiceImpl implements EvaluateService {


    @Autowired
    private EvaluateDao evaluateDao;

    /**
     * 添加评价
     */
    @Override
    public ResultVO add(Evaluate evaluate) {
        evaluateDao.add(evaluate);
        return new ResultVO(ResultEnum.ADD_SUCCESS);
    }

    /**
     * 删除评价
     */
    @Override
    public ResultVO delete(Integer userId, Integer evaluateId) {
        evaluateDao.delete(userId, evaluateId);
        return new ResultVO(ResultEnum.DELETE_SUCCESS);
    }


    /**
     * Id查询
     */
    @Override
    public Evaluate queryId(Integer evaluateId) {
        evaluateDao.queryId(evaluateId);
        return evaluateDao.queryId(evaluateId);
    }

    /**
     * 条件查询
     */
    @Override
    public List<Evaluate> queryCondition(Evaluate evaluate) {
        return evaluateDao.queryCondition(evaluate);
    }

    /**
     * 获取用户评价
     */
    @Override
    public ResultVO getMyEvaluate(Integer userId) {
        return new ResultVO(evaluateDao.getMyEvaluate(userId));
    }

    /**
     * 获取商品评价
     */
    @Override
    public ResultVO getProductEvaluate(Integer productId) {
        return new ResultVO(evaluateDao.getProductEvaluate(productId));
    }

    /**
     * 批量删除
     */
    public void batchDelete(List list) {
        // TODO: implement
    }

    /**
     * 评价等级查询
     */
    public List<Evaluate> queryByGrade(String grade) {
        // TODO: implement
        return null;
    }

}