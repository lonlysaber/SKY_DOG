package com.example.skydog.service;

import com.example.skydog.module.entity.Collect;
import com.example.skydog.module.entity.Product;
import com.example.skydog.module.vo.ResultVO;

import java.util.List;


public interface CollectService {

    ResultVO add(Collect collect);

    ResultVO addStore(Collect collect);

    ResultVO delete(Integer userId, Integer productId);

    ResultVO deleteStore(Integer userId, Integer sellerId);

    ResultVO getMyCollect(Integer userId);

    ResultVO getMyStore(Integer userId);

    ResultVO getUseless(Integer userId);

    ResultVO getSearch(Integer userId, String keyword);

    ResultVO queryCondition(Collect collect);

    ResultVO queryId(Integer collectId);

    ResultVO batchDelete(Integer userId, List list);


}
